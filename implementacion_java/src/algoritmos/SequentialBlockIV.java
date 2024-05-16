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
        int n = A.length;
        int[][] C = new int[n][n];

        // Recorre las matrices en bloques de tamaño block_size
        for (int i = 0; i < n; i += block_size) {
            for (int j = 0; j < n; j += block_size) {
                for (int k = 0; k < n; k += block_size) {
                    // Multiplica los bloques de matrices
                    for (int ii = i; ii < i + block_size && ii < n; ii++) {
                        for (int jj = j; jj < j + block_size && ii < n; jj++) {
                            for (int kk = k; kk < k + block_size && k < n; kk++) {

                                C[ii][kk] += A[ii][jj] * B[jj][kk];
                            }
                        }
                    }
                }
            }
        }
        return C;
    }
}
