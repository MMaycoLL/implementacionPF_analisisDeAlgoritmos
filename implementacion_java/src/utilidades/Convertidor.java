package utilidades;

import java.math.BigInteger;

public class Convertidor {


    // Método para convertir BigInteger[] a double[]
    public double[][] convertBigIntegerArrayToDoubleArray(final BigInteger[][] bigIntArray) {
        double[][] doubleArray = new double[bigIntArray.length][bigIntArray.length];
        for (int i = 0; i < bigIntArray.length; i++) {
            for (int j = 0; j < bigIntArray.length; j++) {
                doubleArray[i][j] = bigIntArray[i][j].intValue();
            }
        }
        return doubleArray;
    }

    // Método para convertir double[] a BigInteger
    public BigInteger[][] convertDoubleArrayToBigInteger(final double[][] doubleArray) {
        int rows = doubleArray.length;
        int cols = doubleArray[0].length;
        BigInteger[][] bigIntegerArray = new BigInteger[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Convierte cada número decimal a BigInteger y almacénalo en la matriz
                // resultante
                bigIntegerArray[i][j] = BigInteger.valueOf((long) doubleArray[i][j]);
            }
        }

        return bigIntegerArray;
    }

    // Método para convertir BigInteger[] a int[]
    public int[][] convertBigIntegerArrayToIntArray(final BigInteger[][] bigIntArray) {
        int[][] intArray = new int[bigIntArray.length][bigIntArray.length];
        for (int i = 0; i < bigIntArray.length; i++) {
            for (int j = 0; j < bigIntArray.length; j++) {
                intArray[i][j] = bigIntArray[i][j].intValue();
            }
        }
        return intArray;
    }

    // Método para convertir int[] a BigInteger
    public BigInteger[][] convertIntArrayToBigInteger(final int[][] doubleArray) {
        int rows = doubleArray.length;
        int cols = doubleArray[0].length;
        BigInteger[][] bigIntegerArray = new BigInteger[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Convierte cada número decimal a BigInteger y almacénalo en la matriz
                // resultante
                bigIntegerArray[i][j] = BigInteger.valueOf(doubleArray[i][j]);
            }
        }

        return bigIntegerArray;
    }

}
