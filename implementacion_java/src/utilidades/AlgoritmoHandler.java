package utilidades;

import algoritmos.*;

import java.math.BigInteger;

public class AlgoritmoHandler {
    private final AlgoritmoTiempo[] arregloDeTiempos;

    public AlgoritmoHandler(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        arregloDeTiempos = new AlgoritmoTiempo[15];
        medirTiempos(matriz1, matriz2);
    }

    private void medirTiempos(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        AlgoritmoMultiplicacion[] algoritmos = {
                new NaivOnArray(), new NaivLoopUnrollingTwo(), new NaivLoopUnrollingFour(),
                new WinogradOriginal(), new WinogradScaled(), new StrassenNaiv(),
                new StrassenWinograd(), new SequentialBlockIII(), new ParallelBlockIII4(),
                new EnhancedParallelBlockIII5(), new SequentialBlockIV(), new ParallelBlockIV4(),
                new EnhancedParallelBlockIV5(), new SequentialBlockV3(), new ParallelBlockV4()
        };

        String[] nombres = {
                "NaivOnArray", "NaivLoopUnrolling2", "NaivLoopUnrolling4",
                "WinogradOriginal", "WinogradScaled", "StrassenNaiv",
                "StrassenWinograd", "SequentialBlock3", "ParallelBlockIII4",
                "EnhancedParallelBlockIII5", "SequentialBlockIV", "ParallelBlockIV4",
                "EnhancedParallelBlockIV5", "SequentialBlockV3", "ParallelBlockV4"
        };

        for (int i = 0; i < algoritmos.length; i++) {
            double tiempo = medirTiempo(algoritmos[i], matriz1, matriz2);
            arregloDeTiempos[i] = new AlgoritmoTiempo(nombres[i], tiempo);
        }
    }

    private double medirTiempo(AlgoritmoMultiplicacion algoritmo, BigInteger[][] matriz1, BigInteger[][] matriz2) {
        long startTime = System.nanoTime();
        algoritmo.multiplicar(matriz1, matriz2);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0; // Convertir de nanosegundos a milisegundos
    }

    public AlgoritmoTiempo[] getArregloDeTiempos() {
        return arregloDeTiempos;
    }
}
