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

    // Método para multiplicar dos matrices utilizando desenrollado de bucles (loop unrolling) con un factor de 4
    public int[][] multiplicarNaivLoopUnrollingFour(int[][] matrizA, int[][] matrizB) {
        // Obtener las dimensiones de las matrices de entrada
        int m = matrizA.length; // Número de filas de la matriz A
        int n = matrizA[0].length; // Número de columnas de la matriz A
        int p = matrizB[0].length; // Número de columnas de la matriz B

        // Crear una matriz para almacenar el resultado de la multiplicación
        int[][] matrizC = new int[m][p];

        int aux = 0; // Variable auxiliar para almacenar las sumas parciales

        // Verificar el residuo de la división por 4 del número de columnas de la matriz B
        if (p % 4 == 0) {
            // Si el residuo es 0, realizar la multiplicación con desenrollado de bucles de factor 4
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    // Inicializar la variable auxiliar
                    aux = 0;
                    // Iterar sobre los elementos de las matrices A y B con desenrollado de bucles de factor 4
                    for (int k = 0; k < n; k += 4) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j]
                                + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j];
                    }
                    // Asignar el valor de la variable auxiliar al resultado
                    matrizC[i][j] = aux;
                }
            }
        } else if (p % 4 == 1) {
            // Si el residuo es 1, realizar la multiplicación con desenrollado de bucles de factor 4 ajustado
            int pp = p - 1;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    // Inicializar la variable auxiliar
                    aux = 0;
                    // Iterar sobre los elementos de las matrices A y B con desenrollado de bucles de factor 4 ajustado
                    for (int k = 0; k < pp; k += 4) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j]
                                + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j];
                    }
                    // Asignar el valor de la variable auxiliar y el elemento de matriz restante al resultado
                    matrizC[i][j] = aux + matrizA[i][pp] * matrizB[pp][j];
                }
            }
        } else if (p % 4 == 2) {
            // Si el residuo es 2, realizar la multiplicación con desenrollado de bucles de factor 4 ajustado
            int pp = p - 2;
            int ppp = p - 1;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    // Inicializar la variable auxiliar
                    aux = 0;
                    // Iterar sobre los elementos de las matrices A y B con desenrollado de bucles de factor 4 ajustado
                    for (int k = 0; k < pp; k += 4) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j]
                                + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j];
                    }
                    // Asignar el valor de la variable auxiliar y los elementos de matriz restantes al resultado
                    matrizC[i][j] = aux + matrizA[i][pp] * matrizB[pp][j] + matrizA[i][ppp] * matrizB[ppp][j];
                }
            }
        } else {
            // Si el residuo es 3, realizar la multiplicación con desenrollado de bucles de factor 4 ajustado
            int pp = p - 3;
            int ppp = p - 2;
            int pppp = p - 1;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    // Inicializar la variable auxiliar
                    aux = 0;
                    // Iterar sobre los elementos de las matrices A y B con desenrollado de bucles de factor 4 ajustado
                    for (int k = 0; k < pp; k += 4) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j]
                                + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j];
                    }
                    // Asignar el valor de la variable auxiliar y los elementos de matriz restantes al resultado
                    matrizC[i][j] = aux + matrizA[i][pp] * matrizB[pp][j] + matrizA[i][ppp] * matrizB[ppp][j]
                            + matrizA[i][pppp] * matrizB[i][pppp];
                }
            }
        }
        // Devolver la matriz resultado
        return matrizC;
    }
}
