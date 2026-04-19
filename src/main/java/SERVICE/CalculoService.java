package SERVICE;

import MODEL.Pedido;
import MODEL.Producto;
import MODEL.ResultadoFactura;
import UTIL.Constantes;

public class CalculoService {

    // punto de entrada principal: recibe el pedido y devuelve la factura calculada
    public ResultadoFactura calcularFactura(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        if (!pedido.hayProductosEnPedido()) {
            throw new IllegalStateException("No se puede facturar un pedido sin productos.");
        }

        // se captura el número ANTES de que el pedido lo incremente al cerrar la mesa
        int numeroFactura = pedido.getNumeroFactura();

        double subtotalBruto        = calcularSubtotalBruto(pedido);
        double subtotalConDescuento = aplicarDescuentoPorVolumen(subtotalBruto, pedido.contarProductosDistintos());
        double montoIva             = calcularIva(subtotalConDescuento);
        double baseConIva           = subtotalConDescuento + montoIva;
        double montoPropina         = calcularPropina(baseConIva, subtotalConDescuento);
        double totalFinal           = baseConIva + montoPropina;

        return new ResultadoFactura(subtotalConDescuento, montoIva, montoPropina, totalFinal, numeroFactura);
    }

    // suma precio × cantidad de cada producto que tenga unidades en el pedido
    private double calcularSubtotalBruto(Pedido pedido) {
        return pedido.getProductos().stream()
                .filter(Producto::tieneUnidadesPedidas)
                .mapToDouble(Producto::calcularSubtotalParcial)
                .sum();
    }

    // el descuento solo aplica cuando el cliente pidió más de MINIMO_ITEMS_CON_DESCUENTO productos distintos
    private double aplicarDescuentoPorVolumen(double subtotalBruto, int cantidadDistintos) {
        if (cantidadDistintos > Constantes.MINIMO_ITEMS_CON_DESCUENTO) {
            return subtotalBruto - (subtotalBruto * Constantes.DESCUENTO_POR_VOLUMEN);
        }
        return subtotalBruto;
    }

    // IVA colombiano estándar sobre la base imponible
    private double calcularIva(double baseImponible) {
        return baseImponible * Constantes.TASA_IVA;
    }

    // la propina se cobra solo cuando el subtotal supera el umbral definido en Constantes
    private double calcularPropina(double baseConIva, double subtotalConDescuento) {
        if (subtotalConDescuento > Constantes.UMBRAL_PROPINA) {
            return baseConIva * Constantes.PORCENTAJE_PROPINA;
        }
        return 0.0;
    }
}