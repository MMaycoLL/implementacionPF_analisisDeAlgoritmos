package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EnhancedParallelBlockIII5 implements AlgoritmoMultiplicacion {

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a double
        double[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1);
        double[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2);
        double[][] doubleResultado = multiplyMatrices(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado double a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado);
    }

    // Número de hilos a utilizar, igual al número de núcleos disponibles en el sistema
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    // Instancia de la clase Convertidor
    Convertidor convertidor = new Convertidor();

    // Método para multiplicar dos matrices en paralelo
    public static double[][] multiplyMatrices(double[][] a, double[][] b) {
        // Obtener las dimensiones de las matrices de entrada
        int aRows = a.length; // Número de filas de la matriz A
        int aCols = a[0].length; // Número de columnas de la matriz A
        int bCols = b[0].length; // Número de columnas de la matriz B

        // Verificar si las dimensiones de las matrices son compatibles para la multiplicación
        if (aCols != b.length) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: dimensions mismatch");
        }

        // Crear una matriz para almacenar el resultado de la multiplicación
        double[][] result = new double[aRows][bCols];
        // Calcular el tamaño del bloque para dividir las tareas entre los hilos
        int blockSize = Math.max(1, aRows / NUM_THREADS);

        // Crear un grupo de hilos con un número fijo de hilos
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // Iterar sobre las matrices en bloques y asignar tareas a los hilos
        for (int i = 0; i < aRows; i += blockSize) {
            for (int j = 0; j < bCols; j += blockSize) {
                for (int k = 0; k < aCols; k += blockSize) {
                    // Ejecutar una instancia de la clase MatrixMultiplier en un hilo
                    executor.submit(new MatrixMultiplier(a, b, result, i, j, k,
                            Math.min(i + blockSize, aRows),
                            Math.min(j + blockSize, bCols),
                            Math.min(k + blockSize, aCols)));
                }
            }
        }

        // Apagar el grupo de hilos después de que todas las tareas hayan finalizado
        executor.shutdown();
        try {
            // Esperar a que todas las tareas se completen antes de continuar
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Devolver la matriz resultado
        return result;
    }

    // Clase interna para realizar la multiplicación de matrices en paralelo
    private static class MatrixMultiplier implements Runnable {
        // Variables de instancia para matrices de entrada, matriz resultado y límites de bucles
        private final double[][] a;
        private final double[][] b;
        private final double[][] result;
        private final int startRow;
        private final int startCol;
        private final int startCommon;
        private final int endRow;
        private final int endCol;
        private final int endCommon;

        // Constructor que inicializa las variables de instancia
        public MatrixMultiplier(double[][] a, double[][] b, double[][] result,
                                int startRow, int startCol, int startCommon,
                                int endRow, int endCol, int endCommon) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.startRow = startRow;
            this.startCol = startCol;
            this.startCommon = startCommon;
            this.endRow = endRow;
            this.endCol = endCol;
            this.endCommon = endCommon;
        }

        @Override
        public void run() {
            // Iterar sobre las filas, columnas y elementos comunes en los bloques asignados
            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    // Sincronizar en la matriz resultado para evitar condiciones de carrera
                    synchronized (result) {
                        // Iterar sobre los elementos comunes y realizar la multiplicación
                        for (int k = startCommon; k < endCommon; k++) {
                            result[i][j] += a[i][k] * b[k][j];
                        }
                    }
                }
            }
        }
    }
}


