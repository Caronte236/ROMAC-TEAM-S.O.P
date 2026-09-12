public class proceso{
    
    private int PID;
    private String nombre;
    private boolean prioridad;
    private estado est;
    
    public enum estado{
        nuevo,listo,espera, ejecutando, terminado;
    }

    public proceso (int p, String n, boolean pri, estado e){
        this.PID= p;
        this.nombre= n;
        this.prioridad= pri;
        this.est= e;
    }
    
    public void setPID(int p){
        this.PID=p;
    }
    
    public int getPID(){
        return PID;
    }
    
    public void setNombre(String n){
        this.nombre= n;
    }
    
    public String getNombre(){
        return nombre;
    }

    
    public void setPrioridad(boolean pri){
        this.prioridad= pri;
    }
    
    public boolean getPrioridad(){
        return prioridad;
    }
    
    public void setEstado(estado e){
        this.est= e;
    }
    public estado getEstado(){
        return est;
    }
    
    public String mostrarProceso(){
        return "PID:" + PID + 
        "\n Nombre:" + nombre +
        "\n Prioridad:" + prioridad +
        "\n Estado:" + est;
    }
}