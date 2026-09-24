public class hilo implements Runnable {
    private String nombre;
    private int repeticiones;
    
    public hilo (String nombre, int repeticiones) {
        this.nombre = nombre;
        this.repeticiones = repeticiones;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= repeticiones; i++) {
            System.out.println(nombre + " -> " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(nombre + " interrumpido");
            }
        }
        System.out.println(nombre + " finalizó.");
    }
}