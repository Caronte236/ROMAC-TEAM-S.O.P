public class Kernel {
    
    private boolean encendido;
    private String usuarioActual;
    private String usuario;
    private String contraseña;

    public Kernel() {
        this.encendido = false;
        this.usuarioActual = null;
        this.usuario = "admin";
        this.contraseña = "admin";
    }

    public void encender() {
        if (!encendido) {
            encendido = true;
        }
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

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }
}