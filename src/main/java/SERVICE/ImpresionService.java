package SERVICE;

import MODEL.Pedido;
import MODEL.Producto;
import MODEL.ResultadoFactura;
import UTIL.Constantes;

public class ImpresionService {

    // ── Carta ─────────────────────────────────────────────────────────────────

    /**
     * Muestra la carta completa del restaurante con numeración y precios.
     */
    public void mostrarCarta(Pedido pedido) {
        imprimirEncabezadoRestaurante();
        System.out.println("    --- NUESTRA CARTA ---");
        System.out.println(Constantes.SEPARADOR_DOBLE);

        int numero = 1;
        for (Producto producto : pedido.getProductos()) {
            System.out.printf("%d. %-22s $%,.0f%n",
                    numero++,
                    producto.getNombre(),
                    producto.getPrecio());
        }

        System.out.println(Constantes.SEPARADOR_DOBLE);
    }

    // ── Pedido actual ─────────────────────────────────────────────────────────

    /**
     * Muestra los productos seleccionados con sus cantidades y subtotales parciales.
     */
    public void mostrarPedidoActual(Pedido pedido) {
        double subtotal = 0;

        System.out.println("--- PEDIDO ACTUAL (Mesa " + pedido.getNumeroMesa() + ") ---");

        for (Producto producto : pedido.getProductos()) {
            if (producto.tieneUnidadesPedidas()) {
                System.out.printf("%-20s x%-6d $%,.0f%n",
                        producto.getNombre(),
                        producto.getCantidad(),
                        producto.calcularSubtotalParcial());
                subtotal += producto.calcularSubtotalParcial();
            }
        }

        System.out.println(Constantes.SEPARADOR_SIMPLE);
        System.out.printf("%-27s $%,.0f%n", "Subtotal:", subtotal);
    }

    // ── Facturas ──────────────────────────────────────────────────────────────

    /**
     * Imprime la factura detallada. Usa el número de factura del ResultadoFactura
     * (capturado antes del incremento) para mostrar el número correcto.
     *
     * @param pedido    Pedido con los productos seleccionados (para listar ítems).
     * @param resultado Resultado del cálculo con el número de factura correcto.
     */
    public void imprimirFacturaCompleta(Pedido pedido, ResultadoFactura resultado) {
        imprimirEncabezadoFactura(resultado.getNumeroFactura());
        imprimirItemsDelPedido(pedido);
        imprimirTotalesFactura(resultado);
        imprimirPieDeFactura();
    }

    /**
     * Imprime únicamente el resumen de totales.
     *
     * @param resultado Resultado del cálculo con los totales y número de factura.
     */
    public void imprimirFacturaResumen(ResultadoFactura resultado) {
        imprimirEncabezadoFactura(resultado.getNumeroFactura());
        System.out.printf("FACTURA No. %03d (RESUMEN)%n", resultado.getNumeroFactura());
        System.out.println(Constantes.SEPARADOR_SIMPLE);
        imprimirTotalesFactura(resultado);
    }

    // ── Métodos privados de impresión ─────────────────────────────────────────

    private void imprimirEncabezadoRestaurante() {
        System.out.println(Constantes.SEPARADOR_DOBLE);
        System.out.println("    " + Constantes.NOMBRE_RESTAURANTE);
        System.out.println(Constantes.SEPARADOR_DOBLE);
    }

    private void imprimirEncabezadoFactura(int numeroFactura) {
        System.out.println(Constantes.SEPARADOR_DOBLE);
        System.out.println("    " + Constantes.NOMBRE_RESTAURANTE);
        System.out.println("    " + Constantes.DIRECCION);
        System.out.println("    NIT: " + Constantes.NIT);
        System.out.println(Constantes.SEPARADOR_DOBLE);
        System.out.printf("FACTURA No. %03d%n", numeroFactura);
        System.out.println(Constantes.SEPARADOR_SIMPLE);
    }

    private void imprimirItemsDelPedido(Pedido pedido) {
        for (Producto producto : pedido.getProductos()) {
            if (producto.tieneUnidadesPedidas()) {
                System.out.printf("%-20s x%-6d $%,.0f%n",
                        producto.getNombre(),
                        producto.getCantidad(),
                        producto.calcularSubtotalParcial());
            }
        }
        System.out.println(Constantes.SEPARADOR_SIMPLE);
    }

    private void imprimirTotalesFactura(ResultadoFactura resultado) {
        System.out.printf("%-27s $%,.0f%n", "Subtotal:", resultado.getSubtotalConDescuento());
        System.out.printf("%-27s $%,.0f%n", "IVA (19%):", resultado.getMontoIva());

        if (resultado.tienePropina()) {
            System.out.printf("%-27s $%,.0f%n", "Propina (10%):", resultado.getMontoPropina());
        }

        System.out.println(Constantes.SEPARADOR_SIMPLE);
        System.out.printf("%-27s $%,.0f%n", "TOTAL:", resultado.getTotalFinal());
        System.out.println(Constantes.SEPARADOR_DOBLE);
    }

    private void imprimirPieDeFactura() {
        System.out.println("Gracias por su visita!");
        System.out.println(Constantes.NOMBRE_RESTAURANTE + " - " + Constantes.DIRECCION);
        System.out.println(Constantes.SEPARADOR_DOBLE);
    }
}