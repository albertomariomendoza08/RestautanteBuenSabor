package MODEL;

// estructura inmutable que transporta los resultados de un cálculo de factura
// se crea con el número de factura ANTES de que Pedido lo incremente
public class ResultadoFactura {
    private final double subtotalConDescuento;
    private final double montoIva;
    private final double montoPropina;
    private final double totalFinal;
    private final int    numeroFactura;

    public ResultadoFactura(double subtotalConDescuento,
                            double montoIva,
                            double montoPropina,
                            double totalFinal,
                            int    numeroFactura) {
        this.subtotalConDescuento = subtotalConDescuento;
        this.montoIva             = montoIva;
        this.montoPropina         = montoPropina;
        this.totalFinal           = totalFinal;
        this.numeroFactura        = numeroFactura;
    }

    // getters del resultado de factura
    public double getSubtotalConDescuento() { return subtotalConDescuento; }
    public double getMontoIva()             { return montoIva; }
    public double getMontoPropina()         { return montoPropina; }
    public double getTotalFinal()           { return totalFinal; }
    public int    getNumeroFactura()        { return numeroFactura; }

    // true cuando el subtotal superó el umbral y se cobró propina
    public boolean tienePropina() {
        return montoPropina > 0;
    }
}