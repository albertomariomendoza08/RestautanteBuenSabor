package SERVICE;

import MODEL.Pedido;
import MODEL.Producto;
import MODEL.ResultadoFactura;
import UTIL.Constantes;

public class CalculoService {

    /**
     * Calcula el resultado completo de la factura para el pedido actual.
     * Captura el número de factura ANTES de que el Pedido lo incremente.
     *
     * @param pedido Pedido con los productos y cantidades seleccionadas.
     * @return {@link ResultadoFactura} con subtotal, IVA, propina, total y número de factura.
     */
    public ResultadoFactura calcularFactura(Pedido pedido) {
        // Capturar el número de factura actual antes de cualquier modificación al pedido
        int numeroFactura = pedido.getNumeroFactura();

        double subtotalBruto        = calcularSubtotalBruto(pedido);
        double subtotalConDescuento = aplicarDescuentoPorVolumen(subtotalBruto, pedido.contarProductosDistintos());
        double montoIva             = calcularIva(subtotalConDescuento);
        double baseConIva           = subtotalConDescuento + montoIva;
        double montoPropina         = calcularPropina(baseConIva, subtotalConDescuento);
        double totalFinal           = baseConIva + montoPropina;

        return new ResultadoFactura(subtotalConDescuento, montoIva, montoPropina, totalFinal, numeroFactura);
    }

    // ── Métodos privados de cálculo ───────────────────────────────────────────

    /**
     * Suma precio × cantidad de cada producto en el pedido.
     */
    private double calcularSubtotalBruto(Pedido pedido) {
        return pedido.getProductos().stream()
                .filter(Producto::tieneUnidadesPedidas)
                .mapToDouble(Producto::calcularSubtotalParcial)
                .sum();
    }

    /**
     * Aplica un descuento por volumen cuando el pedido supera el mínimo de productos distintos.
     */
    private double aplicarDescuentoPorVolumen(double subtotalBruto, int cantidadDistintos) {
        if (cantidadDistintos > Constantes.MINIMO_ITEMS_CON_DESCUENTO) {
            return subtotalBruto - (subtotalBruto * Constantes.DESCUENTO_POR_VOLUMEN);
        }
        return subtotalBruto;
    }

    /**
     * Calcula el IVA sobre la base imponible.
     */
    private double calcularIva(double baseImponible) {
        return baseImponible * Constantes.TASA_IVA;
    }

    /**
     * Aplica propina solo cuando la base con IVA supera el umbral definido.
     */
    private double calcularPropina(double baseConIva, double subtotalConDescuento) {
        if (subtotalConDescuento > Constantes.UMBRAL_PROPINA) {
            return baseConIva * Constantes.PORCENTAJE_PROPINA;
        }
        return 0.0;
    }
}