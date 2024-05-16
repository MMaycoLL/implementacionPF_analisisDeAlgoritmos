package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class SequentialBlockIV : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2)
        val doubleResultado = sequentialBlock_IV3(doubleMatriz1, doubleMatriz2, doubleMatriz1.size)

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado)
    }

    fun sequentialBlock_IV3(A: Array<IntArray>, B: Array<IntArray>, block_size: Int): Array<IntArray> {
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
                                C[ii][kk] += A[ii][jj] * B[jj][kk]
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

    // Método para convertir BigInteger[] a int[]
    private fun convertBigIntegerArrayToDoubleArray(bigIntArray: Array<Array<BigInteger>>): Array<IntArray> {
        val doubleArray = Array(bigIntArray.size) { IntArray(bigIntArray.size) }
        for (i in bigIntArray.indices) {
            for (j in bigIntArray.indices) {
                doubleArray[i][j] = bigIntArray[i][j].toInt()
            }
        }
        return doubleArray
    }

    // Método para convertir int[] a BigInteger
    private fun convertDoubleArrayToBigInteger(doubleArray: Array<IntArray>): Array<Array<BigInteger?>> {
        val rows = doubleArray.size
        val cols = doubleArray[0].size
        val bigIntegerArray = Array(rows) { arrayOfNulls<BigInteger>(cols) }

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                // Convierte cada número decimal a BigInteger y almacénalo en la matriz
                // resultante
                bigIntegerArray[i][j] = BigInteger.valueOf(doubleArray[i][j].toLong())
            }
        }

        return bigIntegerArray
    }
}
