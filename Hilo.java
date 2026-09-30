public class Hilo implements Runnable{
    
    private String nombre;

    public Hilo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(nombre + " " + i);
            try {
                // Pausar el hilo por 500 milisegundos
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(nombre + " fue interrumpido.");
            }
        }
    }
}