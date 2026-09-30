public class Bios {
    
    private boolean iniciar;
    private String dispositivos;

    public Bios() {
        this.iniciar = false;
        this.dispositivos = "USB, disco duro";
    }

    public String arrancar() {
        iniciar = true;
        return "BIOS iniciada. Verificando el sistema...";
    }

    public void apagar() {
        iniciar = false;
    }

    public String checarDispositivos() {
        return dispositivos;
    }

    public boolean estaIniciada() {
        return iniciar;
    }

    public void setIniciar(boolean iniciar) {
        this.iniciar = iniciar;
    }

    public boolean getIniciar() {
        return iniciar;
    }

    public void setDispositivos(String dispositivos) {
        this.dispositivos = dispositivos;
    }

    public String getDispositivos() {
        return dispositivos;
    }
}