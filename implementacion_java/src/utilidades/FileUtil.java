package utilidades;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static BigInteger[][] readNumbersFromFile(String fileName) {
        List<BigInteger[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbersAsString = line.trim().split("\\s+");
                BigInteger[] row = new BigInteger[numbersAsString.length];
                for (int i = 0; i < numbersAsString.length; i++) {
                    try {
                        row[i] = new BigInteger(numbersAsString[i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing number: " + numbersAsString[i] + ". Skipping...");
                    }
                }
                rows.add(row);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return rows.toArray(new BigInteger[0][0]);
    }

    public static void guardarTiemposEnArchivo(AlgoritmoTiempo[] tiempos, String nombreArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            for (AlgoritmoTiempo tiempo : tiempos) {
                bw.write(" " + tiempo.getNombre() + ": " + tiempo.getTiempo() + " ms");
                bw.newLine();
            }
            bw.newLine();
            bw.newLine();
            System.out.println("Tiempos de ejecución guardados en el archivo '" + nombreArchivo + "'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
