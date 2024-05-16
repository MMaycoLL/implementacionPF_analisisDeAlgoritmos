package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class StrassenNaiv implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        int[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1);
        int[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2);
        int[][] doubleResultado = strassenNaiv(doubleMatriz1, doubleMatriz2, 0);

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado);
    }

    public int[][] strassenNaiv(int[][] A, int[][] B, int threshold) {
        int n = A.length;
        int[][] C = new int[n][n];

        if (n <= threshold) {
            // Multiplicación Naive
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        } else {
            // Dividir las matrices en submatrices más pequeñas
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];
            for (int i = 0; i < n / 2; i++) {
                for (int j = 0; j < n / 2; j++) {
                    A11[i][j] = A[i][j];
                    A12[i][j] = A[i][j + n / 2];
                    A21[i][j] = A[i + n / 2][j];
                    A22[i][j] = A[i + n / 2][j + n / 2];
                    B11[i][j] = B[i][j];
                    B12[i][j] = B[i][j + n / 2];
                    B21[i][j] = B[i + n / 2][j];
                    B22[i][j] = B[i + n / 2][j + n / 2];
                }
            }

            // Calcular los productos de matrices utilizando el algoritmo de Strassen
            int[][] P1 = strassenNaiv(add(A11, A22), add(B11, B22), threshold);
            int[][] P2 = strassenNaiv(add(A21, A22), B11, threshold);
            int[][] P3 = strassenNaiv(A11, subtract(B12, B22), threshold);
            int[][] P4 = strassenNaiv(A22, subtract(B21, B11), threshold);
            int[][] P5 = strassenNaiv(add(A11, A12), B22, threshold);
            int[][] P6 = strassenNaiv(subtract(A21, A11), add(B11, B12), threshold);
            int[][] P7 = strassenNaiv(subtract(A12, A22), add(B21, B22), threshold);

            // Calcular las submatrices de la matriz de producto C
            int[][] C11 = subtract(add(add(P1, P4), P7), P5);
            int[][] C12 = add(P3, P5);
            int[][] C21 = add(P2, P4);
            int[][] C22 = subtract(add(add(P1, P3), P6), P2);

            // Combinar las submatrices de la matriz de producto C
            for (int i = 0; i < n / 2; i++) {
                for (int j = 0; j < n / 2; j++) {
                    C[i][j] = C11[i][j];
                    C[i][j + n / 2] = C12[i][j];
                    C[i + n / 2][j] = C21[i][j];
                    C[i + n / 2][j + n / 2] = C22[i][j];
                }
            }
        }

        return C;
    }

    public int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    public int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

}
