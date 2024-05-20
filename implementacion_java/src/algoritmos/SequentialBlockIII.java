package algoritmos;

import utilidades.AlgoritmoMultiplicacion;
import utilidades.Convertidor;

import java.math.BigInteger;

public class SequentialBlockIII implements AlgoritmoMultiplicacion {

    Convertidor convertidor = new Convertidor();

    @Override
    public void multiplicar(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        // Convertir de BigInteger a int
        int[][] doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1);
        int[][] doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2);
        int[][] doubleResultado = sequentialBlockIII3(doubleMatriz1, doubleMatriz2, doubleMatriz1.length);

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado);
    }

    // Método para multiplicar dos matrices en bloques con un tamaño de bloque específico
    public int[][] sequentialBlockIII3(int[][] A, int[][] B, int block_size) {
        int n = A.length;
        int[][] C = new int[n][n]; // Matriz para almacenar el resultado

        // Recorre las matrices en bloques de tamaño block_size
        for (int i = 0; i < n; i += block_size) {
            for (int j = 0; j < n; j += block_size) {
                for (int k = 0; k < n; k += block_size) {
                    // Multiplica los bloques de matrices
                    for (int ii = i; ii < i + block_size && ii < n; ii++) {
                        for (int jj = j; jj < j + block_size && jj < n; jj++) {
                            for (int kk = k; kk < k + block_size && kk < n; kk++) {
                                C[ii][jj] += A[ii][kk] * B[kk][jj]; // Actualiza la celda correspondiente en C
                            }
                        }
                    }
                }
            }
        }
        return C; // Devuelve el resultado de la multiplicación de matrices
    }

}
