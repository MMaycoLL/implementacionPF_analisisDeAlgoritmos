package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min

class EnhancedParallelBlockIV5 : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a double
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2)
        val doubleResultado = multiplyMatrices(doubleMatriz1, doubleMatriz2)

        // Convertir el resultado double a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado)
    }

    private class MatrixMultiplier(
        private val a: Array<DoubleArray>, private val b: Array<DoubleArray>, private val result: Array<DoubleArray>,
        private val startRow: Int, private val startCol: Int, private val startCommon: Int,
        private val endRow: Int, private val endCol: Int, private val endCommon: Int
    ) : Runnable {
        override fun run() {
            for (i in startRow until endRow) {
                for (j in startCol until endCol) {
                    synchronized(result) { // Synchronize on the result matrix
                        for (k in startCommon until endCommon) {
                            result[i][k] += a[i][j] * b[j][k]
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val NUM_THREADS = Runtime.getRuntime().availableProcessors()

        fun multiplyMatrices(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
            val aRows = a.size
            val aCols = a[0].size
            val bCols = b[0].size

            require(aCols == b.size) { "Matrices cannot be multiplied: dimensions mismatch" }

            val result = Array(aRows) { DoubleArray(bCols) }
            val blockSize = max(1.0, (aRows / NUM_THREADS).toDouble())
                .toInt()

            val executor = Executors.newFixedThreadPool(NUM_THREADS)

            var i = 0
            while (i < aRows) {
                var j = 0
                while (j < bCols) {
                    var k = 0
                    while (k < aCols) {
                        executor.submit(
                            MatrixMultiplier(
                                a, b, result, i, j, k,
                                min((i + blockSize).toDouble(), aRows.toDouble()).toInt(),
                                min((j + blockSize).toDouble(), bCols.toDouble()).toInt(),
                                min((k + blockSize).toDouble(), aCols.toDouble()).toInt()
                            )
                        )
                        k += blockSize
                    }
                    j += blockSize
                }
                i += blockSize
            }

            executor.shutdown()
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return result
        }
    }
}