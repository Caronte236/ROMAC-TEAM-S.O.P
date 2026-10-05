import java.util.Scanner;


public class Prueba {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Bios bios = new Bios();
        sistemaOperativo so = new sistemaOperativo("ROMAC", "2.0");
        Kernel kernel = new Kernel();
        Memoria memoria = new Memoria(1000, 50);

        System.out.println("Iniciando Sistema Operativo....");
        System.out.println("Cargando recursos necesarios...");

        System.out.println(so.getLogo());
        System.out.println(so.mostrarInformacion());

        kernel.encender();
        System.out.println("=".repeat(20));
        System.out.println(bios.arrancar());
        System.out.println(bios.checarDispositivos());
        System.out.println("=".repeat(20));

        if (kernel.estaEncendido()) {
            System.out.println(
                "Kernel iniciado correctamente."
            );
        }

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

        Proceso p1 = administrador.crearProceso(
                "P1", false, 0, 7
        );

        Proceso p2 = administrador.crearProceso(
                "P2", false, 2, 4
        );

        Proceso p3 = administrador.crearProceso(
                "P3", false, 4, 1
        );

        Proceso p4 = administrador.crearProceso(
                "P4", false, 5, 4
        );

        // ==========================================
        // CREAR USUARIO (como en Kernel.main)
        // ==========================================

        System.out.println();
        System.out.println("=================================");
        System.out.println("     CREACIÓN DE USUARIO");
        System.out.println("=================================");

        System.out.print("Crea tu user: ");
        String user = sc.nextLine();

        System.out.print("Crea tu pass: ");
        String pass = sc.nextLine();

        kernel.crear(user, pass);

        System.out.println(
            "== Usuario creado: "
            + kernel.getUsuario()
            + " / "
            + kernel.getContraseña()
        );

        // ==========================================
        // INICIO DE SESIÓN (máx 3 intentos)
        // ==========================================

        System.out.println();
        System.out.println("=================================");
        System.out.println("             INICIO");
        System.out.println("=================================");

        boolean iniciado = false;
        int intentos = 0;

        while (!iniciado && intentos < 3 && kernel.estaEncendido()) {

            System.out.println();
            System.out.print("Usuario: ");
            String usuario = sc.nextLine();

            System.out.print("Contraseña: ");
            String contraseña = sc.nextLine();

            if (kernel.iniciar(usuario, contraseña)) {

                System.out.println();
                System.out.println(
                    "Bienvenido "
                    + kernel.getUsuarioActual()
                    + "!"
                );

                iniciado = true;

            } else {

                intentos++;

                System.out.println(
                    "Datos incorrectos. Intento "
                    + intentos + "/3"
                );
            }
        }

        if (!iniciado) {

            System.out.println();
            System.out.println(
                "Demasiados intentos fallidos."
            );

            kernel.apagar();
            System.out.println("Sistema operativo apagado.");

            sc.close();
            return;
        }

        // ==========================================
        // SHELL DE COMANDOS
        // ==========================================

        boolean salir = false;

        String ayuda = """
            
            ============== COMANDOS ==============
            
            help          - Mostrar comandos
            informacion   - Información del sistema
            usuario       - Usuario actual
            procesos      - Lista de procesos activos
            crear_proc    - Crea un nuevo proceso
            eliminar_proc - Elimina un proceso por PID
            sjf           - Ejecutar planificador SJF expropiativo
            memoria       - Información de memoria
            archivos      - Sistema de archivos
            cmuser        - Cambiar nombre de usuario
            cmpass        - Cambiar password
            cerrar        - Cerrar sesión
            apagar        - Apagar sistema
            
            ======================================
            """;

        System.out.println();
        System.out.println(
            "Escriba 'help' para ver los comandos."
        );

