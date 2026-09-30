public class proceso {

    private int PID;
    private String nombre;
    private boolean prioridad;
    private estado est;

    public enum estado {
        nuevo,
        listo,
        espera,
        ejecutando,
        terminado
    }

    public proceso(int p, String n, boolean pri, int unidadesTrabajo) {

        this.PID = p;
        this.nombre = n;
        this.prioridad = pri;
        this.est = estado.nuevo;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int p) {
        PID = p;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        nombre = n;
    }

    public boolean getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(boolean pri) {
        prioridad = pri;
    }

    public estado getEstado() {
        return est;
    }

    public void setEstado(estado e) {
        est = e;
    }

    public void pasarAListo() {
        est = estado.listo;
    }

    public void ejecutar() {
        est = estado.ejecutando;
    }

    public void esperar() {
        est = estado.espera;
    }

    public void terminar() {
        est = estado.terminado;
    }

    public boolean estaTerminado() {
        return est == estado.terminado;
    }

    public String mostrarProceso() {

        return "PID: " + PID +
               "\nNombre: " + nombre +
               "\nPrioridad: " + prioridad +
               "\nEstado: " + est;
    }
}