package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class NaivLoopUnrollingFour implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        int[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1);
        int[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2);
        int[][] doubleResultado = multiplicarNaivLoopUnrollingFour(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado);

    }

    /**
     * Permite multiplicar dos matrices mediante el método NaivLoopUnrollingFour
     *
     * @param matrizA primera matriz
     * @param matrizB segunda matriz
     * @return matrizC matriz resultado de la multiplicación entre A y B
     */
    public int[][] multiplicarNaivLoopUnrollingFour(int[][] matrizA, int[][] matrizB) {
        int m = matrizA.length;
        int n = matrizA[0].length;
        int p = matrizB[0].length;

        int[][] matrizC = new int[m][p];
        int aux = 0;

        if (p % 4 == 0) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {

                    for (int k = 0; k < n; k += 4) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j]
                                + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j];
                    }
                    matrizC[i][j] = aux;
                }
            }
        } else if (p % 4 == 1) {

            int pp = p - 1;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    aux = 0;
                    for (int k = 0; k < pp; k += 4) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j]
                                + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j];
                    }
                    matrizC[i][j] = aux + matrizA[i][pp] * matrizB[pp][j];
                }
            }
        } else if (p % 4 == 2) {

            int pp = p - 2;
            int ppp = p - 1;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    aux = 0;
                    for (int k = 0; k < pp; k += 4) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j]
                                + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j];

                    }
                    matrizC[i][j] = aux + matrizA[i][pp] * matrizB[pp][j] + matrizA[i][ppp] * matrizB[ppp][j];
                }
            }
        } else {
            int pp = p - 3;
            int ppp = p - 2;
            int pppp = p - 1;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    aux = 0;
                    for (int k = 0; k < pp; k += 4) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j]
                                + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j];

                    }
                    matrizC[i][j] = aux + matrizA[i][pp] * matrizB[pp][j] + matrizA[i][ppp] * matrizB[ppp][j]
                            + matrizA[i][pppp] * matrizB[i][pppp];
                }
            }
        }
        return matrizC;
    }
}
