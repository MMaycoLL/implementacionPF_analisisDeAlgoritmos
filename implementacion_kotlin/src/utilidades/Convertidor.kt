package utilidades

import java.math.BigInteger

class Convertidor {
    // Método para convertir BigInteger[] a double[]
    fun convertBigIntegerArrayToDoubleArray(bigIntArray: Array<Array<BigInteger>>): Array<DoubleArray> {
        val doubleArray = Array(bigIntArray.size) { DoubleArray(bigIntArray.size) }
        for (i in bigIntArray.indices) {
            for (j in bigIntArray.indices) {
                doubleArray[i][j] = bigIntArray[i][j].toInt().toDouble()
            }
        }
        return doubleArray
    }

    // Método para convertir double[] a BigInteger
    fun convertDoubleArrayToBigInteger(doubleArray: Array<DoubleArray>): Array<Array<BigInteger?>> {
        val rows = doubleArray.size
        val cols = doubleArray[0].size
        val bigIntegerArray = Array(rows) { arrayOfNulls<BigInteger>(cols) }

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                // Convierte cada número decimal a BigInteger y almacénalo en la matriz
                // resultante
                bigIntegerArray[i][j] = BigInteger.valueOf(doubleArray[i][j].toLong())
            }
        }

        return bigIntegerArray
    }

    // Método para convertir BigInteger[] a int[]
    fun convertBigIntegerArrayToIntArray(bigIntArray: Array<Array<BigInteger>>): Array<IntArray> {
        val intArray = Array(bigIntArray.size) { IntArray(bigIntArray.size) }
        for (i in bigIntArray.indices) {
            for (j in bigIntArray.indices) {
                intArray[i][j] = bigIntArray[i][j].toInt()
            }
        }
        return intArray
    }

    // Método para convertir int[] a BigInteger
    fun convertIntArrayToBigInteger(doubleArray: Array<IntArray>): Array<Array<BigInteger?>> {
        val rows = doubleArray.size
        val cols = doubleArray[0].size
        val bigIntegerArray = Array(rows) { arrayOfNulls<BigInteger>(cols) }

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                // Convierte cada número decimal a BigInteger y almacénalo en la matriz
                // resultante
                bigIntegerArray[i][j] = BigInteger.valueOf(doubleArray[i][j].toLong())
            }
        }

        return bigIntegerArray
    }
}
