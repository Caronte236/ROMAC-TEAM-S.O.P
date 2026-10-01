public class Main {

    public static void main(String[] args) {

        // ==========================================
        // CREAR ADMINISTRADOR DE PROCESOS
        // ==========================================

        AdministradorProcesos administrador =
                new AdministradorProcesos(10);


        // ==========================================
        // CREAR PROCESOS
        // ==========================================
        //
        // Proceso | Llegada | Ráfaga
        // P1      |    0    |   7
        // P2      |    2    |   4
        // P3      |    4    |   1
        // P4      |    5    |   4
        //
        // ==========================================

        proceso p1 = administrador.crearProceso(
                "P1",
                false,
                0,
                7
        );

        proceso p2 = administrador.crearProceso(
                "P2",
                false,
                2,
                4
        );

        proceso p3 = administrador.crearProceso(
                "P3",
                false,
                4,
                1
        );

        proceso p4 = administrador.crearProceso(
                "P4",
                false,
                5,
                4
        );


        // ==========================================
        // MOSTRAR PROCESOS
        // ==========================================

        System.out.println("======================================");
        System.out.println("       PROCESOS CREADOS - ROMAC V2");
        System.out.println("======================================");

        System.out.println(
                administrador.mostrarProcesos()
        );


        // ==========================================
        // CREAR PLANIFICADOR SJF
        // ==========================================

        SJF sjf = new SJF(administrador);


        // ==========================================
        // EJECUTAR SJF EXPROPIATIVO
        // ==========================================

        System.out.println("======================================");
        System.out.println("       SJF EXPROPIATIVO");
        System.out.println("======================================");

        sjf.ejecutar();


        // ==========================================
        // MOSTRAR DIAGRAMA DE GANTT
        // ==========================================

        System.out.println(
                sjf.mostrarGantt()
        );


        // ==========================================
        // MOSTRAR CALCULOS
        // ==========================================

        System.out.println(
                sjf.mostrarResultados()
        );


        // ==========================================
        // MOSTRAR CICLO DE VIDA
        // ==========================================

        System.out.println("======================================");
        System.out.println("       CICLO DE VIDA DE PROCESOS");
        System.out.println("======================================");

        mostrarCiclo(p1);
        mostrarCiclo(p2);
        mostrarCiclo(p3);
        mostrarCiclo(p4);
    }


    // ==========================================
    // MOSTRAR CICLO DE UN PROCESO
    // ==========================================

    public static void mostrarCiclo(proceso p) {

        System.out.println(
                "\nProceso " + p.getNombre()
        );

        System.out.println(
                "Estado final: " + p.getEstado()
        );

        System.out.println(
                "Historial:"
        );

        System.out.println(
                p.getHistorial()
        );

        System.out.println(
                "--------------------------------------"
        );
    }
}