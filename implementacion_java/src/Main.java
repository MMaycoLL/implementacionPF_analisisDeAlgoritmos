import utilidades.AlgoritmoHandler;
import utilidades.AlgoritmoTiempo;
import utilidades.ChartUtil;
import utilidades.FileUtil;

import javax.swing.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

public class Main extends JFrame {
    private static final String FILE_PATH1 = "datos/src/datos_generados1.txt";
    private static final String FILE_PATH2 = "datos/src/datos_generados2.txt";

    public Main(BigInteger[][] matriz1, BigInteger[][] matriz2) {
        super("Tiempos de Ejecución");

        AlgoritmoHandler handler = new AlgoritmoHandler(matriz1, matriz2);
        AlgoritmoTiempo[] arregloDeTiempos = handler.getArregloDeTiempos();

        Arrays.sort(arregloDeTiempos, Comparator.comparing(AlgoritmoTiempo::getTiempo));
        FileUtil.guardarTiemposEnArchivo(arregloDeTiempos, "datos/src/tiempos_java.txt");

        for (AlgoritmoTiempo tiempo : arregloDeTiempos) {
            System.out.println("Tiempo de ejecución de " + tiempo.getNombre() + ": " + tiempo.getTiempo() + " ms");
        }

        JPanel chartPanel = ChartUtil.createChartPanel(arregloDeTiempos);
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {
        BigInteger[][] matrizA = FileUtil.readNumbersFromFile(FILE_PATH1);
        BigInteger[][] matrizB = FileUtil.readNumbersFromFile(FILE_PATH2);

        Main main = new Main(matrizA, matrizB);
        main.pack();
        main.setVisible(true);
    }
}

