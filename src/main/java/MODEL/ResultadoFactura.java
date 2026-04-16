package MODEL;

/**
 * Encapsula el resultado del cálculo de una factura.
 * Incluye el número de factura capturado ANTES de que el Pedido lo incremente,
 * para que la impresión muestre siempre el número correcto.
 */
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

    public double getSubtotalConDescuento() { return subtotalConDescuento; }
    public double getMontoIva()             { return montoIva; }
    public double getMontoPropina()         { return montoPropina; }
    public double getTotalFinal()           { return totalFinal; }
    public int    getNumeroFactura()        { return numeroFactura; }

    /** @return {@code true} si se aplicó propina al pedido. */
    public boolean tienePropina() {
        return montoPropina > 0;
    }
}