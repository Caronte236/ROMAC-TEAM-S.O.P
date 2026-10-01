public class SJFE {

    private AdministradorProcesos administrador;

    private int tiempo;
    private String gantt;

    public SJFE(AdministradorProcesos a) {

        administrador = a;
        tiempo = 0;
        gantt = "";
    }

    // -----------------------------
    // BUSCAR PROCESO MÁS CORTO
    // -----------------------------

    private Proceso buscarMasCorto() {

        Proceso elegido = null;

        for (int i = 0; i < administrador.getCantidad(); i++) {

            Proceso p = administrador.getProceso(i);

            if (p.getEstado() == Proceso.estado.listo
                    && p.getRestante() > 0) {

                if (elegido == null) {

                    elegido = p;

                } else if (p.getRestante()
                        < elegido.getRestante()) {

                    elegido = p;
                }
            }
        }

        return elegido;
    }

    // -----------------------------
    // ADMITIR PROCESOS
    // -----------------------------

    private void admitirProcesos() {

        for (int i = 0;
             i < administrador.getCantidad();
             i++) {

            Proceso p = administrador.getProceso(i);

            if (p.getEstado() == Proceso.estado.nuevo
                    && p.getLlegada() <= tiempo) {

                p.pasarAListo(tiempo);
            }
        }
    }

    // -----------------------------
    // EJECUTAR SJF
    // -----------------------------

    public void ejecutar() {

        Proceso actual = null;

        int terminados = 0;

        tiempo = 0;

        gantt = "";

        int totalProcesos = administrador.getCantidad();

        while (terminados < totalProcesos) {

            // Llegan nuevos procesos
            admitirProcesos();

            // Buscar proceso más corto
            Proceso candidato = buscarMasCorto();

            // ---------------------------------
            // SI NO HAY PROCESOS LISTOS
            // ---------------------------------

            if (actual == null) {

                if (candidato != null) {

                    actual = candidato;

                    actual.ejecutar(tiempo);
                }

            } else {

                // ---------------------------------
                // VERIFICAR SI HAY PREEMPCIÓN
                // ---------------------------------

                if (candidato != null
                        && candidato != actual
                        && candidato.getRestante()
                        < actual.getRestante()) {

                    actual.regresarAListo(tiempo);

                    actual = candidato;

                    actual.ejecutar(tiempo);
                }
            }

            // ---------------------------------
            // EJECUTAR UNA UNIDAD DE TIEMPO
            // ---------------------------------

            if (actual != null) {

                agregarGantt(actual.getPID(), tiempo);

                actual.ejecutarUnidad();

                tiempo++;

                // ---------------------------------
                // TERMINÓ
                // ---------------------------------

                if (actual.haTerminado()) {

                    actual.terminar(tiempo);

                    terminados++;

                    actual = null;
                }

            } else {

                // CPU sin proceso
                agregarGantt(0, tiempo);

                tiempo++;
            }
        }
    }

    // -----------------------------
    // CONSTRUIR GANTT
    // -----------------------------

    private void agregarGantt(int PID, int t) {

        if (PID == 0) {

            gantt += "| IDLE ";

        } else {

            gantt += "| P" + PID + " ";
        }
    }

    // -----------------------------
    // MOSTRAR GANTT
    // -----------------------------

    public String mostrarGantt() {

        String resultado = "\nDIAGRAMA DE GANTT\n\n";

        resultado += gantt;
        resultado += "|\n";

        resultado += "Tiempo: ";

        for (int i = 0; i <= tiempo; i++) {

            resultado += i + " ";
        }

        resultado += "\n";

        return resultado;
    }

    // -----------------------------
    // MOSTRAR RESULTADOS
    // -----------------------------

    public String mostrarResultados() {

        String resultado = "";

        double esperaPromedio = 0;
        double retornoPromedio = 0;

        resultado += "\nRESULTADOS DE SJF\n\n";

        resultado += "PID\tLlegada\tRáfaga\tFin\tEspera\tRetorno\t \n";

        for (int i = 0;
             i < administrador.getCantidad();
             i++) {

            Proceso p = administrador.getProceso(i);

            resultado += "P" + p.getPID()
                    + "\t"
                    + p.getLlegada()
                    + "\t"
                    + p.getRafaga()
                    + "\t"
                    + p.getFin()
                    + "\t"
                    + p.getEspera()
                    + "\t"
                    + p.getRetorno()
                    + "\t"
                    + "\n";

            esperaPromedio += p.getEspera();
            retornoPromedio += p.getRetorno();
           
        }

        int n = administrador.getCantidad();

        if (n > 0) {

            esperaPromedio /= n;
            retornoPromedio /= n;
        }

        resultado += "\nPromedio de espera: "
                + esperaPromedio;

        resultado += "\nPromedio de retorno: "
                + retornoPromedio;

        return resultado;
    }

    public int getTiempo() {
        return tiempo;
    }
}