package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class NaivOnArray implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a double
        double[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1);
        double[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2);
        double[][] doubleResultado = multiplicarNaivOnArray(doubleMatriz1, doubleMatriz2);

        // Convertir el resultado double a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado);
    }

    /**
     * Método para multiplicar dos matrices usando el método NaivOnArray
     *
     * @param matrizA primera matriz
     * @param matrizB segunda matriz
     * @return matrizC matriz resultado de la multiplicación entre A y B
     */
    public double[][] multiplicarNaivOnArray(double[][] matrizA, double[][] matrizB) {

        int m = matrizA.length;
        int n = matrizB[0].length;
        double[][] matrizC = new double[m][n];

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {
                int suma = 0;
                for (int k = 0; k < matrizA[0].length; k++) {
                    suma += matrizA[i][k] * matrizB[k][j];
                }
                matrizC[i][j] = suma;
            }
        }
        return matrizC;
    }
}
