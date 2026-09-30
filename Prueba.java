import java.util.Scanner;

public class Prueba {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Bios bios = new Bios();
        sistemaOperativo so = new sistemaOperativo("ROMAC", "1.0");
        Kernel kernel = new Kernel();
        Memoria memoria = new Memoria(1000, 50);
        
        // ===== V2: Gestor de procesos =====
        GestorProcesos gestor = new GestorProcesos(memoria);

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
            System.out.println("Kernel iniciado correctamente.");
        }

        // Procesos iniciales (V1)
        proceso p1 = new proceso(1, "Kernel", true, proceso.estado.ejecutando);
        proceso p2 = new proceso(2, "Sistema", true, proceso.estado.espera);
        proceso p3 = new proceso(3, "Explorador", false, proceso.estado.listo);
        proceso p4 = new proceso(4, "Servicios", false, proceso.estado.espera);

        // ===== V2: Agregar procesos iniciales al gestor =====
        gestor.getColaProcesos().add(p1);
        gestor.getColaProcesos().add(p2);
        gestor.getColaProcesos().add(p3);
        gestor.getColaProcesos().add(p4);

        System.out.println();
        System.out.println("=================================");
        System.out.println("             INICIO");
        System.out.println("=================================");
        System.out.println("Usuario predeterminado: admin");
        System.out.println("Contraseña predeterminada: admin");

        boolean iniciado = false;

        while (!iniciado && kernel.estaEncendido()) {
            System.out.println();
            System.out.print("Usuario: ");
            String usuario = sc.nextLine();
            System.out.print("Contraseña: ");
            String contraseña = sc.nextLine();

            if (kernel.iniciar(usuario, contraseña)) {
                System.out.println();
                System.out.println("Bienvenido " + kernel.getUsuarioActual() + "!");
                iniciado = true;
            } else {
                System.out.println("Usuario o contraseña incorrectos.");
            }
        }

        if (iniciado) {
            boolean salir = false;

            String ayuda = """
                
                ============== COMANDOS ==============
                
                help          - Mostrar comandos
                informacion   - Información del sistema
                usuario       - Usuario actual
                procesos      - Administrador de tareas
                crear         - Crear un nuevo proceso (V2)
                finalizar     - Terminar un proceso por PID (V2)
                buscar        - Buscar proceso por PID (V2)
                sjf           - Planificación SJF (V2)
                gantt         - Planificación SJF clásica (V1)
                memoria       - Información de memoria
                archivos      - Sistema de archivos
                cerrar        - Cerrar sesión
                apagar        - Apagar sistema
                
                ======================================
                """;

            System.out.println();
            System.out.println("Escriba 'help' para ver los comandos.");

            while (!salir && kernel.estaEncendido()) {
                System.out.print(kernel.getUsuarioActual() + "@" + so.getNombre() + "> ");
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
                        System.out.println("======= USUARIO =======");
                        System.out.println("Usuario actual: " + kernel.getUsuarioActual());
                        System.out.println("=======================");
                        break;

                    case "procesos":
                        System.out.println(gestor.listarProcesos());
                        break;

                    // ===== V2: Crear proceso =====
                    case "crear":
                        System.out.print("Nombre del proceso: ");
                        String nom = sc.nextLine();
                        System.out.print("Prioridad (true=alta / false=baja): ");
                        boolean pri = Boolean.parseBoolean(sc.nextLine());
                        System.out.print("Memoria requerida (MB): ");
                        int mem = Integer.parseInt(sc.nextLine());
                        System.out.print("Tiempo de CPU (ms): ");
                        int cpu = Integer.parseInt(sc.nextLine());
                        System.out.print("Operaciones de E/S: ");
                        int es = Integer.parseInt(sc.nextLine());
                        proceso nuevo = gestor.crearProceso(nom, pri, mem, cpu, es, null);
                        if (nuevo != null) {
                            System.out.println("Proceso creado exitosamente con PID: " + nuevo.getPID());
                        } else {
                            System.out.println("ERROR: Memoria insuficiente.");
                        }
                        break;

                    // ===== V2: Finalizar proceso =====
                    case "finalizar":
                        System.out.print("Ingrese el PID a finalizar: ");
                        int pidFin = Integer.parseInt(sc.nextLine());
                        if (gestor.finalizarProceso(pidFin)) {
                            System.out.println("Proceso finalizado correctamente.");
                        } else {
                            System.out.println("Proceso no encontrado.");
                        }
                        break;

                    // ===== V2: Buscar proceso =====
                    case "buscar":
                        System.out.print("Ingrese el PID a buscar: ");
                        int pidBus = Integer.parseInt(sc.nextLine());
                        proceso encontrado = gestor.buscarProceso(pidBus);
                        if (encontrado != null) {
                            System.out.println(encontrado.mostrarProceso());
                        } else {
                            System.out.println("Proceso no encontrado.");
                        }
                        break;

                    // ===== V2: Ejecutar SJF sobre procesos =====
                    case "sjf":
                        System.out.println(gestor.ejecutarSJF());
                        break;

                    // ===== NUEVO: Ejecutar SJF clásico con PP =====
                    case "gantt":
                        // 1. Prueba crea los objetos PP
                        PP[] procesosPP = {
                            new PP(1, 0, 8),
                            new PP(2, 1, 4),
                            new PP(3, 2, 9),
                            new PP(4, 3, 5)
                        };
                        // 2. Prueba crea el SJF y le pasa los PP
                        SJF sjf = new SJF(procesosPP);
                        sjf.ordenarPorLlegada();
                        // 3. Prueba ejecuta y muestra
                        sjf.ejecutar();
                        sjf.mostrarResultados();
                        break;

                    case "memoria":
                        System.out.println(memoria.mostrarMemoria());
                        break;

                    case "archivos":
                        mostrarArchivos();
                        break;

                    case "cerrar":
                        System.out.println();
                        System.out.println("Cerrando sesión de " + kernel.getUsuarioActual() + "...");
                        kernel.cerrarSesion();
                        System.out.println("Sesión cerrada correctamente.");
                        salir = true;
                        break;

                    case "apagar":
                        System.out.println();
                        System.out.println("Finalizando procesos...");
                        System.out.println("Guardando configuración...");
                        System.out.println("Cerrando Kernel...");
                        kernel.apagar();
                        System.out.println("Sistema operativo apagado.");
                        salir = true;
                        break;

                    default:
                        System.out.println("Comando no reconocido.");
                        System.out.println("Escriba 'help' para ver los comandos disponibles.");
                        break;
                }
            }
        }

        sc.close();
    }

    public static void mostrarArchivos() {
        Archivo archivo1 = new Archivo("kernel.sys", "Sistema", 15.5, "/system");
        Archivo archivo2 = new Archivo("config.sys", "Configuración", 5.2, "/system");
        Archivo archivo3 = new Archivo("usuario.dat", "Datos", 10.8, "/users");
        Archivo archivo4 = new Archivo("explorer.exe", "Programa", 35.0, "/programs");
        Archivo archivo5 = new Archivo("documento.txt", "Documento", 2.5, "/documents");

        System.out.println();
        System.out.println("=================================================");
        System.out.println("             SISTEMA DE ARCHIVOS");
        System.out.println("=================================================");
        System.out.println("NOMBRE            TIPO          TAMAÑO    UBICACION");
        System.out.println("-------------------------------------------------");
        System.out.println(archivo1.mostrarInformacion());
        System.out.println();
        System.out.println(archivo2.mostrarInformacion());
        System.out.println();
        System.out.println(archivo3.mostrarInformacion());
        System.out.println();
        System.out.println(archivo4.mostrarInformacion());
        System.out.println();
        System.out.println(archivo5.mostrarInformacion());
        System.out.println("=================================================");
    }
}