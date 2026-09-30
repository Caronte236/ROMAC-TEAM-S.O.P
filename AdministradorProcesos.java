public class AdministradorProcesos {

    private proceso[] procesos;
    private int cantidad;
    private int siguientePID;

    public AdministradorProcesos(int capacidad) {

        procesos = new proceso[capacidad];

        cantidad = 0;
        siguientePID = 1;
    }

    public proceso crearProceso(
        String nombre,
        boolean prioridad,
        int llegada,
        int rafaga) {

        if (cantidad >= procesos.length) {

            System.out.println(
                "No hay espacio para crear otro proceso."
            );

            return null;
        }

        proceso nuevo = new proceso(
            siguientePID,
            nombre,
            prioridad,
            rafaga
        );

        procesos[cantidad] = nuevo;

        cantidad++;

        siguientePID++;

        return nuevo;
    }

    public PP crearPlanificacion(
        proceso p,
        int llegada,
        int rafaga) {

        return new PP(
            p,
            llegada,
            rafaga
        );
    }

    public void enviarProcesosAListos() {

        for (int i = 0; i < cantidad; i++) {

            if (procesos[i].getEstado() ==
                proceso.estado.nuevo) {

                procesos[i].pasarAListo();
            }
        }
    }

    public proceso buscarProceso(int pid) {

        for (int i = 0; i < cantidad; i++) {

            if (procesos[i].getPID() == pid) {

                return procesos[i];
            }
        }

        return null;
    }

    public void mostrarProcesos() {

        System.out.println(
            "\n================================"
        );

        System.out.println(
            "       PROCESOS DEL SISTEMA"
        );

        System.out.println(
            "================================"
        );

        if (cantidad == 0) {

            System.out.println(
                "No existen procesos."
            );

            return;
        }

        for (int i = 0; i < cantidad; i++) {

            System.out.println(
                procesos[i].mostrarProceso()
            );

            System.out.println(
                "--------------------------------"
            );
        }
    }

    public void finalizarProceso(int pid) {

        proceso p = buscarProceso(pid);

        if (p != null) {

            p.terminar();

            System.out.println(
                "Proceso P" + pid +
                " finalizado."
            );

        } else {

            System.out.println(
                "No se encontró el proceso."
            );
        }
    }

    public void eliminarProceso(int pid) {

        for (int i = 0; i < cantidad; i++) {

            if (procesos[i].getPID() == pid) {

                for (int j = i;
                     j < cantidad - 1;
                     j++) {

                    procesos[j] =
                        procesos[j + 1];
                }

                procesos[cantidad - 1] = null;

                cantidad--;

                System.out.println(
                    "Proceso P" + pid +
                    " eliminado del sistema."
                );

                return;
            }
        }

        System.out.println(
            "No se encontró el proceso."
        );
    }

    public int getCantidad() {
        return cantidad;
    }

    public proceso getProceso(int posicion) {

        if (posicion >= 0 &&
            posicion < cantidad) {

            return procesos[posicion];
        }

        return null;
    }
}