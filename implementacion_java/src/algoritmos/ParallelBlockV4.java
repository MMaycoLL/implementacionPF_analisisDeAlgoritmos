package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelBlockV4 implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        int[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1);
        int[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2);
        int[][] doubleResultado = multiplicarParallelBlockV4(doubleMatriz1, doubleMatriz2, doubleMatriz1.length);

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado);
    }

    // Método para multiplicar dos matrices en paralelo utilizando bloques
    public int[][] multiplicarParallelBlockV4(int[][] matrix1, int[][] matrix2, int blockSize) {
        // Obtener las dimensiones de las matrices de entrada
        int m = matrix1.length; // Número de filas de la matriz 1
        int n = matrix2[0].length; // Número de columnas de la matriz 2
        // Crear una matriz para almacenar el resultado de la multiplicación
        int[][] result = new int[m][n];
        // Obtener el número de hilos disponibles en el sistema
        int numThreads = Runtime.getRuntime().availableProcessors();
        // Crear un grupo de hilos con un número fijo de hilos
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Iterar sobre las matrices en bloques y asignar tareas a los hilos
        for (int i = 0; i < m; i += blockSize) {
            for (int j = 0; j < n; j += blockSize) {
                for (int k = 0; k < matrix1[0].length; k += blockSize) {
                    // Ejecutar una instancia de la clase MatrixMultiplier en un hilo
                    executor.execute(new MatrixMultiplier(matrix1, matrix2, result, i, j, k, blockSize));
                }
            }
        }

        // Apagar el grupo de hilos después de que todas las tareas hayan finalizado
        executor.shutdown();
        // Esperar hasta que todas las tareas se completen
        while (!executor.isTerminated())
            ;
        // Devolver la matriz resultado
        return result;
    }

    // Clase interna para realizar la multiplicación de matrices en paralelo
    private class MatrixMultiplier implements Runnable {
        // Variables de instancia para matrices de entrada, matriz resultado y límites de bucles
        private final int[][] matrix1;
        private final int[][] matrix2;
        private final int[][] result;
        private final int startRow;
        private final int startCol;
        private final int startK;
        private final int blockSize;

        // Constructor que inicializa las variables de instancia
        public MatrixMultiplier(int[][] matrix1, int[][] matrix2, int[][] result, int startRow, int startCol,
                                int startK, int blockSize) {
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.result = result;
            this.startRow = startRow;
            this.startCol = startCol;
            this.startK = startK;
            this.blockSize = blockSize;
        }

        @Override
        public void run() {
            // Calcular los límites para los bucles de iteración
            int endRow = Math.min(startRow + blockSize, matrix1.length);
            int endCol = Math.min(startCol + blockSize, matrix2[0].length);
            int endK = Math.min(startK + blockSize, matrix1[0].length);

            // Iterar sobre los bloques asignados y realizar la multiplicación
            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    for (int k = startK; k < endK; k++) {
                        result[k][i] += matrix1[k][j] * matrix2[j][i];
                    }
                }
            }
        }
    }
}
