package SERVICE;

import MODEL.Pedido;
import MODEL.Producto;
import MODEL.ResultadoFactura;

import java.util.List;

public class PedidoService {

    private final CalculoService calculoService;

    public PedidoService(CalculoService calculoService) {
        if (calculoService == null) {
            throw new IllegalArgumentException("CalculoService no puede ser nulo.");
        }
        this.calculoService = calculoService;
    }

    // agrega unidades de un producto; si la mesa no está activa la abre con el número recibido
    public void agregarProducto(Pedido pedido, int indiceMenu, int cantidad, int numeroMesa) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        List<Producto> productos = pedido.getProductos();

        // el índice viene del menú (1-based convertido a 0-based en la vista)
        if (indiceMenu < 0 || indiceMenu >= productos.size()) {
            throw new IndexOutOfBoundsException(
                    "Índice de producto fuera de rango: " + indiceMenu +
                            ". El menú tiene " + productos.size() + " productos.");
        }

        if (!pedido.isMesaActiva()) {
            pedido.setNumeroMesa(numeroMesa);
        }

        productos.get(indiceMenu).agregarUnidades(cantidad);
    }

    // calcula la factura, guarda el total y cierra la mesa; el número queda en ResultadoFactura
    public ResultadoFactura generarYCerrarFactura(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        ResultadoFactura resultado = calculoService.calcularFactura(pedido);
        pedido.setTotalUltimaFactura(resultado.getTotalFinal());
        pedido.cerrarMesaYAvanzarFactura();
        return resultado;
    }

    // limpia el pedido para atender una nueva mesa sin instanciar objetos nuevos
    public void prepararNuevaMesa(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        pedido.reiniciarPedido();
    }
}
