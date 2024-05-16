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

    public int[][] multiplicarParallelBlockV4(int[][] matrix1, int[][] matrix2, int blockSize) {

        int m = matrix1.length;
        int n = matrix2[0].length;
        int[][] result = new int[m][n];
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < m; i += blockSize) {
            for (int j = 0; j < n; j += blockSize) {
                for (int k = 0; k < matrix1[0].length; k += blockSize) {
                    executor.execute(new MatrixMultiplier(matrix1, matrix2, result, i, j, k, blockSize));
                }
            }
        }

        executor.shutdown();
        while (!executor.isTerminated())
            ;
        return result;
    }
    // Clase para realizar la multiplicación de matrices en paralelo
    private class MatrixMultiplier implements Runnable {

        private final int[][] matrix1;
        private final int[][] matrix2;
        private final int[][] result;
        private final int startRow;
        private final int startCol;
        private final int startK;
        private final int blockSize;

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
            int endRow = Math.min(startRow + blockSize, matrix1.length);
            int endCol = Math.min(startCol + blockSize, matrix2[0].length);
            int endK = Math.min(startK + blockSize, matrix1[0].length);

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
