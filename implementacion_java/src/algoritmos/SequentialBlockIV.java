package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class SequentialBlockIV implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        int[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1);
        int[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2);
        int[][] doubleResultado = sequentialBlock_IV3(doubleMatriz1, doubleMatriz2, doubleMatriz1.length);

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado);
    }

    public int[][] sequentialBlock_IV3(int[][] A, int[][] B, int block_size) {
        int n = A.length; // Tamaño de la matriz (se asume que es cuadrada)
        int[][] C = new int[n][n]; // Matriz de resultado

        // Recorre las matrices en bloques de tamaño block_size
        for (int i = 0; i < n; i += block_size) { // Recorrido por filas con incrementos de tamaño block_size
            for (int j = 0; j < n; j += block_size) { // Recorrido por columnas con incrementos de tamaño block_size
                for (int k = 0; k < n; k += block_size) { // Recorrido para la multiplicación de bloques
                    // Multiplica los bloques de matrices
                    for (int ii = i; ii < i + block_size && ii < n; ii++) { // Recorrido por filas dentro del bloque
                        for (int jj = j; jj < j + block_size && jj < n; jj++) { // Recorrido por columnas dentro del bloque
                            for (int kk = k; kk < k + block_size && kk < n; kk++) { // Recorrido para la multiplicación de elementos dentro del bloque
                                // Realiza la multiplicación del bloque actual y acumula el resultado en la matriz C
                                C[ii][kk] += A[ii][jj] * B[jj][kk];
                            }
                        }
                    }
                }
            }
        }

        return C; // Devolver la matriz de resultado
    }
}
