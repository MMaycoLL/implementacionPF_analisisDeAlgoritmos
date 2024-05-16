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

    /**
     * Permite multiplicar dos matrices mediante el método WinogradOriginal
     *
     * @param matrizA primera matriz
     * @param matrizB segunda matriz
     * @return matrizC matriz resultado de la multiplicación entre A y B
     */
    public int[][] multiplicarWinogradOriginal(int[][] matrizA, int[][] matrizB) {
        int N = matrizA.length;
        int P = matrizA[0].length;
        int M = matrizB[0].length;
        int[][] matrizC = new int[N][M];
        int[] y = new int[M];
        int[] z = new int[N];

        int aux;
        int upsilon = P % 2;
        int gamma = P - upsilon;

        int i, j, k;

        for (i = 0; i < M; i++) {
            aux = 0;
            for (j = 0; j < gamma; j += 2) {
                aux += matrizA[i][j] * matrizA[i][j + 1];
            }
            y[i] = aux;
        }
        for (i = 0; i < N; i++) {
            aux = 0;
            for (j = 0; j < gamma; j += 2) {
                aux += matrizB[j][i] * matrizB[j + 1][i];
            }
            z[i] = aux;
        }
        if (upsilon == 1) {
            /*
             * P is odd The value matrizA[i][P]*matrizB[P][k] is missing in all auxiliary
             * sums.
             */
            int PP = P - 1;
            for (i = 0; i < M; i++) {
                for (k = 0; k < N; k++) {
                    aux = 0;
                    for (j = 0; j < gamma; j += 2) {
                        aux += (matrizA[i][j] + matrizB[j + 1][k]) * (matrizA[i][j + 1] + matrizB[j][k]);
                    }
                    matrizC[i][k] = aux - y[i] - z[k] + matrizA[i][PP] * matrizB[PP][k];
                }
            }
        } else {
            /*
             * P is even The result can be computed with the auxiliary sums.
             */
            for (i = 0; i < M; i++) {
                for (k = 0; k < N; k++) {
                    aux = 0;
                    for (j = 0; j < gamma; j += 2) {
                        aux += (matrizA[i][j] + matrizB[j + 1][k]) * (matrizA[i][j + 1] + matrizB[j][k]);
                    }
                    matrizC[i][k] = aux - y[i] - z[k];
                }
            }
        }
        return matrizC;
    }
}
