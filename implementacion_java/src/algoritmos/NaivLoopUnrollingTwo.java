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

    // Método para multiplicar dos matrices utilizando desenrollado de bucles (loop unrolling) con un factor de 2
    public double[][] multiplicarNaivLoopUnrollingTwo(double[][] matrizA, double[][] matrizB) {
        // Obtener las dimensiones de las matrices de entrada
        int m = matrizA.length; // Número de filas de la matriz A
        int n = matrizB[0].length; // Número de columnas de la matriz B
        int p = matrizB.length; // Número de filas de la matriz B

        // Crear una matriz para almacenar el resultado de la multiplicación
        double[][] matrizC = new double[n][m];

        // Verificar si el número de filas de la matriz B es par
        if (p % 2 == 0) {
            // Si es par, realizar la multiplicación con desenrollado de bucles
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    // Inicializar sumas parciales
                    double sum1 = 0.0;
                    double sum2 = 0.0;
                    // Iterar sobre los elementos de las matrices A y B con desenrollado de bucles de factor 2
                    for (int k = 0; k < p; k += 2) {
                        sum1 += matrizA[i][k] * matrizB[k][j];
                        sum2 += matrizA[i][k + 1] * matrizB[k + 1][j];
                    }
                    // Sumar las sumas parciales y asignar al resultado
                    matrizC[i][j] = sum1 + sum2;
                }
            }
        } else {
            // Si el número de filas de la matriz B es impar, ajustar el bucle de desenrollado
            int pp = p - 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    // Inicializar sumas parciales
                    double sum1 = 0.0;
                    double sum2 = 0.0;
                    // Iterar sobre los elementos de las matrices A y B con desenrollado de bucles de factor 2
                    for (int k = 0; k < pp; k += 2) {
                        sum1 += matrizA[i][k] * matrizB[k][j];
                        sum2 += matrizA[i][k + 1] * matrizB[k + 1][j];
                    }
                    // Sumar las sumas parciales y agregar el elemento de matriz restante
                    matrizC[i][j] = (sum1 + sum2) + matrizA[i][pp] * matrizB[pp][j];
                }
            }
        }
        // Devolver la matriz resultado
        return matrizC;
    }


}
