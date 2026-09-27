public class Main {
    public static void main(String[] args) {

        PP[] procesos = {
            new PP(1, 0, 8),
            new PP(2, 1, 4),
            new PP(3, 2, 9),
            new PP(4, 3, 5)
        };

        SJF sjf = new SJF(procesos);
        sjf.ordenarPorLlegada();
        sjf.ejecutar();
        sjf.mostrarResultados();
    }
}