        while (!salir && kernel.estaEncendido()) {

            System.out.print(
                kernel.getUsuarioActual()
                + "@"
                + so.getNombre()
                + "> "
            );

            String comando = sc.nextLine();

            switch (comando.toLowerCase()) {

                case "help":

                    System.out.println(ayuda);
                    break;

                case "informacion":

                    System.out.println(so.mostrarInformacion());
                    break;

                case "usuario":

                    System.out.println();
                    System.out.println(
                        "======= USUARIO ======="
                    );

                    System.out.println(
                        "Usuario actual: "
                        + kernel.getUsuarioActual()
                    );

                    System.out.println(
                        "======================="
                    );
                    break;
                    
                case "cmuser":

                    System.out.println();
                    System.out.println(
                        "======= CAMBIAR USUARIO ======="
                    );

                    System.out.print(
                        "Ingrese su nombre de usuario actual: "
                    );

                    String uac = sc.nextLine();

                    if (kernel.getUsuario().equals(uac)) {

                        System.out.print(
                            "Ingrese el nuevo nombre de usuario: "
                        );

                        String nu = sc.nextLine();

                        kernel.setUsuario(nu);

                        // Actualizar también el usuario en sesión
                        // para que el prompt muestre el nuevo nombre
                        if (kernel.getUsuarioActual() != null) {
                            kernel.setUsuarioActual(nu);
                        }

                        System.out.println(
                            "Nombre de usuario actualizado: "
                            + kernel.getUsuario()
                        );

                    } else {

                        System.out.println(
                            "Usuario no encontrado, verifique."
                        );
                    }

                    break;
                // ==========================================
                // CAMBIAR PASSWORD
                // ==========================================
                case "cmpass":

                    System.out.println();
                    System.out.println(
                        "======= CAMBIAR PASSWORD ======="
                    );

                    System.out.print(
                        "Ingrese su password actual: "
                    );

                    String pwa = sc.nextLine();

                    if (kernel.getContraseña().equals(pwa)) {

                        System.out.print(
                            "Ingrese el nuevo password: "
                        );

                        String np = sc.nextLine();

                        System.out.print(
                            "Confirme el password: "
                        );

                        String conf = sc.nextLine();

                        if (np.equals(conf)) {

                            kernel.setContraseña(conf);

                            System.out.println(
                                "Password actualizado correctamente."
                            );

                        } else {

                            System.out.println(
                                "Las contraseñas no coinciden."
                            );
                        }

                    } else {

                        System.out.println(
                            "Password incorrecto."
                        );
                    }

                    break;            
            
                case "procesos":

                    System.out.println(
                        administrador.mostrarProcesos()
                    );
                    break;
                
                case "crear_proc": 
                System.out.print("Nombre del proceso:");
                String nomProc= sc.nextLine();
                    System.out.print("¿Es de alta prioridad? (true/false): ");
                    boolean prio = Boolean.parseBoolean(sc.nextLine());
                    System.out.print("Tiempo de llegada: ");
                    int lleg = Integer.parseInt(sc.nextLine());
                    System.out.print("Tiempo de rafaga: ");
                    int raf = Integer.parseInt(sc.nextLine());

                    Proceso nuevoP = administrador.crearProceso(nomProc, prio, lleg, raf);
                    if (nuevoP != null) {
                        System.out.println("¡Proceso creado con exito! PID asignado: " + nuevoP.getPID());
                    } else {
                        System.out.println("Error: Capacidad maxima de procesos alcanzada.");
                    }
                    break;

                case "eliminar_proc":
                    System.out.print("Ingrese el PID del proceso a eliminar: ");
                    int idEliminar = Integer.parseInt(sc.nextLine());
                    if (administrador.eliminarProceso(idEliminar)) {
                        System.out.println("Proceso con PID " + idEliminar + " eliminado correctamente.");
                    } else {
                        System.out.println("No se encontro un proceso con el PID especificado.");
                    }
                    break;

                // ==========================================
                // SJF EXPROPIATIVO + GANTT + RESULTADOS
                // + CICLO DE VIDA
                // ==========================================

                case "sjf":

                    System.out.println();
                    System.out.println(
                        "======================================"
                    );
                    System.out.println(
                        "       SJF EXPROPIATIVO"
                    );
                    System.out.println(
                        "======================================"
                    );

                    SJFE sjf = new SJFE(administrador);
                    sjf.ejecutar();

                    System.out.println(
                        sjf.mostrarGantt()
                    );

                    System.out.println(
                        sjf.mostrarResultados()
                    );

                    System.out.println(
                        "======================================"
                    );
                    System.out.println(
                        "       CICLO DE VIDA DE PROCESOS"
                    );
                    System.out.println(
                        "======================================"
                    );
                    
                    for(int i = 0; i < administrador.getCantidad(); i++) {
                        mostrarCiclo(administrador.getProceso(i));
                    }
                    
                    mostrarCiclo(p1);
                    mostrarCiclo(p2);
                    mostrarCiclo(p3);
                    mostrarCiclo(p4);

                    break;

                case "memoria":

                    System.out.println(memoria.mostrarMemoria());
                    break;

                case "archivos":

                    mostrarArchivos();
                    break;

                case "cerrar":

                    System.out.println();
                    System.out.println(
                        "Cerrando sesión de "
                        + kernel.getUsuarioActual()
                        + "..."
                    );

                    kernel.cerrarSesion();

                    System.out.println(
                        "Sesión cerrada correctamente."
                    );

                    salir = true;
                    break;

                case "apagar":

                    System.out.println();
                    System.out.println(
                        "Finalizando procesos..."
                    );

                    System.out.println(
                        "Guardando configuración..."
                    );

                    System.out.println(
                        "Cerrando Kernel..."
                    );

                    kernel.apagar();

                    System.out.println(
                        "Sistema operativo apagado."
                    );

                    salir = true;
                    break;

                default:

                    System.out.println(
                        "Comando no reconocido."
                    );

                    System.out.println(
                        "Escriba 'help' para ver "
                        + "los comandos disponibles."
                    );
                    break;
            }
        }

        sc.close();
    }

    // ==========================================
    // MOSTRAR CICLO DE UN PROCESO
    // ==========================================

    public static void mostrarCiclo(Proceso p) {

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

    // ==========================================
    // MOSTRAR SISTEMA DE ARCHIVOS
    // ==========================================

    public static void mostrarArchivos() {

        Archivo archivo1 =
            new Archivo(
                "kernel.sys",
                "Sistema",
                15.5,
                "/system"
            );

        Archivo archivo2 =
            new Archivo(
                "config.sys",
                "Configuración",
                5.2,
                "/system"
            );

        Archivo archivo3 =
            new Archivo(
                "usuario.dat",
                "Datos",
                10.8,
                "/users"
            );

        Archivo archivo4 =
            new Archivo(
                "explorer.exe",
                "Programa",
                35.0,
                "/programs"
            );

        Archivo archivo5 =
            new Archivo(
                "documento.txt",
                "Documento",
                2.5,
                "/documents"
            );

        System.out.println();
        System.out.println(
            "================================================="
        );
        System.out.println(
            "             SISTEMA DE ARCHIVOS"
        );
        System.out.println(
            "================================================="
        );

        System.out.println(
            "NOMBRE            TIPO          TAMAÑO    UBICACION"
        );

        System.out.println(
            "-------------------------------------------------"
        );

        System.out.println(archivo1.mostrarInformacion());
        System.out.println();
        System.out.println(archivo2.mostrarInformacion());
        System.out.println();
        System.out.println(archivo3.mostrarInformacion());
        System.out.println();
        System.out.println(archivo4.mostrarInformacion());
        System.out.println();
        System.out.println(archivo5.mostrarInformacion());

        System.out.println(
            "================================================="
        );
    }
}