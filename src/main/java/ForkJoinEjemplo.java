import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinEjemplo extends RecursiveTask<Long> {
    private long numero;

    public ForkJoinEjemplo(long numero) {
        this.numero = numero;
    }

    @Override
    protected Long compute() {
        if (numero <= 10) {
            return sumar();
        } else {
            long mitad = numero / 2;
            ForkJoinEjemplo tarea1 = new ForkJoinEjemplo(mitad);
            ForkJoinEjemplo tarea2 = new ForkJoinEjemplo(numero - mitad);

            tarea1.fork();
            long resultado2 = tarea2.compute();
            long resultado1 = tarea1.join();

            return resultado1 + resultado2;
        }
    }

    private long sumar() {
        long suma = 0;
        for (long i = 1; i <= numero; i++) {
            suma += i;
        }
        return suma;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinEjemplo tarea = new ForkJoinEjemplo(100);

        long resultado = pool.invoke(tarea);
        System.out.println("Resultado: " + resultado);
    }
}