package SERVICE;

import MODEL.Pedido;
import MODEL.Producto;
import MODEL.ResultadoFactura;

public class PedidoService {
    private final CalculoService calculoService;

    public PedidoService(CalculoService calculoService) {
        this.calculoService = calculoService;
    }

    // ── Operaciones de pedido ─────────────────────────────────────────────────

    /**
     * Agrega unidades de un producto al pedido.
     * Si la mesa no está activa, la activa con el número indicado.
     *
     * @param pedido       Pedido actual.
     * @param indiceMenu   Índice del producto en la lista (0-based).
     * @param cantidad     Unidades a agregar.
     * @param numeroMesa   Número de mesa a activar si aún no está activa.
     */
    public void agregarProducto(Pedido pedido, int indiceMenu, int cantidad, int numeroMesa) {
        if (!pedido.isMesaActiva()) {
            pedido.setNumeroMesa(numeroMesa);
        }

        Producto productoSeleccionado = pedido.getProductos().get(indiceMenu);
        productoSeleccionado.agregarUnidades(cantidad);
    }

    /**
     * Calcula la factura, registra el total y cierra la mesa.
     * El número de factura queda capturado dentro del ResultadoFactura
     * antes de que el Pedido lo incremente.
     *
     * @param pedido Pedido con los productos seleccionados.
     * @return Resultado del cálculo listo para imprimir.
     */
    public ResultadoFactura generarYCerrarFactura(Pedido pedido) {
        ResultadoFactura resultado = calculoService.calcularFactura(pedido);
        pedido.setTotalUltimaFactura(resultado.getTotalFinal());
        pedido.cerrarMesaYAvanzarFactura();
        return resultado;
    }

    /** Reinicia el pedido para una nueva mesa. */
    public void prepararNuevaMesa(Pedido pedido) {
        pedido.reiniciarPedido();
    }
}