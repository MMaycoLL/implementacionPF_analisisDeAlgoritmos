package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class SequentialBlockIII : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2)
        val doubleResultado = sequentialBlockIII3(doubleMatriz1, doubleMatriz2, doubleMatriz1.size)

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado)
    }

    /**
     * @param A          matriz
     * @param B          matriz
     * @param block_size tamaño del bloque
     * @return matriz resultante de la multiplicación
     */
    fun sequentialBlockIII3(A: Array<IntArray>, B: Array<IntArray>, block_size: Int): Array<IntArray> {
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
                    var ii = i
                    while (ii < i + block_size && ii < n) {
                        var jj = j
                        while (jj < j + block_size && ii < n) {
                            var kk = k
                            while (kk < k + block_size && k < n) {
                                C[ii][jj] += A[ii][kk] * B[kk][jj]

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
