import java.util.ArrayList;
import java.util.List;

public class SJFE {

    private final AdministradorProcesos admin;
    private final List<String> gantt = new ArrayList<>();
    private int tiempo;

    public SJFE(AdministradorProcesos a) {
        this.admin = a;
    }

    // =========================================================
    // SJF EXPROPIATIVO
    // =========================================================
    public void ejecutar() {

        int n = admin.getCantidad();
        if (n == 0) return;

        tiempo = 0;
        gantt.clear();
        Proceso actual = null;

        // Contar los que ya estaban terminados (por si se re-ejecuta)
        int terminados = 0;
        for (int i = 0; i < n; i++) {
            if (admin.getProceso(i).getEstado() == Proceso.estado.terminado) {
                terminados++;
            }
        }

        while (terminados < n) {

            // 1) Admitir procesos cuya llegada ya ocurrió
            for (int i = 0; i < n; i++) {
                Proceso p = admin.getProceso(i);
                if (p.getEstado() == Proceso.estado.nuevo && p.getLlegada() <= tiempo) {
                    p.pasarAListo(tiempo);
                }
            }

            // 2) Buscar el más corto entre los LISTOS
            Proceso candidato = null;
            for (int i = 0; i < n; i++) {
                Proceso p = admin.getProceso(i);
                if (p.getEstado() == Proceso.estado.listo && p.getRestante() > 0) {
                    if (candidato == null || p.getRestante() < candidato.getRestante()) {
                        candidato = p;
                    }
                }
            }

            // 3) Decidir quién ejecuta (con expropiación si aplica)
            if (actual == null) {
                if (candidato != null) {
                    actual = candidato;
                    actual.ejecutar(tiempo);
                }
            } else {
                if (candidato != null
                        && candidato != actual
                        && candidato.getRestante() < actual.getRestante()) {
                    actual.regresarAListo(tiempo);
                    actual = candidato;
                    actual.ejecutar(tiempo);
                }
            }

            // 4) Consumir una unidad de tiempo
            if (actual != null) {
                gantt.add(actual.getNombre());
                actual.ejecutarUnidad();
                tiempo++;

                if (actual.haTerminado()) {
                    actual.terminar(tiempo);
                    terminados++;
                    actual = null;
                }
            } else {
                gantt.add("IDLE");
                tiempo++;
            }
        }
    }

    // =========================================================
    // GANTT
    // =========================================================
    public String mostrarGantt() {

        if (gantt.isEmpty()) return "\nDIAGRAMA DE GANTT\n\n(sin ejecuciones)\n";

        // Agrupar unidades consecutivas del mismo proceso
        List<String>  nombres = new ArrayList<>();
        List<Integer> inicios = new ArrayList<>();
        List<Integer> fines   = new ArrayList<>();

        String act = gantt.get(0);
        int ini = 0;
        for (int i = 1; i <= gantt.size(); i++) {
            if (i == gantt.size() || !gantt.get(i).equals(act)) {
                nombres.add(act);
                inicios.add(ini);
                fines.add(i);
                if (i < gantt.size()) {
                    act = gantt.get(i);
                    ini = i;
                }
            }
        }

        // Ancho uniforme por celda para alinear los "|"
        int maxLen = 4; // "IDLE"
        for (String s : nombres) if (s.length() > maxLen) maxLen = s.length();
        int ancho = maxLen + 3;  // "| " + nombre + relleno

        // Barra de procesos
        StringBuilder barra = new StringBuilder();
        for (String s : nombres) {
            String celda = "| " + s;
            while (celda.length() < ancho) celda += " ";
            barra.append(celda);
        }
        barra.append("|");

        // Números alineados justo debajo de cada "|"
        StringBuilder nums = new StringBuilder();
        for (int i = 0; i <= nombres.size(); i++) {
            int pos = i * ancho;
            while (nums.length() < pos) nums.append(' ');
            nums.append(i < inicios.size() ? inicios.get(i) : tiempo);
        }

        return "\nDIAGRAMA DE GANTT\n\n" + barra + "\n" + nums + "\n";
    }

    // =========================================================
    // RESULTADOS
    // =========================================================
    public String mostrarResultados() {

        StringBuilder sb = new StringBuilder();
        sb.append("\nRESULTADOS DE SJF\n\n");
        sb.append(String.format("%-6s %-9s %-8s %-6s %-8s %-8s%n",
                "PID", "Llegada", "Ráfaga", "Fin", "Espera", "Retorno"));

        int n = admin.getCantidad();
        double sumaE = 0, sumaR = 0;

        for (int i = 0; i < n; i++) {
            Proceso p = admin.getProceso(i);
            sb.append(String.format("%-6s %-9d %-8d %-6d %-8d %-8d%n",
                    p.getNombre(), p.getLlegada(), p.getRafaga(),
                    p.getFin(), p.getEspera(), p.getRetorno()));
            sumaE += p.getEspera();
            sumaR += p.getRetorno();
        }

        if (n > 0) {
            sb.append(String.format("%nPromedio de espera:  %.2f%n", sumaE / n));
        }
        return sb.toString();
    }

    public int getTiempo() { return tiempo; }
}