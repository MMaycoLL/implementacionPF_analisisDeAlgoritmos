package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class WinogradScaled : AlgoritmoMultiplicacion {

    private val convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a double
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2)
        val doubleResultado = multiplicarWinogradScaled(doubleMatriz1, doubleMatriz2)

        // Convertir el resultado double a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado)
    }

    private fun multiplicarWinogradScaled(A: Array<DoubleArray>, B: Array<DoubleArray>): Array<DoubleArray> {
        val N = A.size
        val P = A[0].size
        val M = B[0].size
        val C: Array<DoubleArray>

        // Create scaled copies of A and B
        val copyA = Array(N) { DoubleArray(P) }
        val copyB = Array(P) { DoubleArray(M) }

        // Scaling factors
        val a = NormInf(A, N, P)
        val b = NormInf(B, P, M)
        val lambda = Math.floor(0.5 + Math.log(b / a) / Math.log(4.0))

        // Scaling
        MultiplyWithScalar(A, copyA, N, P, Math.pow(2.0, lambda))
        MultiplyWithScalar(B, copyB, P, M, Math.pow(2.0, -lambda))

        // Using Winograd with scaled matrices
        C = multiplyWinograd(copyA, copyB)

        return C
    }

    /**
     * Multiplies matrices using Winograd algorithm.
     */
    private fun multiplyWinograd(A: Array<DoubleArray>, B: Array<DoubleArray>): Array<DoubleArray> {
        val N = A.size
        val M = B[0].size
        val P = B.size
        val C = Array(N) { DoubleArray(M) }

        // Intermediate variables for Winograd algorithm
        val rowFactor = DoubleArray(N)
        val colFactor = DoubleArray(M)

        // Precompute row factors
        for (i in 0 until N) {
            rowFactor[i] = A[i][0] * A[i][1]
            for (j in 2 until P step 2) {
                rowFactor[i] += A[i][j] * A[i][j + 1]
            }
        }

        // Precompute column factors
        for (j in 0 until M) {
            colFactor[j] = B[0][j] * B[1][j]
            for (i in 2 until P step 2) {
                colFactor[j] += B[i][j] * B[i + 1][j]
            }
        }

        // Actual matrix multiplication
        for (i in 0 until N) {
            for (j in 0 until M) {
                C[i][j] = -rowFactor[i] - colFactor[j]
                for (k in 0 until P step 2) {
                    C[i][j] += (A[i][k] + B[k + 1][j]) * (A[i][k + 1] + B[k][j])
                }
            }
        }

        // Adjust for odd dimension
        if (P % 2 == 1) {
            for (i in 0 until N) {
                for (j in 0 until M) {
                    C[i][j] += A[i][P - 1] * B[P - 1][j]
                }
            }
        }

        return C
    }

    /**
     * Calcula la norma infinito de una matriz.
     *
     *@param A Matriz de entrada.
     * @param N Número de filas.
     * @param M Número de columnas.
     * @return La norma infinito de la matriz.
     */
    fun NormInf(A: Array<DoubleArray>, N: Int, M: Int): Double {
        var max = Double.NEGATIVE_INFINITY
        for (i in 0 until N) {
            var sum = 0.0
            for (j in 0 until M) {
                sum += Math.abs(A[i][j])
            }
            if (sum > max) {
                max = sum
            }
        }
        return max
    }

    /**
     * Multiplica una matriz por un escalar.
     *
     * @param A      Matriz de entrada.
     * @param B      Matriz de salida.
     * @param N      Número de filas.
     * @param M      Número de columnas.
     * @param scalar Escalar.
     */
    fun MultiplyWithScalar(A: Array<DoubleArray>, B: Array<DoubleArray>, N: Int, M: Int, scalar: Double) {
        for (i in 0 until N) {
            for (j in 0 until M) {
                B[i][j] = A[i][j] * scalar
            }
        }
    }


}