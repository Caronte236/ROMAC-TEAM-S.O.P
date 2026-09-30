public class SJF {

    private PP[] procesos;
    private int cantidad;
    private int tiempo;
    private int terminados;
    
    //

    public SJF(int capacidad) {

        procesos = new PP[capacidad];
        cantidad = 0;
        tiempo = 0;
        terminados = 0;
    }

    public boolean agregarProceso(PP planificacion) {

        if (cantidad >= procesos.length) {
            return false;
        }

        procesos[cantidad] = planificacion;
        cantidad++;

        return true;
    }

    public void ordenarPorLlegada() {

        for (int i = 0; i < cantidad - 1; i++) {

            for (int j = 0; j < cantidad - 1 - i; j++) {

                if (procesos[j].getLlegada() >
                    procesos[j + 1].getLlegada()) {

                    PP auxiliar = procesos[j];

                    procesos[j] = procesos[j + 1];

                    procesos[j + 1] = auxiliar;
                }
            }
        }
    }

    private PP seleccionarProceso() {

        PP seleccionado = null;

        for (int i = 0; i < cantidad; i++) {

            PP actual = procesos[i];

            if (actual.yaLlego(tiempo) &&
                actual.necesitaCPU()) {

                if (seleccionado == null) {

                    seleccionado = actual;

                } else if (
                    actual.getRestante() <
                    seleccionado.getRestante()) {

                    seleccionado = actual;
                }
            }
        }

        return seleccionado;
    }

    public void ejecutar() {

        tiempo = 0;
        terminados = 0;

        System.out.println(
            "\n================================"
        );

        System.out.println(
            "      PLANIFICACION SJF"
        );

        System.out.println(
            "================================"
        );

        System.out.print("\nGantt: ");

        while (terminados < cantidad) {

            PP seleccionado =
                seleccionarProceso();

            if (seleccionado == null) {

                tiempo++;

                System.out.print("-- ");

            } else {

                proceso p =
                    seleccionado.getProceso();

                p.ejecutar();

                System.out.print(
                    "P" + p.getPID() + " "
                );

                seleccionado.consumirUnidad();

                tiempo++;

                if (!seleccionado.necesitaCPU()) {

                    seleccionado.setFin(tiempo);

                    p.terminar();

                    terminados++;

                } else {

                    p.pasarAListo();
                }
            }
        }

        System.out.println();

        System.out.println(
            "\nTiempo total: " + tiempo
        );

        System.out.println(
            "Procesos terminados: " +
            terminados
        );
    }

    public void mostrarResultados() {

        System.out.println(
            "\n=============================================="
        );

        System.out.println(
            "             RESULTADOS SJF"
        );

        System.out.println(
            "=============================================="
        );

        System.out.println(
            "Proceso\tLlegada\tRafaga\tFin\tEspera\tRetorno"
        );

        int sumaEspera = 0;
        int sumaRetorno = 0;

        for (int i = 0; i < cantidad; i++) {

            PP p = procesos[i];

            sumaEspera += p.getEspera();
            sumaRetorno += p.getRetorno();

            System.out.println(
                "P" + p.getNumero() +
                "\t" + p.getLlegada() +
                "\t" + p.getRafaga() +
                "\t" + p.getFin() +
                "\t" + p.getEspera() +
                "\t" + p.getRetorno()
            );
        }

        if (cantidad > 0) {

            System.out.println(
                "\nEspera promedio: " +
                (double) sumaEspera / cantidad
            );

            System.out.println(
                "Retorno promedio: " +
                (double) sumaRetorno / cantidad
            );
        }
    }
}