package DAO;

import MODEL.ResultadoFactura;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos en memoria.
 * Almacena el historial de facturas generadas durante la sesión.
 */
public class PedidoDao {

    private final List<ResultadoFactura> historialFacturas;

    public PedidoDao() {
        this.historialFacturas = new ArrayList<>();
    }

    /**
     * Registra una factura generada en el historial.
     *
     * @param resultado Resultado de la factura a guardar.
     */
    public void guardarFactura(ResultadoFactura resultado) {
        historialFacturas.add(resultado);
    }

    /**
     * @return Copia de la lista de todas las facturas generadas en la sesión.
     */
    public List<ResultadoFactura> obtenerHistorial() {
        return new ArrayList<>(historialFacturas);
    }

    /**
     * @return Total acumulado de todas las facturas de la sesión.
     */
    public double calcularTotalAcumulado() {
        return historialFacturas.stream()
                .mapToDouble(ResultadoFactura::getTotalFinal)
                .sum();
    }

    /**
     * @return Número de facturas registradas en la sesión.
     */
    public int contarFacturas() {
        return historialFacturas.size();
    }
}