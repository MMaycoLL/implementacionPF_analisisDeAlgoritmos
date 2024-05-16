package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger
import java.util.concurrent.Executors

class ParallelBlockIII4 : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2)
        val doubleResultado = matrixMultiplicationParallel(doubleMatriz1, doubleMatriz2)

        // Convertir el resultado int a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado)
    }

    companion object {
        private val NUM_THREADS = Runtime.getRuntime().availableProcessors()

        fun matrixMultiplicationParallel(A: Array<DoubleArray>, B: Array<DoubleArray>): Array<DoubleArray> {
            val m = A.size
            val n = A[0].size
            val p = B.size
            val q = B[0].size

            require(n == p) { "Las dimensiones de las matrices no son compatibles para la multiplicación." }

            val C = Array(m) { DoubleArray(q) }

            val executor = Executors.newFixedThreadPool(NUM_THREADS)

            for (i in 0 until m) {
                val row = i
                executor.execute {
                    for (j in 0 until q) {
                        for (k in 0 until n) {
                            C[row][j] += A[row][k] * B[k][j]
                        }
                    }
                }
            }

            executor.shutdown()
            while (!executor.isTerminated) {
                // Esperar a que todas las tareas se completen
            }

            return C
        }
    }
}
