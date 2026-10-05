public class Proceso {

    
    public enum estado {
        nuevo,
        listo,
        ejecutando,
        espera,
        terminado
    }

    private int PID;
    private String nombre;
    private boolean prioridad;

    // Datos para planificación
    private int llegada;
    private int rafaga;
    private int restante;
    private int inicio;
    private int fin;

    private estado est;

    private String historial;

    public Proceso(int p, String n, boolean pri, int l, int r) {

        PID = p;
        nombre = n;
        prioridad = pri;

        llegada = l;
        rafaga = r;
        restante = r;

        inicio = -1;
        fin = -1;

        est = estado.nuevo;

        historial = "";
    }

    // -----------------------------
    // DATOS DEL PROCESO
    // -----------------------------

    public int getPID() {
        return PID;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean getPrioridad() {
        return prioridad;
    }

    public int getLlegada() {
        return llegada;
    }

    public int getRafaga() {
        return rafaga;
    }

    public int getRestante() {
        return restante;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }

    public estado getEstado() {
        return est;
    }

    // -----------------------------
    // CAMBIOS DE ESTADO
    // -----------------------------

    private void cambiarEstado(estado nuevo, int tiempo) {

        historial += "Tiempo " + tiempo + ": "
                + est + " -> " + nuevo + "\n";

        est = nuevo;
    }

    public void pasarAListo(int tiempo) {

        if (est == estado.nuevo || est == estado.espera) {
            cambiarEstado(estado.listo, tiempo);
        }
    }

    public void ejecutar(int tiempo) {

        if (est == estado.listo) {

            if (inicio == -1) {
                inicio = tiempo;
            }

            cambiarEstado(estado.ejecutando, tiempo);
        }
    }

    public void regresarAListo(int tiempo) {

        if (est == estado.ejecutando) {
            cambiarEstado(estado.listo, tiempo);
        }
    }

    public void ponerEnEspera(int tiempo) {

        if (est == estado.ejecutando) {
            cambiarEstado(estado.espera, tiempo);
        }
    }

    public void ejecutarUnidad() {

        if (est == estado.ejecutando && restante > 0) {
            restante--;
        }
    }

    public void terminar(int tiempo) {

        if (restante == 0 && est == estado.ejecutando) {

            fin = tiempo;

            cambiarEstado(estado.terminado, tiempo);
        }
    }

    // -----------------------------
    // INFORMACIÓN
    // -----------------------------

    public boolean haTerminado() {
        return restante == 0;
    }

    public int getRetorno() {
        if (fin == -1) {
            return 0;
        }

        return fin - llegada;
    }

    public int getEspera() {
        if (fin == -1) {
            return 0;
        }

        return getRetorno() - rafaga;
    }

    public int getRespuesta() {
        if (inicio == -1) {
            return 0;
        }

        return inicio - llegada;
    }

    public String getHistorial() {
        return historial;
    }

    public String mostrarProceso() {

        return "PID: " + PID
                + "\nNombre: " + nombre
                + "\nPrioridad: " + prioridad
                + "\nEstado: " + est
                + "\nLlegada: " + llegada
                + "\nRáfaga: " + rafaga
                + "\nRestante: " + restante;
    }
}