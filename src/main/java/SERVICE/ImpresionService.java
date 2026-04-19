package SERVICE;

import MODEL.Pedido;
import MODEL.Producto;
import MODEL.ResultadoFactura;
import UTIL.Constantes;

public class ImpresionService {

    // muestra la carta completa con numeración y precios formateados
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

    // lista los productos pedidos con cantidad y subtotal parcial
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

    // imprime la factura completa: encabezado, ítems y totales
    public void imprimirFacturaCompleta(Pedido pedido, ResultadoFactura resultado) {
        imprimirEncabezadoFactura(resultado.getNumeroFactura());
        imprimirItemsDelPedido(pedido);
        imprimirTotalesFactura(resultado);
        imprimirPieDeFactura();
    }

    // versión resumida de la factura sin detallar cada ítem
    public void imprimirFacturaResumen(ResultadoFactura resultado) {
        imprimirEncabezadoFactura(resultado.getNumeroFactura());
        System.out.printf("FACTURA No. %03d (RESUMEN)%n", resultado.getNumeroFactura());
        System.out.println(Constantes.SEPARADOR_SIMPLE);
        imprimirTotalesFactura(resultado);
    }

    // encabezado sin NIT ni dirección, solo para la carta
    private void imprimirEncabezadoRestaurante() {
        System.out.println(Constantes.SEPARADOR_DOBLE);
        System.out.println("    " + Constantes.NOMBRE_RESTAURANTE);
        System.out.println(Constantes.SEPARADOR_DOBLE);
    }

    // encabezado completo con datos fiscales para facturas legales
    private void imprimirEncabezadoFactura(int numeroFactura) {
        System.out.println(Constantes.SEPARADOR_DOBLE);
        System.out.println("    " + Constantes.NOMBRE_RESTAURANTE);
        System.out.println("    " + Constantes.DIRECCION);
        System.out.println("    NIT: " + Constantes.NIT);
        System.out.println(Constantes.SEPARADOR_DOBLE);
        System.out.printf("FACTURA No. %03d%n", numeroFactura);
        System.out.println(Constantes.SEPARADOR_SIMPLE);
    }

    // recorre solo los productos con unidades para no imprimir líneas vacías
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

    // la propina solo se imprime si aplica, para no confundir al cliente
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
