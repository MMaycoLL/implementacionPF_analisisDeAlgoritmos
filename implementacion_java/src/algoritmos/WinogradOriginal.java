package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class WinogradOriginal implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        int[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1);
        int[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2);
        int[][] doubleResultado = multiplicarWinogradOriginal(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado double a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado);
    }

    public int[][] multiplicarWinogradOriginal(int[][] matrizA, int[][] matrizB) {
        int N = matrizA.length; // Número de filas de A
        int P = matrizA[0].length; // Número de columnas de A (y filas de B)
        int M = matrizB[0].length; // Número de columnas de B
        int[][] matrizC = new int[N][M]; // Matriz de resultado
        int[] y = new int[M]; // Vector de factores de fila
        int[] z = new int[N]; // Vector de factores de columna

        int aux; // Variable auxiliar para acumulaciones
        int upsilon = P % 2; // Determina si P es par o impar
        int gamma = P - upsilon; // Ajusta gamma para manejar pares

        int i, j, k;

        // Precalcular los factores de filas
        for (i = 0; i < M; i++) {
            aux = 0;
            for (j = 0; j < gamma; j += 2) {
                aux += matrizA[i][j] * matrizA[i][j + 1]; // Sumar productos de pares de elementos en la fila
            }
            y[i] = aux;
        }

        // Precalcular los factores de columnas
        for (i = 0; i < N; i++) {
            aux = 0;
            for (j = 0; j < gamma; j += 2) {
                aux += matrizB[j][i] * matrizB[j + 1][i]; // Sumar productos de pares de elementos en la columna
            }
            z[i] = aux;
        }

        if (upsilon == 1) { // Si P es impar
            /*
             * Si P es impar, falta el término matrizA[i][P] * matrizB[P][k] en todas las sumas auxiliares.
             */
            int PP = P - 1; // Última posición válida de los índices
            for (i = 0; i < M; i++) {
                for (k = 0; k < N; k++) {
                    aux = 0;
                    for (j = 0; j < gamma; j += 2) {
                        aux += (matrizA[i][j] + matrizB[j + 1][k]) * (matrizA[i][j + 1] + matrizB[j][k]); // Producto ajustado
                    }
                    matrizC[i][k] = aux - y[i] - z[k] + matrizA[i][PP] * matrizB[PP][k]; // Ajustar el término faltante
                }
            }
        } else { // Si P es par
            /*
             * Si P es par, el resultado se puede calcular directamente con las sumas auxiliares.
             */
            for (i = 0; i < M; i++) {
                for (k = 0; k < N; k++) {
                    aux = 0;
                    for (j = 0; j < gamma; j += 2) {
                        aux += (matrizA[i][j] + matrizB[j + 1][k]) * (matrizA[i][j + 1] + matrizB[j][k]); // Producto ajustado
                    }
                    matrizC[i][k] = aux - y[i] - z[k]; // Usar las sumas auxiliares
                }
            }
        }

        // Devolver la matriz resultado
        return matrizC;
    }
}

