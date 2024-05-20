package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelBlockIII4 implements AlgoritmoMultiplicacion {


    // Número de hilos a utilizar, igual al número de núcleos disponibles en el sistema
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    // Instancia de la clase Convertidor
    Convertidor convertidor = new Convertidor();

    // Método para multiplicar dos matrices en paralelo
    public static double[][] matrixMultiplicationParallel(double[][] A, double[][] B) {
        // Obtener las dimensiones de las matrices de entrada
        int m = A.length; // Número de filas de la matriz A
        int n = A[0].length; // Número de columnas de la matriz A (igual al número de filas de la matriz B)
        int p = B.length; // Número de filas de la matriz B
        int q = B[0].length; // Número de columnas de la matriz B

        // Verificar si las dimensiones de las matrices son compatibles para la multiplicación
        if (n != p) {
            throw new IllegalArgumentException("Las dimensiones de las matrices no son compatibles para la multiplicación.");
        }

        // Crear una matriz para almacenar el resultado de la multiplicación
        double[][] C = new double[m][q];

        // Crear un grupo de hilos con un número fijo de hilos
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // Iterar sobre las filas de la matriz A
        for (int i = 0; i < m; i++) {
            final int row = i;
            // Ejecutar una tarea para cada fila de la matriz A en paralelo
            executor.execute(() -> {
                // Iterar sobre las columnas de la matriz B
                for (int j = 0; j < q; j++) {
                    // Iterar sobre las dimensiones internas de las matrices A y B
                    for (int k = 0; k < n; k++) {
                        // Calcular el producto de los elementos correspondientes y sumar al resultado
                        C[row][j] += A[row][k] * B[k][j];
                    }
                }
            });
        }

        // Apagar el grupo de hilos después de que todas las tareas hayan finalizado
        executor.shutdown();
        // Esperar a que todas las tareas se completen antes de continuar
        while (!executor.isTerminated()) {
            // Esperar a que todas las tareas se completen
        }

        // Devolver la matriz resultado
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
