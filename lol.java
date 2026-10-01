 
    import java.util.Scanner;

public class lol
{
    private boolean encendido;
    private String usuarioActual;
    private String usuario;
    private String contraseña;

    public lol() {
        this.encendido = false;
        this.usuarioActual = null;
        this.usuario = null;
        this.contraseña = null;
    }

    public void encender() {
        if (!encendido) {
            encendido = true;
        }
    }
    
    public String crear(String user, String pass){
         usuario=user;
         contraseña=pass;
         return "user: "+ usuario + ", pass= " + contraseña;
    } 
    
    public void apagar() {
        if (encendido) {
            encendido = false;
            usuarioActual = null;
        }
    }

    public boolean estaEncendido() {
        return encendido;
    }

    public void crearUsuario(String us, String contra) {
        this.usuario = us;
        this.contraseña = contra;
    }

    public boolean validarUsuario(String intentous, String intentocontra) {
        return this.usuario.equals(intentous)
            && this.contraseña.equals(intentocontra);
    }

    public boolean iniciar(String usuario, String contraseña) {

        if (!encendido) {
            return false;
        }

        if (usuarioActual != null) {
            return false;
        }

        if (validarUsuario(usuario, contraseña)) {
            usuarioActual = usuario;
            return true;
        }

        return false;
    }

    public void cerrarSesion() {
        if (usuarioActual != null){
        usuarioActual = null;
    }
}
    
    public void setEncendido(boolean e) {
        this.encendido = e;
    }

    public boolean getEncendido() {
        return encendido;
    }

    public void setUsuarioActual(String us) {
        this.usuarioActual = us;
    }

    public String getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuario(String u){
        this.usuario=u;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }
    public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    lol prueba = new lol();
    prueba.encender(); 

    System.out.print("Crea tu user: ");
    String user = scan.nextLine();
    System.out.print("Crea tu pass: ");
    String pass = scan.nextLine();
    prueba.crear(user, pass);

    System.out.println("== Usuario creado: " + prueba.getUsuario()
                       + " / " + prueba.getContraseña());

    boolean logueado = false;
    int intentos = 0;

    while (!logueado && intentos < 3) {
        System.out.print("Ingresa user: ");
        String intentoU = scan.nextLine();
        System.out.print("Ingresa pass: ");
        String intentoP = scan.nextLine();

        if (prueba.iniciar(intentoU, intentoP)) {
            logueado = true;
            System.out.println(" Login correcto. " 
                               + prueba.getUsuarioActual());
        } else {
            intentos++;
            System.out.println(" Datos incorrectos. Intento "
                               + intentos + "/3");
        }
    }

    if (!logueado) {
        System.out.println(" Demasiados intentos fallidos.");
    } else {
        prueba.cerrarSesion();
        System.out.println("Sesión cerrada.");
    }

    scan.close();
}
}
