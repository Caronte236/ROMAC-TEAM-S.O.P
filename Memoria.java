public class Memoria {
    
    private int memoriaTotal;
    private int memoriaOcupada;
    
    public Memoria(int total, int ocupada) {
        memoriaTotal = total;
        memoriaOcupada = ocupada;
    }
    
    public int getMemoriaTotal() {
        return memoriaTotal;
    }
    
    public int getMemoriaOcupada() {
        return memoriaOcupada;
    }
    
    public int getMemoriaDisponible() {
        return memoriaTotal - memoriaOcupada;
    }
    
    public double getPorcentajeUso() {
        return ((double) memoriaOcupada / memoriaTotal) * 100;
    }
    
    public String mostrarMemoria() {
        return "Memoria total: " + memoriaTotal + " GB" +
               "\nMemoria ocupada: " + memoriaOcupada + " GB" +
               "\nMemoria disponible: " + getMemoriaDisponible() + " GB" +
               "\nUso de memoria: " + getPorcentajeUso() + "%";
    }
}