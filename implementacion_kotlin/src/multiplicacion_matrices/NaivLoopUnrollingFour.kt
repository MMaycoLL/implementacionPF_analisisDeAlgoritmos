package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class NaivLoopUnrollingFour : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2)
        val doubleResultado = multiplicarNaivLoopUnrollingFour(doubleMatriz1, doubleMatriz2)

        // Convertir el resultado int a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado)
    }

    /**
     * Permite multiplicar dos matrices mediante el método NaivLoopUnrollingFour
     *
     * @param matrizA primera matriz
     * @param matrizB segunda matriz
     * @return matrizC matriz resultado de la multiplicación entre A y B
     */
    fun multiplicarNaivLoopUnrollingFour(matrizA: Array<IntArray>, matrizB: Array<IntArray>): Array<IntArray> {
        val m = matrizA.size
        val n = matrizA[0].size
        val p = matrizB[0].size

        val matrizC = Array(m) { IntArray(p) }
        var aux = 0

        if (p % 4 == 0) {
            for (i in 0 until m) {
                for (j in 0 until p) {
                    var k = 0
                    while (k < n) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j] + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j]
                        k += 4
                    }
                    matrizC[i][j] = aux
                }
            }
        } else if (p % 4 == 1) {
            val pp = p - 1
            for (i in 0 until m) {
                for (j in 0 until p) {
                    aux = 0
                    var k = 0
                    while (k < pp) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j] + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j]
                        k += 4
                    }
                    matrizC[i][j] = aux + matrizA[i][pp] * matrizB[pp][j]
                }
            }
        } else if (p % 4 == 2) {
            val pp = p - 2
            val ppp = p - 1
            for (i in 0 until m) {
                for (j in 0 until p) {
                    aux = 0
                    var k = 0
                    while (k < pp) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j] + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j]

                        k += 4
                    }
                    matrizC[i][j] = aux + matrizA[i][pp] * matrizB[pp][j] + matrizA[i][ppp] * matrizB[ppp][j]
                }
            }
        } else {
            val pp = p - 3
            val ppp = p - 2
            val pppp = p - 1

            for (i in 0 until m) {
                for (j in 0 until p) {
                    aux = 0
                    var k = 0
                    while (k < pp) {
                        aux += matrizA[i][k] * matrizB[k][j] + matrizA[i][k + 1] * matrizB[k + 1][j] + matrizA[i][k + 2] * matrizB[k + 2][j] + matrizA[i][k + 3] * matrizB[k + 3][j]

                        k += 4
                    }
                    matrizC[i][j] =
                        aux + matrizA[i][pp] * matrizB[pp][j] + matrizA[i][ppp] * matrizB[ppp][j] + matrizA[i][pppp] * matrizB[i][pppp]
                }
            }
        }
        return matrizC
    }
}
