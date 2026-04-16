package MODEL;

public class Producto {
    private final String nombre;
    private final double precio;
    private int         cantidad;

    /**
     * @param nombre Nombre descriptivo del plato o bebida.
     * @param precio Precio unitario en pesos colombianos.
     */
    public Producto(String nombre, double precio) {
        this.nombre   = nombre;
        this.precio   = precio;
        this.cantidad = 0;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getNombre()   { return nombre; }
    public double getPrecio()   { return precio; }
    public int    getCantidad() { return cantidad; }

    // ── Lógica de pedido ──────────────────────────────────────────────────────

    /**
     * Añade unidades al pedido.
     *
     * @param unidades Cantidad positiva a agregar.
     */
    public void agregarUnidades(int unidades) {
        this.cantidad += unidades;
    }

    /** Reinicia la cantidad pedida a cero. */
    public void reiniciarCantidad() {
        this.cantidad = 0;
    }

    /** @return Subtotal parcial (precio × cantidad). */
    public double calcularSubtotalParcial() {
        return precio * cantidad;
    }

    /** @return {@code true} si el producto tiene al menos una unidad en el pedido. */
    public boolean tieneUnidadesPedidas() {
        return cantidad > 0;
    }
}