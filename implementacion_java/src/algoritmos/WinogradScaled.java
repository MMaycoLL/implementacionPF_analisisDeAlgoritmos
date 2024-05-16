package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class WinogradScaled implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    /**
     * Calcula la norma infinito de una matriz.
     *
     * @param A Matriz de entrada.
     * @param N Número de filas.
     * @param M Número de columnas.
     * @return La norma infinito de la matriz.
     */
    public static double NormInf(double[][] A, int N, int M) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < N; i++) {
            double sum = 0.0;
            for (int j = 0; j < M; j++) {
                sum += Math.abs(A[i][j]);
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    /**
     * Multiplica una matriz por un escalar.
     *
     * @param A      Matriz de entrada.
     * @param B      Matriz de salida.
     * @param N      Número de filas.
     * @param M      Número de columnas.
     * @param scalar Escalar.
     */
    public static void MultiplyWithScalar(double[][] A, double[][] B, int N, int M, double scalar) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                B[i][j] = A[i][j] * scalar;
            }
        }
    }

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        double[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1);
        double[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2);
        double[][] doubleResultado = multiplicarWinogradScaled(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado int a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado);
    }

    public double[][] multiplicarWinogradScaled(double[][] A, double[][] B) {
        int N = A.length;
        int P = A[0].length;
        int M = B[0].length;
        double[][] C = new double[N][M];

        // Create scaled copies of A and B
        double[][] CopyA = new double[N][P];
        double[][] CopyB = new double[P][M];

        // Scaling factors
        double a = NormInf(A, N, P);
        double b = NormInf(B, P, M);
        double lambda = Math.floor(0.5 + Math.log(b / a) / Math.log(4));

        // Scaling
        MultiplyWithScalar(A, CopyA, N, P, Math.pow(2, lambda));
        MultiplyWithScalar(B, CopyB, P, M, Math.pow(2, -lambda));

        // Using Winograd with scaled matrices
        C = multiplyWinograd(CopyA, CopyB);

        return C;
    }

    /**
     * Multiplies matrices using Winograd algorithm.
     */
    private double[][] multiplyWinograd(double[][] A, double[][] B) {
        int N = A.length;
        int M = B[0].length;
        int P = B.length;
        double[][] C = new double[N][M];

        // Intermediate variables for Winograd algorithm
        double[] rowFactor = new double[N];
        double[] colFactor = new double[M];

        // Precompute row factors
        for (int i = 0; i < N; i++) {
            rowFactor[i] = A[i][0] * A[i][1];
            for (int j = 2; j < P; j += 2) {
                rowFactor[i] += A[i][j] * A[i][j + 1];
            }
        }

        // Precompute column factors
        for (int j = 0; j < M; j++) {
            colFactor[j] = B[0][j] * B[1][j];
            for (int i = 2; i < P; i += 2) {
                colFactor[j] += B[i][j] * B[i + 1][j];
            }
        }

        // Actual matrix multiplication
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                C[i][j] = -rowFactor[i] - colFactor[j];
                for (int k = 0; k < P; k += 2) {
                    C[i][j] += (A[i][k] + B[k + 1][j]) * (A[i][k + 1] + B[k][j]);
                }
            }
        }

        // Adjust for odd dimension
        if (P % 2 == 1) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    C[i][j] += A[i][P - 1] * B[P - 1][j];
                }
            }
        }

        return C;
    }

}
