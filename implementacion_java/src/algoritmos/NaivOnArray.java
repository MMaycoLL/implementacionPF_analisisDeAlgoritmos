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

    public double[][] multiplicarNaivOnArray(double[][] matrizA, double[][] matrizB) {
        // Obtener las dimensiones de las matrices de entrada
        int m = matrizA.length; // Número de filas de la matriz A
        int n = matrizB[0].length; // Número de columnas de la matriz B
        // Crear una matriz para almacenar el resultado de la multiplicación
        double[][] matrizC = new double[m][n];

        // Iterar sobre las filas de la matriz A
        for (int i = 0; i < m; i++) {
            // Iterar sobre las columnas de la matriz B
            for (int j = 0; j < n; j++) {
                int suma = 0;
                // Iterar sobre las dimensiones internas de las matrices A y B
                for (int k = 0; k < matrizA[0].length; k++) {
                    // Calcular el producto de los elementos correspondientes y sumar al resultado
                    suma += matrizA[i][k] * matrizB[k][j];
                }
                // Asignar la suma al elemento correspondiente en la matriz resultado
                matrizC[i][j] = suma;
            }
        }
        // Devolver la matriz resultado
        return matrizC;
    }
}
