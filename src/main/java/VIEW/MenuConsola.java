package VIEW;

import DAO.PedidoDao;
import MODEL.Pedido;
import MODEL.ResultadoFactura;
import SERVICE.CalculoService;
import SERVICE.ImpresionService;
import SERVICE.PedidoService;
import UTIL.Constantes;

import java.util.Scanner;

public class MenuConsola {
    private static final int OPCION_VER_CARTA         = 1;
    private static final int OPCION_AGREGAR_PRODUCTO  = 2;
    private static final int OPCION_VER_PEDIDO        = 3;
    private static final int OPCION_GENERAR_FACTURA   = 4;
    private static final int OPCION_NUEVA_MESA        = 5;
    private static final int OPCION_VER_HISTORIAL     = 6;
    private static final int OPCION_SALIR             = 0;

    private final Pedido           pedido;
    private final PedidoService    pedidoService;
    private final ImpresionService impresionService;
    private final PedidoDao        pedidoDao;
    private final Scanner          entradaUsuario;

    public MenuConsola() {
        CalculoService calculoService = new CalculoService();
        this.pedido           = new Pedido();
        this.pedidoService    = new PedidoService(calculoService);
        this.impresionService = new ImpresionService();
        this.pedidoDao        = new PedidoDao();
        this.entradaUsuario   = new Scanner(System.in);
    }

    // ── Bucle principal ───────────────────────────────────────────────────────

    /**
     * Inicia el sistema y mantiene el menú activo hasta que el usuario elija salir.
     */
    public void iniciar() {
        imprimirBienvenida();

        boolean continuarEjecutando = true;
        while (continuarEjecutando) {
            imprimirOpcionesDelMenu();
            int opcionSeleccionada = leerOpcionDelUsuario();
            continuarEjecutando = procesarOpcion(opcionSeleccionada);
        }

        entradaUsuario.close();
    }

    // ── Procesamiento de opciones ─────────────────────────────────────────────

    private boolean procesarOpcion(int opcionSeleccionada) {
        switch (opcionSeleccionada) {
            case OPCION_VER_CARTA:        procesarVerCarta();        break;
            case OPCION_AGREGAR_PRODUCTO: procesarAgregarProducto(); break;
            case OPCION_VER_PEDIDO:       procesarVerPedido();       break;
            case OPCION_GENERAR_FACTURA:  procesarGenerarFactura();  break;
            case OPCION_NUEVA_MESA:       procesarNuevaMesa();       break;
            case OPCION_VER_HISTORIAL:    procesarVerHistorial();    break;
            case OPCION_SALIR:            return false;
            default:
                System.out.println("Opción no válida. Seleccione entre 0 y 6.");
        }
        return true;
    }

    private void procesarVerCarta() {
        System.out.println();
        impresionService.mostrarCarta(pedido);
        System.out.println();
    }

    private void procesarAgregarProducto() {
        System.out.println("--- AGREGAR PRODUCTO ---");

        int numeroProduto = leerNumeroProducto();
        if (!esNumeroDeProductoValido(numeroProduto)) {
            System.out.println("Producto no existe. La carta tiene "
                    + pedido.getProductos().size() + " productos.");
            return;
        }

        int cantidad = leerCantidad();
        if (!esCantidadValida(cantidad)) {
            System.out.println("La cantidad debe ser mayor a cero.");
            return;
        }

        int numeroMesa = obtenerNumeroMesaParaElPedido();
        pedidoService.agregarProducto(pedido, numeroProduto - 1, cantidad, numeroMesa);

        String nombreProducto = pedido.getProductos().get(numeroProduto - 1).getNombre();
        System.out.println("Producto agregado: " + nombreProducto + " x" + cantidad);
        System.out.println();
    }

    private void procesarVerPedido() {
        System.out.println();
        if (pedido.hayProductosEnPedido()) {
            impresionService.mostrarPedidoActual(pedido);
        } else {
            System.out.println("No hay productos en el pedido. Use la opción 2 para agregar.");
        }
        System.out.println();
    }

