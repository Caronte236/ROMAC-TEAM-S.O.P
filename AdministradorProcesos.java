public class AdministradorProcesos {

    private proceso[] procesos;
    private int cantidad;
    private int siguientePID;

    public AdministradorProcesos(int capacidad) {

        procesos = new proceso[capacidad];

        cantidad = 0;
        siguientePID = 1;
    }

    // -----------------------------
    // CREAR PROCESO
    // -----------------------------

    public proceso crearProceso(String nombre,
                                boolean prioridad,
                                int llegada,
                                int rafaga) {

        if (cantidad >= procesos.length) {
            return null;
        }

        proceso p = new proceso(
                siguientePID,
                nombre,
                prioridad,
                llegada,
                rafaga
        );

        procesos[cantidad] = p;

        cantidad++;
        siguientePID++;

        return p;
    }

    // -----------------------------
    // BUSCAR PROCESO
    // -----------------------------

    public proceso buscarProceso(int PID) {

        for (int i = 0; i < cantidad; i++) {

            if (procesos[i].getPID() == PID) {
                return procesos[i];
            }
        }

        return null;
    }

    // -----------------------------
    // MOSTRAR PROCESOS
    // -----------------------------

    public String mostrarProcesos() {

        String texto = "";

        for (int i = 0; i < cantidad; i++) {

            texto += procesos[i].mostrarProceso();
            texto += "\n----------------------\n";
        }

        return texto;
    }

    // -----------------------------
    // FINALIZAR PROCESO
    // -----------------------------

    public boolean finalizarProceso(int PID, int tiempo) {

        proceso p = buscarProceso(PID);

        if (p == null) {
            return false;
        }

        if (p.getEstado() == proceso.estado.ejecutando
                && p.haTerminado()) {

            p.terminar(tiempo);

            return true;
        }

        return false;
    }

    // -----------------------------
    // PONER EN ESPERA
    // -----------------------------

    public boolean bloquearProceso(int PID, int tiempo) {

        proceso p = buscarProceso(PID);

        if (p == null) {
            return false;
        }

        if (p.getEstado() == proceso.estado.ejecutando) {

            p.ponerEnEspera(tiempo);

            return true;
        }

        return false;
    }

    // -----------------------------
    // REGRESAR DE ESPERA
    // -----------------------------

    public boolean desbloquearProceso(int PID, int tiempo) {

        proceso p = buscarProceso(PID);

        if (p == null) {
            return false;
        }

        if (p.getEstado() == proceso.estado.espera) {

            p.pasarAListo(tiempo);

            return true;
        }

        return false;
    }

    // -----------------------------
    // ELIMINAR PROCESO
    // -----------------------------

    public boolean eliminarProceso(int PID) {

        for (int i = 0; i < cantidad; i++) {

            if (procesos[i].getPID() == PID) {

                for (int j = i; j < cantidad - 1; j++) {
                    procesos[j] = procesos[j + 1];
                }

                procesos[cantidad - 1] = null;

                cantidad--;

                return true;
            }
        }

        return false;
    }

    // -----------------------------
    // GETTERS
    // -----------------------------

    public int getCantidad() {
        return cantidad;
    }

    public proceso getProceso(int posicion) {

        if (posicion >= 0 && posicion < cantidad) {
            return procesos[posicion];
        }

        return null;
    }
}