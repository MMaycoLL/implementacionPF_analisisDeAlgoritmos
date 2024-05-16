package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class NaivLoopUnrollingTwo : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a double
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2)
        val doubleResultado = multiplicarNaivLoopUnrollingTwo(doubleMatriz1, doubleMatriz2)

        // Convertir el resultado double a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado)
    }

    /**
     * Permite multiplicar dos matrices mediante el método NaivLoopUnrollingTwo
     *
     * @param matrizA primera matriz
     * @param matrizB segunda matriz
     * @return matrizC matriz resultado de la multiplicación entre A y B
     */
    fun multiplicarNaivLoopUnrollingTwo(matrizA: Array<DoubleArray>, matrizB: Array<DoubleArray>): Array<DoubleArray> {
        val m = matrizA.size
        val n = matrizB[0].size
        val p = matrizB.size

        val matrizC = Array(n) { DoubleArray(m) }

        if (p % 2 == 0) {
            for (i in 0 until n) {
                for (j in 0 until m) {
                    var sum1 = 0.0
                    var sum2 = 0.0
                    var k = 0
                    while (k < p) {
                        sum1 += matrizA[i][k] * matrizB[k][j]
                        sum2 += matrizA[i][k + 1] * matrizB[k + 1][j]
                        k += 2
                    }
                    matrizC[i][j] = sum1 + sum2
                }
            }
        } else {
            val pp = p - 1
            for (i in 0 until n) {
                for (j in 0 until m) {
                    var sum1 = 0.0
                    var sum2 = 0.0
                    var k = 0
                    while (k < pp) {
                        sum1 += matrizA[i][k] * matrizB[k][j]
                        sum2 += matrizA[i][k + 1] * matrizB[k + 1][j]

                        k += 2
                    }
                    matrizC[i][j] = (sum1 + sum2) + matrizA[i][pp] * matrizB[pp][j]
                }
            }
        }
        return matrizC
    }
}
