import utilidades.AlgoritmoHandler
import utilidades.AlgoritmoTiempo
import utilidades.ChartUtil
import utilidades.FileUtil
import java.math.BigInteger
import java.util.*
import javax.swing.JFrame

class Main(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) :
    JFrame("Tiempos de Ejecución") {
    init {
        val handler = AlgoritmoHandler(matriz1, matriz2)
        val arregloDeTiempos = handler.arregloDeTiempos

        Arrays.sort(arregloDeTiempos, Comparator.comparing { obj: AlgoritmoTiempo -> obj.tiempo })
        FileUtil.guardarTiemposEnArchivo(arregloDeTiempos, "datos/src/tiempos_kotlin.txt")

        for (tiempo in arregloDeTiempos) {
            if (tiempo != null) {
                println("Tiempo de ejecución de " + tiempo.nombre + ": " + tiempo.tiempo + " ms")
            }
        }

        val chartPanel = ChartUtil.createChartPanel(arregloDeTiempos)
        contentPane = chartPanel
    }

    companion object {
        private const val FILE_PATH1 = "datos/src/matriz_1.txt"
        private const val FILE_PATH2 = "datos/src/matriz_2.txt"

        @JvmStatic
        fun main(args: Array<String>) {
            val matrizA = FileUtil.readNumbersFromFile(FILE_PATH1)
            val matrizB = FileUtil.readNumbersFromFile(FILE_PATH2)

            val main = Main(matrizA, matrizB)
            main.pack()
            main.isVisible = true
        }
    }
}

