package utilidades

import multiplicacion_matrices.*
import java.math.BigInteger

class AlgoritmoHandler(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
    val arregloDeTiempos: Array<AlgoritmoTiempo?> = arrayOfNulls(15)

    init {
        medirTiempos(matriz1, matriz2)
    }

    private fun medirTiempos(matriz1: Array<Array<BigInteger>>, matriz2: Array<Array<BigInteger>>) {
        val algoritmos = arrayOf(
            NaivOnArray(), NaivLoopUnrollingTwo(), NaivLoopUnrollingFour(),
            WinogradOriginal(), WinogradScaled(), StrassenNaiv(),
            StrassenWinograd(), SequentialBlockIII(), ParallelBlockIII4(),
            EnhancedParallelBlockIII5(), SequentialBlockIV(), ParallelBlockIV4(),
            EnhancedParallelBlockIV5(), SequentialBlockV3(), ParallelBlockV4()
        )

        val nombres = arrayOf(
            "NaivOnArray", "NaivLoopUnrolling2", "NaivLoopUnrolling4",
            "WinogradOriginal", "WinogradScaled", "StrassenNaiv",
            "StrassenWinograd", "SequentialBlock3", "ParallelBlockIII4",
            "EnhancedParallelBlockIII5", "SequentialBlockIV", "ParallelBlockIV4",
            "EnhancedParallelBlockIV5", "SequentialBlockV3", "ParallelBlockV4"
        )

        for (i in algoritmos.indices) {
            val tiempo = medirTiempo(algoritmos[i], matriz1, matriz2)
            arregloDeTiempos[i] = AlgoritmoTiempo(nombres[i], tiempo)
        }
    }

    private fun medirTiempo(
        algoritmo: AlgoritmoMultiplicacion,
        matriz1: Array<Array<BigInteger>>,
        matriz2: Array<Array<BigInteger>>
    ): Double {
        val startTime = System.nanoTime()
        algoritmo.multiplicar(matriz1, matriz2)
        val endTime = System.nanoTime()
        return (endTime - startTime) / 1000000.0 // Convertir de nanosegundos a milisegundos
    }
}
