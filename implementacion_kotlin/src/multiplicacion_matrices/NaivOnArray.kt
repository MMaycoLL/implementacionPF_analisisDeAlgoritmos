package multiplicacion_matrices

import utilidades.AlgoritmoMultiplicacion
import utilidades.Convertidor
import java.math.BigInteger

class NaivOnArray : AlgoritmoMultiplicacion {
    var convertidor: Convertidor = Convertidor()

    override fun multiplicar(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        // Convertir de BigInteger a double
        val doubleMatriz1 = convertidor.convertBigIntegerArrayToDoubleArray(matriz1)
        val doubleMatriz2 = convertidor.convertBigIntegerArrayToDoubleArray(matriz2)
        val doubleResultado = multiplicarNaivOnArray(doubleMatriz1, doubleMatriz2)

        // Convertir el resultado double a BigINteger
        convertidor.convertDoubleArrayToBigInteger(doubleResultado)
    }

    /**
     * Método para multiplicar dos matrices usando el método NaivOnArray
     *
     * @param matrizA primera matriz
     * @param matrizB segunda matriz
     * @return matrizC matriz resultado de la multiplicación entre A y B
     */
    fun multiplicarNaivOnArray(matrizA: Array<DoubleArray>, matrizB: Array<DoubleArray>): Array<DoubleArray> {
        val m = matrizA.size
        val n = matrizB[0].size
        val matrizC = Array(m) { DoubleArray(n) }

        for (i in 0 until m) {
            for (j in 0 until n) {
                var suma = 0
                for (k in matrizA[0].indices) {
                    suma = (suma + matrizA[i][k] * matrizB[k][j]).toInt()
                }
                matrizC[i][j] = suma.toDouble()
            }
        }
        return matrizC
    }
}
