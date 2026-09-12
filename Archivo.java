public class Archivo {
    
    private String nombre;
    private String tipo;
    private double tamaño;
    private String ubicacion;
    
    public Archivo(String n, String t, double tam, String u) {
        nombre = n;
        tipo = t;
        tamaño = tam;
        ubicacion = u;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public double getTamaño() {
        return tamaño;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public String mostrarInformacion() {
        return "Nombre: " + nombre +
               "\nTipo: " + tipo +
               "\nTamaño: " + tamaño + " MB" +
               "\nUbicacion: " + ubicacion;
    }
}