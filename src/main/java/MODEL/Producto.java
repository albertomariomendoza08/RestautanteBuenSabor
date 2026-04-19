package MODEL;

public class Producto {
    private final String nombre;
    private final double precio;
    private int         cantidad;

    public Producto(String nombre, double precio) {
        this.nombre   = nombre;
        this.precio   = precio;
        this.cantidad = 0;
    }

    // getters básicos del producto
    public String getNombre()   { return nombre; }
    public double getPrecio()   { return precio; }
    public int    getCantidad() { return cantidad; }

    // suma unidades al pedido; no acepta cero ni negativos porque corrompería el subtotal
    public void agregarUnidades(int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad a agregar debe ser mayor a cero. Se recibió: " + unidades);
        }
        this.cantidad += unidades;
    }

    // reinicia a cero para reutilizar el producto en un nuevo pedido
    public void reiniciarCantidad() {
        this.cantidad = 0;
    }

    // precio multiplicado por las unidades pedidas
    public double calcularSubtotalParcial() {
        return precio * cantidad;
    }

    // true si el cliente ya pidió al menos una unidad de este producto
    public boolean tieneUnidadesPedidas() {
        return cantidad > 0;
    }
}
