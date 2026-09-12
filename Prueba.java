import java.util.Scanner;

public class Prueba {

    public static void main(String[] args) {

       Scanner sc = new Scanner(System.in);
        Bios bios = new Bios();
        sistemaOperativo so = new sistemaOperativo("ROMAC", "1.0");
        Kernel kernel = new Kernel();
        Memoria memoria = new Memoria(1000,50);
        
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


        proceso p1 = new proceso(
            1,
            "Kernel",
            true,
            proceso.estado.ejecutando
        );
        proceso p2 = new proceso(
            2,
            "Sistema",
            true,
            proceso.estado.espera
        );

        proceso p3 = new proceso(
            3,
            "Explorador",
            false,
            proceso.estado.listo
        );

        proceso p4 = new proceso(
            4,
            "Servicios",
            false,
            proceso.estado.espera
        );  

        System.out.println();
        System.out.println("=================================");
        System.out.println("             INICIO");
        System.out.println("=================================");

        System.out.println(
            "Usuario predeterminado: admin"
        );

        System.out.println(
            "Contraseña predeterminada: admin"
        );

        boolean iniciado = false;

        while (!iniciado && kernel.estaEncendido()) {

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

                System.out.println(
                    "Usuario o contraseña incorrectos."
                );
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
                memoria       - Información de memoria
                archivos      - Sistema de archivos
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

                   

                    case "procesos":
                        
                        System.out.println(p1.mostrarProceso());
                        System.out.println(p2.mostrarProceso());
                        System.out.println(p3.mostrarProceso());
                        System.out.println(p4.mostrarProceso());
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
        }

        sc.close();
    }


    
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