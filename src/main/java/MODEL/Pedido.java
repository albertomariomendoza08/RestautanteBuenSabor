package MODEL;

import java.util.Arrays;
import java.util.List;

public class Pedido {

    private final List<Producto> productos;
    private int    numeroMesa;
    private int    numeroFactura;
    private double totalUltimaFactura;
    private boolean mesaActiva;

    public Pedido() {
        this.productos           = inicializarMenuDelRestaurante();
        this.numeroMesa          = 0;
        this.numeroFactura       = 1;
        this.totalUltimaFactura  = 0.0;
        this.mesaActiva          = false;
    }

    // ── Inicialización del menú ───────────────────────────────────────────────

    private List<Producto> inicializarMenuDelRestaurante() {
        return Arrays.asList(
                new Producto("Bandeja Paisa",       32_000),
                new Producto("Sancocho de Gallina", 28_000),
                new Producto("Arepa con Huevo",      8_000),
                new Producto("Jugo Natural",          7_000),
                new Producto("Gaseosa",               4_500),
                new Producto("Cerveza Poker",         6_000),
                new Producto("Agua Panela",           3_500),
                new Producto("Arroz con Pollo",      25_000)
        );
    }

    // ── Consultas ─────────────────────────────────────────────────────────────

    public List<Producto> getProductos()          { return productos; }
    public int            getNumeroMesa()         { return numeroMesa; }
    public int            getNumeroFactura()      { return numeroFactura; }
    public double         getTotalUltimaFactura() { return totalUltimaFactura; }
    public boolean        isMesaActiva()          { return mesaActiva; }

    /** @return {@code true} si hay al menos un producto con cantidad > 0. */
    public boolean hayProductosEnPedido() {
        return productos.stream().anyMatch(Producto::tieneUnidadesPedidas);
    }

    /** @return Número de productos distintos agregados al pedido. */
    public int contarProductosDistintos() {
        return (int) productos.stream()
                .filter(Producto::tieneUnidadesPedidas)
                .count();
    }

    // ── Mutaciones ────────────────────────────────────────────────────────────

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        this.mesaActiva = true;
    }

    public void setTotalUltimaFactura(double total) {
        this.totalUltimaFactura = total;
    }

    /** Avanza el contador de facturas y cierra la mesa activa. */
    public void cerrarMesaYAvanzarFactura() {
        this.numeroFactura++;
        this.mesaActiva = false;
    }

    /** Reinicia el pedido para atender una nueva mesa. */
    public void reiniciarPedido() {
        productos.forEach(Producto::reiniciarCantidad);
        this.totalUltimaFactura = 0.0;
        this.mesaActiva         = false;
        this.numeroMesa         = 0;
    }
}