    private void procesarGenerarFactura() {
        System.out.println();
        if (!pedido.hayProductosEnPedido()) {
            System.out.println("No se puede generar factura. Agregue productos primero (opción 2).");
            return;
        }

        // resultado ya contiene el número de factura correcto (antes del incremento en pedido)
        ResultadoFactura resultado = pedidoService.generarYCerrarFactura(pedido);
        pedidoDao.guardarFactura(resultado);
        impresionService.imprimirFacturaCompleta(pedido, resultado);
        System.out.println();
    }

    private void procesarNuevaMesa() {
        System.out.println();
        pedidoService.prepararNuevaMesa(pedido);
        System.out.println("Mesa reiniciada. Lista para nuevo cliente.");
        System.out.println();
    }

    private void procesarVerHistorial() {
        System.out.println();
        if (pedidoDao.contarFacturas() == 0) {
            System.out.println("No hay facturas generadas en esta sesión.");
        } else {
            System.out.println(Constantes.SEPARADOR_DOBLE);
            System.out.println("    HISTORIAL DE FACTURAS");
            System.out.println(Constantes.SEPARADOR_DOBLE);
            for (ResultadoFactura f : pedidoDao.obtenerHistorial()) {
                System.out.printf("Factura No. %03d  -  TOTAL: $%,.0f%n",
                        f.getNumeroFactura(), f.getTotalFinal());
            }
            System.out.println(Constantes.SEPARADOR_SIMPLE);
            System.out.printf("%-27s $%,.0f%n",
                    "Total acumulado sesión:", pedidoDao.calcularTotalAcumulado());
            System.out.println(Constantes.SEPARADOR_DOBLE);
        }
        System.out.println();
    }

    // ── Lectura de entradas del usuario ───────────────────────────────────────

    private void imprimirBienvenida() {
        System.out.println(Constantes.SEPARADOR_DOBLE);
        System.out.println("    " + Constantes.NOMBRE_RESTAURANTE);
        System.out.println("    " + Constantes.DIRECCION);
        System.out.println("    NIT: " + Constantes.NIT);
        System.out.println(Constantes.SEPARADOR_DOBLE);
    }

    private void imprimirOpcionesDelMenu() {
        System.out.println(OPCION_VER_CARTA        + ". Ver carta");
        System.out.println(OPCION_AGREGAR_PRODUCTO + ". Agregar producto al pedido");
        System.out.println(OPCION_VER_PEDIDO       + ". Ver pedido actual");
        System.out.println(OPCION_GENERAR_FACTURA  + ". Generar factura");
        System.out.println(OPCION_NUEVA_MESA       + ". Nueva mesa");
        System.out.println(OPCION_VER_HISTORIAL    + ". Ver historial de facturas");
        System.out.println(OPCION_SALIR            + ". Salir");
        System.out.println(Constantes.SEPARADOR_DOBLE);
        System.out.print("Seleccione una opción: ");
    }

    private int leerOpcionDelUsuario() {
        return entradaUsuario.nextInt();
    }

    private int leerNumeroProducto() {
        System.out.print("Número de producto (1-" + pedido.getProductos().size() + "): ");
        return entradaUsuario.nextInt();
    }

    private int leerCantidad() {
        System.out.print("Cantidad: ");
        return entradaUsuario.nextInt();
    }

    private int obtenerNumeroMesaParaElPedido() {
        if (pedido.isMesaActiva()) {
            return pedido.getNumeroMesa();
        }
        System.out.print("Ingrese número de mesa: ");
        int numeroMesa = entradaUsuario.nextInt();
        return (numeroMesa > 0) ? numeroMesa : 1;
    }

    // ── Validaciones ──────────────────────────────────────────────────────────

    private boolean esNumeroDeProductoValido(int numeroProduto) {
        return numeroProduto >= 1 && numeroProduto <= pedido.getProductos().size();
    }

    private boolean esCantidadValida(int cantidad) {
        return cantidad > 0;
    }
}