public class PP {

    private proceso proceso;
    private int llegada;
    private int rafaga;
    private int restante;
    private int fin;

    public PP(proceso proceso, int llegada, int rafaga) {

        this.proceso = proceso;
        this.llegada = llegada;
        this.rafaga = rafaga;
        this.restante = rafaga;
        this.fin = 0;
    }

    public proceso getProceso() {
        return proceso;
    }

    public void setProceso(proceso p) {
        proceso = p;
    }

    public int getNumero() {
        return proceso.getPID();
    }

    public int getLlegada() {
        return llegada;
    }

    public void setLlegada(int l) {
        llegada = l;
    }

    public int getRafaga() {
        return rafaga;
    }

    public void setRafaga(int r) {
        rafaga = r;
        restante = r;
    }

    public int getRestante() {
        return restante;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int f) {
        fin = f;
    }

    public boolean yaLlego(int tiempo) {

        return llegada <= tiempo;
    }

    public boolean necesitaCPU() {

        return restante > 0;
    }

    public void consumirUnidad() {

        if (restante > 0) {
            restante--;
        }
    }

    public int getEspera() {

        return fin - llegada - rafaga;
    }

    public int getRetorno() {

        return fin - llegada;
    }
}