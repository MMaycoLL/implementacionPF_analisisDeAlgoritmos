package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class SequentialBlockV3 : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2)
        val doubleResultado = sequentialBlock_V3(doubleMatriz1, doubleMatriz2, doubleMatriz1.size)

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado)
    }

    fun sequentialBlock_V3(A: Array<IntArray>, B: Array<IntArray>, block_size: Int): Array<IntArray> {
        val n = A.size
        val C = Array(n) { IntArray(n) }

        // Recorre las matrices en bloques de tamaño block_size
        var i = 0
        while (i < n) {
            var j = 0
            while (j < n) {
                var k = 0
                while (k < n) {
                    // Multiplica los bloques de matrices
//	                for (int ii = i; ii < Math.min(i + block_size, n); ii++) {
                    var ii = i
                    while (ii < i + block_size && ii < n) {
                        //	                    for (int jj = j; jj < Math.min(j + block_size, n); jj++) {
                        var jj = j
                        while (jj < j + block_size && ii < n) {
                            //	                        for (int kk = k; kk < Math.min(k + block_size, n); kk++) {
                            var kk = k
                            while (kk < k + block_size && k < n) {
                                C[kk][ii] += A[kk][jj] * B[jj][ii]

                                kk++
                            }
                            jj++
                        }
                        ii++
                    }
                    k += block_size
                }
                j += block_size
            }
            i += block_size
        }
        return C
    }
}
