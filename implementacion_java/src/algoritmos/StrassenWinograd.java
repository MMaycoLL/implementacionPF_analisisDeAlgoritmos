package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class StrassenWinograd implements AlgoritmoMultiplicacion {

    private final int THRESHOLD = 32;
    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        int[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1);
        int[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2);
        int[][] doubleResultado = multiplicarStrassenWinograd(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado);
    }

    public int[][] multiplicarStrassenWinograd(int[][] A, int[][] B) {
        int n = A.length; // Tamaño de la matriz (se asume que es cuadrada)
        int[][] C = new int[n][n]; // Matriz de resultado

        // Umbral para usar el algoritmo tradicional de multiplicación de matrices
        if (n <= THRESHOLD) {
            // Multiplicación de matrices tradicional
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
            return C; // Devolver el resultado
        } else {
            // Dividir las matrices en submatrices
            int[][] A11 = split(A, 0, 0);
            int[][] A12 = split(A, 0, n / 2);
            int[][] A21 = split(A, n / 2, 0);
            int[][] A22 = split(A, n / 2, n / 2);

            int[][] B11 = split(B, 0, 0);
            int[][] B12 = split(B, 0, n / 2);
            int[][] B21 = split(B, n / 2, 0);
            int[][] B22 = split(B, n / 2, n / 2);

            // Calcular los productos intermedios usando la técnica de Strassen
            int[][] P1 = multiplicarStrassenWinograd(add(A11, A22), add(B11, B22));
            int[][] P2 = multiplicarStrassenWinograd(add(A21, A22), B11);
            int[][] P3 = multiplicarStrassenWinograd(A11, sub(B12, B22));
            int[][] P4 = multiplicarStrassenWinograd(A22, sub(B21, B11));
            int[][] P5 = multiplicarStrassenWinograd(add(A11, A12), B22);
            int[][] P6 = multiplicarStrassenWinograd(sub(A21, A11), add(B11, B12));
            int[][] P7 = multiplicarStrassenWinograd(sub(A12, A22), add(B21, B22));

            // Combinar los productos intermedios para obtener las submatrices de C
            int[][] C11 = add(sub(add(P1, P4), P5), P7);
            int[][] C12 = add(P3, P5);
            int[][] C21 = add(P2, P4);
            int[][] C22 = add(sub(add(P1, P3), P2), P6);

            // Unir las submatrices en la matriz de resultado C
            join(C11, C, 0, 0);
            join(C12, C, 0, n / 2);
            join(C21, C, n / 2, 0);
            join(C22, C, n / 2, n / 2);

            return C; // Devolver la matriz de resultado
        }
    }

    // Método para dividir una matriz en una submatriz
    private int[][] split(int[][] A, int iB, int jB) {
        int nB = A.length / 2; // Tamaño de la submatriz
        int[][] B = new int[nB][nB]; // Submatriz de resultado
        for (int i1 = 0, i2 = iB; i1 < nB; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < nB; j1++, j2++) {
                B[i1][j1] = A[i2][j2]; // Copiar elementos a la submatriz
            }
        }
        return B; // Devolver la submatriz
    }

    // Método para unir una submatriz en la matriz de resultado
    private void join(int[][] A, int[][] B, int iB, int jB) {
        int nB = A.length; // Tamaño de la submatriz
        for (int i1 = 0, i2 = iB; i1 < nB; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < nB; j1++, j2++) {
                B[i2][j2] = A[i1][j1]; // Copiar elementos de la submatriz a la matriz de resultado
            }
        }
    }

    // Método para sumar dos matrices
    private int[][] add(int[][] A, int[][] B) {
        int n = A.length; // Tamaño de las matrices
        int[][] C = new int[n][n]; // Matriz de resultado
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j]; // Sumar los elementos correspondientes
            }
        }
        return C; // Devolver la matriz de suma
    }

    // Método para restar dos matrices
    private int[][] sub(int[][] A, int[][] B) {
        int n = A.length; // Tamaño de las matrices
        int[][] C = new int[n][n]; // Matriz de resultado
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j]; // Restar los elementos correspondientes
            }
        }
        return C; // Devolver la matriz de resta
    }
}
