package UTIL;

public class Constantes {

    // no se instancia; solo agrupa constantes del negocio
    private Constantes() {}

    // datos del restaurante para encabezados y facturas
    public static final String NOMBRE_RESTAURANTE  = "El Buen Sabor";
    public static final String DIRECCION           = "Calle 15 #8-32, Valledupar";
    public static final String NIT                 = "900.123.456-7";

    // reglas de facturación definidas por el negocio
    public static final double TASA_IVA                   = 0.19;
    public static final double PORCENTAJE_PROPINA         = 0.10;
    public static final double DESCUENTO_POR_VOLUMEN      = 0.05;
    public static final double UMBRAL_PROPINA             = 50_000.0;
    public static final int    MINIMO_ITEMS_CON_DESCUENTO = 3;

    // separadores reutilizables para la consola
    public static final String SEPARADOR_DOBLE  = "========================================";
    public static final String SEPARADOR_SIMPLE = "----------------------------------------";
}