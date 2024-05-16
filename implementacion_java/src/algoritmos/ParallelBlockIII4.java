package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelBlockIII4 implements AlgoritmoMultiplicacion {


    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    Convertidor convertidor = new Convertidor();

    public static double[][] matrixMultiplicationParallel(double[][] A, double[][] B) {
        int m = A.length;
        int n = A[0].length;
        int p = B.length;
        int q = B[0].length;

        if (n != p) {
            throw new IllegalArgumentException("Las dimensiones de las matrices no son compatibles para la multiplicación.");
        }

        double[][] C = new double[m][q];

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < m; i++) {
            final int row = i;
            executor.execute(() -> {
                for (int j = 0; j < q; j++) {
                    for (int k = 0; k < n; k++) {
                        C[row][j] += A[row][k] * B[k][j];
                    }
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Esperar a que todas las tareas se completen
        }

        return C;
    }

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        double[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1);
        double[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2);
        double[][] doubleResultado = matrixMultiplicationParallel(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado int a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado);

    }
}
