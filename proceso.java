import java.util.ArrayList;
import java.util.List;

public class proceso {

    private int PID;
    private String nombre;
    private boolean prioridad;
    private estado est;
    
    // ===== NUEVOS ATRIBUTOS V2 =====
    private int memoriaRequerida;
    private int tiempoCPU;
    private int operacionesES;
    private proceso padre;
    private List<proceso> hijos;

    public enum estado {
        nuevo, listo, espera, ejecutando, terminado;
    }

    // Constructor original (compatible con V1)
    public proceso(int p, String n, boolean pri, estado e) {
        this.PID = p;
        this.nombre = n;
        this.prioridad = pri;
        this.est = e;
        this.memoriaRequerida = 0;
        this.tiempoCPU = 0;
        this.operacionesES = 0;
        this.padre = null;
        this.hijos = new ArrayList<>();
    }

    // ===== NUEVO CONSTRUCTOR V2 =====
    public proceso(int p, String n, boolean pri, estado e, int mem, int cpu, int es, proceso padre) {
        this.PID = p;
        this.nombre = n;
        this.prioridad = pri;
        this.est = e;
        this.memoriaRequerida = mem;
        this.tiempoCPU = cpu;
        this.operacionesES = es;
        this.padre = padre;
        this.hijos = new ArrayList<>();
        if (padre != null) padre.agregarHijo(this);
    }

    // Getters y Setters originales
    public void setPID(int p) { this.PID = p; }
    public int getPID() { return PID; }
    public void setNombre(String n) { this.nombre = n; }
    public String getNombre() { return nombre; }
    public void setPrioridad(boolean pri) { this.prioridad = pri; }
    public boolean getPrioridad() { return prioridad; }
    public void setEstado(estado e) { this.est = e; }
    public estado getEstado() { return est; }

    // ===== NUEVOS GETTERS Y SETTERS V2 =====
    public int getMemoriaRequerida() { return memoriaRequerida; }
    public void setMemoriaRequerida(int m) { this.memoriaRequerida = m; }
    public int getTiempoCPU() { return tiempoCPU; }
    public void setTiempoCPU(int t) { this.tiempoCPU = t; }
    public int getOperacionesES() { return operacionesES; }
    public void setOperacionesES(int e) { this.operacionesES = e; }
    public proceso getPadre() { return padre; }
    public void setPadre(proceso p) { this.padre = p; }
    public List<proceso> getHijos() { return hijos; }
    public void agregarHijo(proceso hijo) { this.hijos.add(hijo); }

    // Método mostrarProceso ampliado
    public String mostrarProceso() {
        String infoPadre = (padre != null) ? padre.getNombre() : "Ninguno";
        return "PID: " + PID +
               "\n Nombre: " + nombre +
               "\n Prioridad: " + (prioridad ? "Alta" : "Baja") +
               "\n Estado: " + est +
               "\n Memoria: " + memoriaRequerida + " MB" +
               "\n CPU: " + tiempoCPU + " ms" +
               "\n E/S: " + operacionesES +
               "\n Padre: " + infoPadre +
               "\n Hijos: " + hijos.size();
    }
}