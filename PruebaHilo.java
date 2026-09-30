public class PruebaHilo{
     public static void main(String args[]){
        
        Hilo h1 = new Hilo("Hilo A");
        Hilo h2 = new Hilo("Hilo B");

        Thread t1 = new Thread(h1);
        Thread t2 = new Thread(h2);

        t1.start();
        t2.start();
    }
}
