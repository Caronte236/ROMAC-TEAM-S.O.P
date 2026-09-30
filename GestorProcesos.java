import java.util.ArrayList;
import java.util.List;

public class GestorProcesos {

    private List<proceso> colaProcesos;
    private int contadorPID;
    private Memoria memoria;

    public GestorProcesos(Memoria memoria) {
        this.colaProcesos = new ArrayList<>();
        this.contadorPID = 5; // Ya existen 4 procesos (1-4)
        this.memoria = memoria;
    }

    public proceso crearProceso(String nombre, boolean prioridad, int memReq, int cpu, int es, proceso padre) {
        // Verificar memoria disponible
        if (memReq > memoria.getMemoriaDisponible()) {
            return null; // no hay memoria
        }
        proceso nuevo = new proceso(contadorPID, nombre, prioridad, proceso.estado.nuevo, memReq, cpu, es, padre);
        contadorPID++;
        colaProcesos.add(nuevo);
        nuevo.setEstado(proceso.estado.listo); // NUEVO -> LISTO
        return nuevo;
    }

    public boolean finalizarProceso(int pid) {
        proceso p = buscarProceso(pid);
        if (p != null) {
            p.setEstado(proceso.estado.terminado); // -> TERMINADO
            return true;
        }
        return false;
    }

    public proceso buscarProceso(int pid) {
        for (proceso p : colaProcesos) {
            if (p.getPID() == pid) return p;
        }
        return null;
    }

    public String listarProcesos() {
        if (colaProcesos.isEmpty()) return "No hay procesos en el sistema.";
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== LISTA DE PROCESOS =====\n");
        for (proceso p : colaProcesos) {
            sb.append(p.mostrarProceso()).append("\n");
            sb.append("-----------------------------\n");
        }
        return sb.toString();
    }

    // Cambio de estado explícito (útil para demostrar transiciones)
    public String cambiarEstado(int pid, proceso.estado nuevoEstado) {
        proceso p = buscarProceso(pid);
        if (p == null) return "Proceso no encontrado.";
        proceso.estado anterior = p.getEstado();
        p.setEstado(nuevoEstado);
        return "Proceso " + p.getNombre() + ": " + anterior + " -> " + nuevoEstado;
    }

    // SJF sobre los procesos en estado LISTO
    public String ejecutarSJF() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== PLANIFICACIÓN SJF =====\n");

        List<proceso> listos = new ArrayList<>();
        for (proceso p : colaProcesos) {
            if (p.getEstado() == proceso.estado.listo) {
                listos.add(p);
            }
        }

        if (listos.isEmpty()) return "No hay procesos LISTOS para ejecutar.";

        // Ordenar por tiempoCPU (SJF)
        listos.sort((a, b) -> Integer.compare(a.getTiempoCPU(), b.getTiempoCPU()));

        for (proceso p : listos) {
            p.setEstado(proceso.estado.ejecutando); // LISTO -> EJECUTANDO
            sb.append("Ejecutando: ").append(p.getNombre())
              .append(" (PID: ").append(p.getPID())
              .append(") - CPU: ").append(p.getTiempoCPU()).append(" ms\n");
            p.setEstado(proceso.estado.terminado); // EJECUTANDO -> TERMINADO
            sb.append("Proceso ").append(p.getNombre()).append(" finalizado.\n");
        }
        return sb.toString();
    }

    public List<proceso> getColaProcesos() { return colaProcesos; }
    public int getContadorPID() { return contadorPID; }
}