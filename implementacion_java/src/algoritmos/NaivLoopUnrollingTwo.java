package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class NaivLoopUnrollingTwo implements AlgoritmoMultiplicacion {


    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a double
        double[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1);
        double[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2);
        double[][] doubleResultado = multiplicarNaivLoopUnrollingTwo(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado double a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado);
    }

    /**
     * Permite multiplicar dos matrices mediante el método NaivLoopUnrollingTwo
     *
     * @param matrizA primera matriz
     * @param matrizB segunda matriz
     * @return matrizC matriz resultado de la multiplicación entre A y B
     */
    public double[][] multiplicarNaivLoopUnrollingTwo(double[][] matrizA, double[][] matrizB) {
        int m = matrizA.length;
        int n = matrizB[0].length;
        int p = matrizB.length;

        double[][] matrizC = new double[n][m];

        if (p % 2 == 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    double sum1 = 0.0;
                    double sum2 = 0.0;
                    for (int k = 0; k < p; k += 2) {
                        sum1 += matrizA[i][k] * matrizB[k][j];
                        sum2 += matrizA[i][k + 1] * matrizB[k + 1][j];
                    }
                    matrizC[i][j] = sum1 + sum2;
                }
            }
        } else {
            int pp = p - 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    double sum1 = 0.0;
                    double sum2 = 0.0;
                    for (int k = 0; k < pp; k += 2) {
                        sum1 += matrizA[i][k] * matrizB[k][j];
                        sum2 += matrizA[i][k + 1] * matrizB[k + 1][j];

                    }
                    matrizC[i][j] = (sum1 + sum2) + matrizA[i][pp] * matrizB[pp][j];
                }
            }
        }
        return matrizC;
    }

}
