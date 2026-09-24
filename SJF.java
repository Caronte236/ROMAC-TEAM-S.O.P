import java.util.*;

public class SJF {
    private List<proceso> lista;
    public SJF() {
        this.lista = new ArrayList<>();
    }

    public void agregarProceso(proceso p) {
        lista.add(p);
    }

    public String ejecutar() {
        int tiempo = 0;
        int terminados = 0;
        int n = lista.size();

        while (terminados < n) {
            for (proceso p : lista) {
                if(){}
            }

        }

       
    }
}