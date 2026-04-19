package DAO;

import MODEL.ResultadoFactura;

import java.util.ArrayList;
import java.util.List;

// almacena en memoria el historial de facturas generadas durante la sesión
public class PedidoDao {

    private final List<ResultadoFactura> historialFacturas;

    public PedidoDao() {
        this.historialFacturas = new ArrayList<>();
    }

    // registra una factura al historial; no se permite guardar nulos
    public void guardarFactura(ResultadoFactura resultado) {
        if (resultado == null) {
            throw new IllegalArgumentException("No se puede guardar una factura nula.");
        }
        historialFacturas.add(resultado);
    }

    // devuelve copia para que nadie modifique el historial desde afuera
    public List<ResultadoFactura> obtenerHistorial() {
        return new ArrayList<>(historialFacturas);
    }

    // suma todos los totales de la sesión para el cierre del día
    public double calcularTotalAcumulado() {
        return historialFacturas.stream()
                .mapToDouble(ResultadoFactura::getTotalFinal)
                .sum();
    }

    // cantidad de facturas emitidas en la sesión activa
    public int contarFacturas() {
        return historialFacturas.size();
    }
}
