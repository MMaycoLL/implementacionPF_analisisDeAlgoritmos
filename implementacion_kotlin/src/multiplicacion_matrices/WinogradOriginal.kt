package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class WinogradOriginal : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a int
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToIntArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToIntArray(matriz2)
        val doubleResultado = multiplicarWinogradOriginal(doubleMatriz1, doubleMatriz2)

        // Convertir el resultado double a BigINteger
        convertidor.convertIntArrayToBigInteger(doubleResultado)
    }

    /**
     * Permite multiplicar dos matrices mediante el método WinogradOriginal
     *
     * @param matrizA primera matriz
     * @param matrizB segunda matriz
     * @return matrizC matriz resultado de la multiplicación entre A y B
     */
    fun multiplicarWinogradOriginal(matrizA: Array<IntArray>, matrizB: Array<IntArray>): Array<IntArray> {
        val N = matrizA.size
        val P = matrizA[0].size
        val M = matrizB[0].size
        val matrizC = Array(N) { IntArray(M) }
        val y = IntArray(M)
        val z = IntArray(N)

        var aux: Int
        val upsilon = P % 2
        val gamma = P - upsilon

        var i: Int
        var j: Int
        var k: Int

        i = 0
        while (i < M) {
            aux = 0
            j = 0
            while (j < gamma) {
                aux += matrizA[i][j] * matrizA[i][j + 1]
                j += 2
            }
            y[i] = aux
            i++
        }
        i = 0
        while (i < N) {
            aux = 0
            j = 0
            while (j < gamma) {
                aux += matrizB[j][i] * matrizB[j + 1][i]
                j += 2
            }
            z[i] = aux
            i++
        }
        if (upsilon == 1) {
            /*
			 * P is odd The value matrizA[i][P]*matrizB[P][k] is missing in all auxiliary
			 * sums.
			 */
            val PP = P - 1
            i = 0
            while (i < M) {
                k = 0
                while (k < N) {
                    aux = 0
                    j = 0
                    while (j < gamma) {
                        aux += (matrizA[i][j] + matrizB[j + 1][k]) * (matrizA[i][j + 1] + matrizB[j][k])
                        j += 2
                    }
                    matrizC[i][k] = aux - y[i] - z[k] + matrizA[i][PP] * matrizB[PP][k]
                    k++
                }
                i++
            }
        } else {
            /*
			 * P is even The result can be computed with the auxiliary sums.
			 */
            i = 0
            while (i < M) {
                k = 0
                while (k < N) {
                    aux = 0
                    j = 0
                    while (j < gamma) {
                        aux += (matrizA[i][j] + matrizB[j + 1][k]) * (matrizA[i][j + 1] + matrizB[j][k])
                        j += 2
                    }
                    matrizC[i][k] = aux - y[i] - z[k]
                    k++
                }
                i++
            }
        }
        return matrizC
    }
}
