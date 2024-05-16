package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger
import java.util.concurrent.Executors
import kotlin.math.min

class ParallelBlockV4 : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2)
        val doubleResultado = multiplicarParallelBlockV4(doubleMatriz1, doubleMatriz2, doubleMatriz1.size)

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado)
    }

    fun multiplicarParallelBlockV4(
        matrix1: Array<IntArray>,
        matrix2: Array<IntArray>,
        blockSize: Int
    ): Array<IntArray> {
        val m = matrix1.size
        val n = matrix2[0].size
        val result = Array(m) { IntArray(n) }
        val numThreads = Runtime.getRuntime().availableProcessors()
        val executor = Executors.newFixedThreadPool(numThreads)

        var i = 0
        while (i < m) {
            var j = 0
            while (j < n) {
                var k = 0
                while (k < matrix1[0].size) {
                    executor.execute(MatrixMultiplier(matrix1, matrix2, result, i, j, k, blockSize))
                    k += blockSize
                }
                j += blockSize
            }
            i += blockSize
        }

        executor.shutdown()
        while (!executor.isTerminated);
        return result
    }

    // Clase para realizar la multiplicación de matrices en paralelo
    private inner class MatrixMultiplier(
        private val matrix1: Array<IntArray>,
        private val matrix2: Array<IntArray>,
        private val result: Array<IntArray>,
        private val startRow: Int,
        private val startCol: Int,
        private val startK: Int,
        private val blockSize: Int
    ) : Runnable {
        override fun run() {
            val endRow = min((startRow + blockSize).toDouble(), matrix1.size.toDouble()).toInt()
            val endCol = min((startCol + blockSize).toDouble(), matrix2[0].size.toDouble()).toInt()
            val endK = min((startK + blockSize).toDouble(), matrix1[0].size.toDouble()).toInt()

            for (i in startRow until endRow) {
                for (j in startCol until endCol) {
                    for (k in startK until endK) {
                        result[k][i] += matrix1[k][j] * matrix2[j][i]
                    }
                }
            }
        }
    }
}
