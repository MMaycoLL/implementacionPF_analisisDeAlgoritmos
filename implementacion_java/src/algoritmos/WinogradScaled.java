package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class WinogradScaled implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();


    public static double NormInf(double[][] A, int N, int M) {
        // Inicializa la variable max con el valor más bajo posible
        double max = Double.NEGATIVE_INFINITY;

        // Recorre cada fila de la matriz
        for (int i = 0; i < N; i++) {
            double sum = 0.0; // Inicializa la suma de elementos absolutos de la fila actual

            // Recorre cada columna de la fila actual
            for (int j = 0; j < M; j++) {
                sum += Math.abs(A[i][j]); // Suma el valor absoluto del elemento actual
            }

            // Actualiza el valor máximo si la suma actual es mayor que el máximo registrado
            if (sum > max) {
                max = sum;
            }
        }

        // Devuelve la suma máxima de elementos absolutos por fila (norma infinito)
        return max;
    }

    public static void MultiplyWithScalar(double[][] A, double[][] B, int N, int M, double scalar) {
        // Recorre cada elemento de la matriz
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                B[i][j] = A[i][j] * scalar; // Multiplica el elemento por el escalar y lo almacena en la matriz de salida
            }
        }
    }

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a double
        double[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1);
        double[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2);
        double[][] doubleResultado = multiplicarWinogradScaled(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado de double a BigInteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado);
    }

    public double[][] multiplicarWinogradScaled(double[][] A, double[][] B) {
        int N = A.length; // Número de filas de A
        int P = A[0].length; // Número de columnas de A (y filas de B)
        int M = B[0].length; // Número de columnas de B
        double[][] C = new double[N][M]; // Matriz de resultado

        // Crear copias escaladas de A y B
        double[][] CopyA = new double[N][P];
        double[][] CopyB = new double[P][M];

        // Factores de escalado
        double a = NormInf(A, N, P); // Norma infinito de A
        double b = NormInf(B, P, M); // Norma infinito de B
        double lambda = Math.floor(0.5 + Math.log(b / a) / Math.log(4)); // Cálculo del factor de escalado

        // Escalar las matrices A y B
        MultiplyWithScalar(A, CopyA, N, P, Math.pow(2, lambda));
        MultiplyWithScalar(B, CopyB, P, M, Math.pow(2, -lambda));

        // Usar Winograd con matrices escaladas
        C = multiplyWinograd(CopyA, CopyB);

        return C; // Devolver la matriz resultado
    }

    /**
     * Multiplica matrices usando el algoritmo de Winograd.
     */
    private double[][] multiplyWinograd(double[][] A, double[][] B) {
        int N = A.length; // Número de filas de A
        int M = B[0].length; // Número de columnas de B
        int P = B.length; // Número de columnas de A (y filas de B)
        double[][] C = new double[N][M]; // Matriz de resultado

        // Variables intermedias para el algoritmo de Winograd
        double[] rowFactor = new double[N];
        double[] colFactor = new double[M];

        // Precalcular factores de filas
        for (int i = 0; i < N; i++) {
            rowFactor[i] = A[i][0] * A[i][1]; // Multiplicar los dos primeros elementos de cada fila
            for (int j = 2; j < P; j += 2) {
                rowFactor[i] += A[i][j] * A[i][j + 1]; // Sumar productos de pares de elementos
            }
        }

        // Precalcular factores de columnas
        for (int j = 0; j < M; j++) {
            colFactor[j] = B[0][j] * B[1][j]; // Multiplicar los dos primeros elementos de cada columna
            for (int i = 2; i < P; i += 2) {
                colFactor[j] += B[i][j] * B[i + 1][j]; // Sumar productos de pares de elementos
            }
        }

        // Realizar la multiplicación de matrices
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                C[i][j] = -rowFactor[i] - colFactor[j]; // Inicializar con la suma de factores negativos
                for (int k = 0; k < P; k += 2) {
                    C[i][j] += (A[i][k] + B[k + 1][j]) * (A[i][k + 1] + B[k][j]); // Sumar productos ajustados
                }
            }
        }

        // Ajustar si la dimensión es impar
        if (P % 2 == 1) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    C[i][j] += A[i][P - 1] * B[P - 1][j]; // Añadir el último producto si P es impar
                }
            }
        }

        return C; // Devolver la matriz resultado
    }
}
