public class PP {
    private int numero;
    private int llegada;
    private int rafaga;
    private int restante;
    private int fin;

    public PP(int numero, int llegada, int rafaga) {
        this.numero  = numero;
        this.llegada = llegada;
        this.rafaga  = rafaga;
        this.restante = rafaga;
    }

    public int getNumero()   { return numero; }
    public int getLlegada()  { return llegada; }
    public int getRafaga()   { return rafaga; }
    public int getRestante() { return restante; }
    public int getFin()      { return fin; }

    public void setRestante(int r) { this.restante = r; }
    public void setFin(int f)      { this.fin = f; }

    // Cálculos útiles
    public int getEspera()  { return fin - llegada - rafaga; }
    public int getRetorno() { return fin - llegada; }
}