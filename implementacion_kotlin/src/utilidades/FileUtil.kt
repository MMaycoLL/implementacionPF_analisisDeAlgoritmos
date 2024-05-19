package utilidades

import java.io.*
import java.math.BigInteger

object FileUtil {
    @JvmStatic
    fun readNumbersFromFile(fileName: String): Array<Array<BigInteger>> {
        val rows = mutableListOf<Array<BigInteger>>()
        try {
            BufferedReader(FileReader(fileName)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    val numbersAsString = line!!.trim().split("\\s+".toRegex()).toTypedArray()
                    val row = Array(numbersAsString.size) { i ->
                        try {
                            BigInteger(numbersAsString[i])
                        } catch (e: NumberFormatException) {
                            println("Error parsing number: ${numbersAsString[i]}. Skipping...")
                            BigInteger.ZERO // Default value for skipped elements
                        }
                    }
                    rows.add(row)
                }
            }
        } catch (e: IOException) {
            println("Error reading file: ${e.message}")
        }
        return rows.toTypedArray()
    }

    @JvmStatic
    fun guardarTiemposEnArchivo(tiempos: Array<AlgoritmoTiempo?>, nombreArchivo: String) {
        try {
            BufferedWriter(FileWriter(nombreArchivo)).use { bw ->
                for (tiempo in tiempos) {
                    if (tiempo != null) {
                        bw.write("${tiempo.nombre}: ${tiempo.tiempo} ms")
                    }
                    bw.newLine()
                }
                println("Tiempos de ejecución guardados en el archivo '$nombreArchivo'.")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}


