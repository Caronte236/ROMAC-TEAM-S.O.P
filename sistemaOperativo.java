public class sistemaOperativo{
    private String nombre; 
    private String version;
    private String logo;
    private String creadores;
    
public sistemaOperativo(String n, String v){
    this.nombre=n;
    this.version=v;
    this.logo= genLogo();
    this.creadores="Jorge Carrera  \n Cyntia Angeles \n Joselyn Olivera \n Jisharlyn Miguel \n Michael Reyes";
}

    public void setNombre(String n){
        this.nombre=n;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setVersion(String v){
        this.version=v;
    }
    
    public String getVersion(){
        return version;
    }
    
public String genLogo(){
        
        return """ 
                                   #################                                
                               ########          ########                            
                             #####                    ######                         
                           ####                          #####                       
                                                           ####                      
                       ===                 ########          ####                    
                       ===             #######  #######       ####                   
                                  ########          ########   ###                   
                        #####   #############################   ###                  
                   ###  #####     ##########################    ###                  
                                  ######## ######## #######     ####                 
                      ====         ######   ######   #####       ###                 
                      ====*        ######   ######   #####       ###                 
                          ##       ######   ######   #####      ###                  
                      #*#   ####   ######   ######   #####      ###                  
                            ####   ######   ######   #####     ###                   
                        +====      ######   ######   #####    ####                   
                        =====      ######   ######   #####   ####                    
                            +=+    ######   ######   ##### ####                      
                            ==+    ######   ######   ##  #####                       
                              #### ######   ######    ######                         
                                #######**        ########                            
                                    #################                                
                                                                                     
                                                                                     
                                                                                     
          #########     #########     ###      ###       ###       #########         
          ##     ###   ###     ###    ####   #####      #####     ###                
          ## #######  ###       ##    ## #########     ##  ###    ##                 
          ## #####     ##      ###    ##  #### ###    ## == ###   ###                
          ##    ###     #########     ##       ###   ##  === ###   #########
        
        
        
        """;
        
    }
    
    public String getLogo(){
        return logo;
    }
    
    public String mostrarInformacion(){
        return "Sistema operativo: " + nombre + "\n version: " + version + "\n" +  "\n Creadores:" + creadores + "\n Romac es un sistema operativo monousuario y modular \n diseñado para trabajar con un solo usuario y organizado en diferentes modulos \n para representar sus principales funciones";
    
    }
    
}
