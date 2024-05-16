package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class StrassenWinograd : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2)
        val doubleResultado = multiplicarStrassenWinograd(doubleMatriz1, doubleMatriz2)

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado)
    }

    private val THRESHOLD = 32

    fun multiplicarStrassenWinograd(A: Array<IntArray>, B: Array<IntArray>): Array<IntArray> {
        val n = A.size
        val C = Array(n) { IntArray(n) }
        if (n <= THRESHOLD) {
            for (i in 0 until n) {
                for (j in 0 until n) {
                    for (k in 0 until n) {
                        C[i][j] += A[i][k] * B[k][j]
                    }
                }
            }
            return C
        } else {
            val A11 = split(A, 0, 0)
            val A12 = split(A, 0, n / 2)
            val A21 = split(A, n / 2, 0)
            val A22 = split(A, n / 2, n / 2)

            val B11 = split(B, 0, 0)
            val B12 = split(B, 0, n / 2)
            val B21 = split(B, n / 2, 0)
            val B22 = split(B, n / 2, n / 2)

            val P1 = multiplicarStrassenWinograd(add(A11, A22), add(B11, B22))
            val P2 = multiplicarStrassenWinograd(add(A21, A22), B11)
            val P3 = multiplicarStrassenWinograd(A11, sub(B12, B22))
            val P4 = multiplicarStrassenWinograd(A22, sub(B21, B11))
            val P5 = multiplicarStrassenWinograd(add(A11, A12), B22)
            val P6 = multiplicarStrassenWinograd(sub(A21, A11), add(B11, B12))
            val P7 = multiplicarStrassenWinograd(sub(A12, A22), add(B21, B22))

            val C11 = add(sub(add(P1, P4), P5), P7)
            val C12 = add(P3, P5)
            val C21 = add(P2, P4)
            val C22 = add(sub(add(P1, P3), P2), P6)

            join(C11, C, 0, 0)
            join(C12, C, 0, n / 2)
            join(C21, C, n / 2, 0)
            join(C22, C, n / 2, n / 2)

            return C
        }
    }

    private fun split(A: Array<IntArray>, iB: Int, jB: Int): Array<IntArray> {
        val nB = A.size / 2
        val B = Array(nB) { IntArray(nB) }
        var i1 = 0
        var i2 = iB
        while (i1 < nB) {
            var j1 = 0
            var j2 = jB
            while (j1 < nB) {
                B[i1][j1] = A[i2][j2]
                j1++
                j2++
            }
            i1++
            i2++
        }
        return B
    }

    private fun join(A: Array<IntArray>, B: Array<IntArray>, iB: Int, jB: Int) {
        val nB = A.size
        var i1 = 0
        var i2 = iB
        while (i1 < nB) {
            var j1 = 0
            var j2 = jB
            while (j1 < nB) {
                B[i2][j2] = A[i1][j1]
                j1++
                j2++
            }
            i1++

            i2++
        }
    }

    private fun add(A: Array<IntArray>, B: Array<IntArray>): Array<IntArray> {
        val n = A.size
        val C = Array(n) { IntArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                C[i][j] = A[i][j] + B[i][j]
            }
        }
        return C
    }

    private fun sub(A: Array<IntArray>, B: Array<IntArray>): Array<IntArray> {
        val n = A.size
        val C = Array(n) { IntArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                C[i][j] = A[i][j] - B[i][j]
            }
        }
        return C
    }
}
