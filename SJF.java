public class SJF {
    private PP[] procesos;
    private int n;

    public SJF(PP[] procesos) {
        this.procesos = procesos;
        this.n = procesos.length;
    }

    // Ordenar por llegada (menor a mayor)
    public void ordenarPorLlegada() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (procesos[j].getLlegada() > procesos[j + 1].getLlegada()) {
                    PP aux = procesos[j];
                    procesos[j] = procesos[j + 1];
                    procesos[j + 1] = aux;
                }
            }
        }
    }

    // Ejecutar SJF expropiativo (SRTF)
    public void ejecutar() {
        int tiempo = 0;
        int terminados = 0;

        System.out.print("Gantt: ");
        while (terminados < n) {

            // Buscar el proceso con menor restante que ya haya llegado
            int elegido = -1;
            for (int i = 0; i < n; i++) {
                if (procesos[i].getLlegada() <= tiempo && procesos[i].getRestante() > 0) {
                    if (elegido == -1 || procesos[i].getRestante() < procesos[elegido].getRestante()) {
                        elegido = i;
                    }
                }
            }

            // Nadie ha llegado todavía
            if (elegido == -1) {
                tiempo++;
                continue;
            }

            // Ejecutar 1 unidad
            System.out.print("P" + procesos[elegido].getNumero() + " ");
            procesos[elegido].setRestante(procesos[elegido].getRestante() - 1);
            tiempo++;

            // Si terminó
            if (procesos[elegido].getRestante() == 0) {
                procesos[elegido].setFin(tiempo);
                terminados++;
            }
        }
        System.out.println();
    }

    // Mostrar la tabla de resultados
    public void mostrarResultados() {
        System.out.println("\nProceso  Llegada  Rafaga  Fin  Espera  Retorno");
        int sumaEspera = 0, sumaRetorno = 0;

        for (PP p : procesos) {
            sumaEspera  += p.getEspera();
            sumaRetorno += p.getRetorno();
            System.out.println("   P" + p.getNumero() + "       " +
                    p.getLlegada() + "        " + p.getRafaga() + "      " +
                    p.getFin() + "     " + p.getEspera() + "       " + p.getRetorno());
        }

        System.out.println("\nEspera promedio:  " + (double) sumaEspera / n);
        System.out.println("Retorno promedio: " + (double) sumaRetorno / n);
    }
}