public class Bios{
   private boolean iniciar;
   private String dispositivos;
    public Bios(){
        this.iniciar = false;
        this.dispositivos="usb, disco duro, ";
    }
    
    public String arrancar(){
        this.iniciar=true;
        return "Verificando el sistema..";
    }
    
    public void apagar(){
        this.iniciar=false;
    }
    
    public String checarDispositivos(){
        return dispositivos;
    }
    
    
}