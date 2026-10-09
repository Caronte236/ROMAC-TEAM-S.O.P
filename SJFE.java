import java.util.ArrayList;
import java.util.List;

public class SJFE {

    private AdministradorProcesos administrador;
    private int tiempo;

    // Cada elemento guarda: {indiceProceso, tiempoInicio}
    // indiceProceso = -1 significa IDLE
    private List<int[]> segmentos;

    public SJFE(AdministradorProcesos a) {
        administrador = a;
        tiempo = 0;
        segmentos = new ArrayList<>();
    }

    // -----------------------------
    // BUSCAR PROCESO MÁS CORTO
    // -----------------------------
    private Proceso buscarMasCorto() {
        Proceso elegido = null;
        for (int i = 0; i < administrador.getCantidad(); i++) {
            Proceso p = administrador.getProceso(i);
            if (p.getEstado() == Proceso.estado.listo && p.getRestante() > 0) {
                if (elegido == null || p.getRestante() < elegido.getRestante()) {
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
        for (int i = 0; i < administrador.getCantidad(); i++) {
            Proceso p = administrador.getProceso(i);
            if (p.getEstado() == Proceso.estado.nuevo && p.getLlegada() <= tiempo) {
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
        segmentos = new ArrayList<>();

        int totalProcesos = administrador.getCantidad();

        while (terminados < totalProcesos) {

            admitirProcesos();
            Proceso candidato = buscarMasCorto();

            if (actual == null) {
                if (candidato != null) {
                    actual = candidato;
                    actual.ejecutar(tiempo);
                }
            } else {
                if (candidato != null && candidato != actual
                        && candidato.getRestante() < actual.getRestante()) {
                    actual.regresarAListo(tiempo);
                    actual = candidato;
                    actual.ejecutar(tiempo);
                }
            }

            if (actual != null) {
                int idx = indiceDe(actual);
                segmentos.add(new int[]{idx, tiempo});
                actual.ejecutarUnidad();
                tiempo++;

                if (actual.haTerminado()) {
                    actual.terminar(tiempo);
                    terminados++;
                    actual = null;
                }
            } else {
                segmentos.add(new int[]{-1, tiempo});
                tiempo++;
            }
        }
    }

    private int indiceDe(Proceso p) {
        for (int i = 0; i < administrador.getCantidad(); i++) {
            if (administrador.getProceso(i) == p) return i;
        }
        return -1;
    }

    // -----------------------------
    // MOSTRAR GANTT (ALINEADO)
    // -----------------------------
    public String mostrarGantt() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nDIAGRAMA DE GANTT\n\n");

        if (segmentos.isEmpty()) {
            sb.append("(sin ejecuciones)\n");
            return sb.toString();
        }

        // Ancho fijo por celda, para que TODOS los "|" queden alineados
        int maxLen = 4; // mínimo para "IDLE"
        for (int i = 0; i < administrador.getCantidad(); i++) {
            int l = administrador.getProceso(i).getNombre().length();
            if (l > maxLen) maxLen = l;
        }
        // Celda = "| " + nombre + relleno  → ancho = maxLen + 3
        // Con nombres "P1".."P4": maxLen=2 → ancho=5 ("| P1 ")
        int ancho = maxLen + 3;

        // ---------- Barra ----------
        StringBuilder barra = new StringBuilder();
        for (int[] seg : segmentos) {
            String nombre = (seg[0] == -1)
                    ? "IDLE"
                    : administrador.getProceso(seg[0]).getNombre();

            String celda = "| " + nombre;
            while (celda.length() < ancho) celda += " ";
            barra.append(celda);
        }
        barra.append("|");

        // ---------- Números alineados ----------
        // El i-ésimo pipe está en la posición i * ancho
        // Ponemos el número i justo debajo de ese pipe
        StringBuilder nums = new StringBuilder();
        int totalPipes = segmentos.size() + 1;
        for (int i = 0; i < totalPipes; i++) {
            int pos = i * ancho;
            while (nums.length() < pos) nums.append(' ');
            nums.append(i);
        }

        sb.append(barra).append("\n");
        sb.append(nums).append("\n");

        return sb.toString();
    }

    // -----------------------------
    // MOSTRAR RESULTADOS
    // -----------------------------
    public String mostrarResultados() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nRESULTADOS DE SJF\n\n");

        sb.append(String.format("%-6s %-9s %-8s %-6s %-8s %-8s%n",
                "PID", "Llegada", "Ráfaga", "Fin", "Espera", "Retorno"));

        double esperaPromedio = 0;
        double retornoPromedio = 0;

        for (int i = 0; i < administrador.getCantidad(); i++) {
            Proceso p = administrador.getProceso(i);
            sb.append(String.format("%-6s %-9d %-8d %-6d %-8d %-8d%n",
                    p.getNombre(),
                    p.getLlegada(),
                    p.getRafaga(),
                    p.getFin(),
                    p.getEspera(),
                    p.getRetorno()));
            esperaPromedio += p.getEspera();
            retornoPromedio += p.getRetorno();
        }

        int n = administrador.getCantidad();
        if (n > 0) {
            esperaPromedio /= n;
            retornoPromedio /= n;
        }

        sb.append(String.format("%nPromedio de espera: %.2f%n", esperaPromedio));
        sb.append(String.format("Promedio de retorno: %.2f%n", retornoPromedio));

        return sb.toString();
    }

    public int getTiempo() {
        return tiempo;
    }
}