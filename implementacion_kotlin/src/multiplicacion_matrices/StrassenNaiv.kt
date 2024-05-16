package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class StrassenNaiv : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2)
        val doubleResultado = strassenNaiv(doubleMatriz1, doubleMatriz2, 0)

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado)
    }

    fun strassenNaiv(A: Array<IntArray>, B: Array<IntArray>, threshold: Int): Array<IntArray> {
        val n = A.size
        val C = Array(n) { IntArray(n) }

        if (n <= threshold) {
            // Multiplicación Naive
            for (i in 0 until n) {
                for (j in 0 until n) {
                    for (k in 0 until n) {
                        C[i][j] += A[i][k] * B[k][j]
                    }
                }
            }
        } else {
            // Dividir las matrices en submatrices más pequeñas
            val A11 = Array(n / 2) { IntArray(n / 2) }
            val A12 = Array(n / 2) { IntArray(n / 2) }
            val A21 = Array(n / 2) { IntArray(n / 2) }
            val A22 = Array(n / 2) { IntArray(n / 2) }
            val B11 = Array(n / 2) { IntArray(n / 2) }
            val B12 = Array(n / 2) { IntArray(n / 2) }
            val B21 = Array(n / 2) { IntArray(n / 2) }
            val B22 = Array(n / 2) { IntArray(n / 2) }
            for (i in 0 until n / 2) {
                for (j in 0 until n / 2) {
                    A11[i][j] = A[i][j]
                    A12[i][j] = A[i][j + n / 2]
                    A21[i][j] = A[i + n / 2][j]
                    A22[i][j] = A[i + n / 2][j + n / 2]
                    B11[i][j] = B[i][j]
                    B12[i][j] = B[i][j + n / 2]
                    B21[i][j] = B[i + n / 2][j]
                    B22[i][j] = B[i + n / 2][j + n / 2]
                }
            }

            // Calcular los productos de matrices utilizando el algoritmo de Strassen
            val P1 = strassenNaiv(add(A11, A22), add(B11, B22), threshold)
            val P2 = strassenNaiv(add(A21, A22), B11, threshold)
            val P3 = strassenNaiv(A11, subtract(B12, B22), threshold)
            val P4 = strassenNaiv(A22, subtract(B21, B11), threshold)
            val P5 = strassenNaiv(add(A11, A12), B22, threshold)
            val P6 = strassenNaiv(subtract(A21, A11), add(B11, B12), threshold)
            val P7 = strassenNaiv(subtract(A12, A22), add(B21, B22), threshold)

            // Calcular las submatrices de la matriz de producto C
            val C11 = subtract(add(add(P1, P4), P7), P5)
            val C12 = add(P3, P5)
            val C21 = add(P2, P4)
            val C22 = subtract(add(add(P1, P3), P6), P2)

            // Combinar las submatrices de la matriz de producto C
            for (i in 0 until n / 2) {
                for (j in 0 until n / 2) {
                    C[i][j] = C11[i][j]
                    C[i][j + n / 2] = C12[i][j]
                    C[i + n / 2][j] = C21[i][j]
                    C[i + n / 2][j + n / 2] = C22[i][j]
                }
            }
        }

        return C
    }

    fun add(A: Array<IntArray>, B: Array<IntArray>): Array<IntArray> {
        val n = A.size
        val C = Array(n) { IntArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                C[i][j] = A[i][j] + B[i][j]
            }
        }
        return C
    }

    fun subtract(A: Array<IntArray>, B: Array<IntArray>): Array<IntArray> {
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
