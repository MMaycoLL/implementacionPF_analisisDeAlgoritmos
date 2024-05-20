package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelBlockIV4 implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        int[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1);
        int[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2);
        int[][] doubleResultado = multiply(doubleMatriz1, doubleMatriz2, doubleMatriz1.length);

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado);
    }

    // Método para multiplicar dos matrices usando el método Parallel Block 4.4
    public int[][] multiply(int[][] matrix1, int[][] matrix2, int blockSize) {
        // Obtiene las dimensiones de las matrices
        int m = matrix1.length; // Número de filas de la primera matriz
        int n = matrix2[0].length; // Número de columnas de la segunda matriz
        // Crea una matriz para almacenar el resultado de la multiplicación
        int[][] result = new int[m][n];
        // Obtiene el número de hilos disponibles en el sistema
        int numThreads = Runtime.getRuntime().availableProcessors();
        // Crea un grupo de hilos con un número fijo de hilos
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Itera sobre las matrices en bloques de tamaño blockSize
        for (int i = 0; i < m; i += blockSize) {
            for (int j = 0; j < n; j += blockSize) {
                for (int k = 0; k < matrix1[0].length; k += blockSize) {
                    // Ejecuta una instancia de la clase MatrixMultiplier en un hilo
                    executor.execute(new MatrixMultiplier(matrix1, matrix2, result, i, j, k, blockSize));
                }
            }
        }

        // Apaga el grupo de hilos después de que todos los hilos hayan terminado
        executor.shutdown();
        // Espera a que todos los hilos finalicen
        while (!executor.isTerminated())
            ;
        // Devuelve la matriz resultado
        return result;
    }

    // Clase para realizar la multiplicación de matrices en paralelo
    private class MatrixMultiplier implements Runnable {
        // Variables de instancia para matrices de entrada, matriz resultado y parámetros de bloque
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

        // Método run() que realiza la multiplicación de matrices en paralelo
        @Override
        public void run() {
            // Calcula las filas, columnas y dimensiones internas finales del bloque
            int endRow = Math.min(startRow + blockSize, matrix1.length);
            int endCol = Math.min(startCol + blockSize, matrix2[0].length);
            int endK = Math.min(startK + blockSize, matrix1[0].length);

            // Itera sobre los elementos del bloque y realiza la multiplicación
            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    for (int k = startK; k < endK; k++) {
                        result[i][k] += matrix1[i][j] * matrix2[j][k];
                    }
                }
            }
        }
    }
}
