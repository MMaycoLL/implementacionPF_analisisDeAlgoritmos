import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeneradorDatos {

    private static final int LONGITUD_ARREGLO = 4; // Longitud de la matriz

    private static void guardarDatosEnArchivo(int[][] matriz, String nombreArchivo) throws IOException {
        // Asegúrate de que el directorio existe
        File archivo = new File(nombreArchivo);
        archivo.getParentFile().mkdirs();

        // Usa try-with-resources para asegurarte de que BufferedWriter se cierra correctamente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (int[] fila : matriz) {
                StringBuilder filaBuilder = new StringBuilder();
                for (int dato : fila) {
                    filaBuilder.append(dato).append(" ");
                }
                bw.write(filaBuilder.toString().trim());
                bw.newLine();
            }
        }
        System.out.println("Datos generados y guardados en el archivo '" + nombreArchivo + "'.");
    }

    private static int generarNumeroAleatorioDeSeisDigitos() {
        Random rand = new Random();
        // Generar un número de Seis dígitos (100000 a 999999)
        return 100000 + rand.nextInt(900000);
    }

    public static int[][] generarMatrizAleatoria() {
        int[][] matriz = new int[LONGITUD_ARREGLO][LONGITUD_ARREGLO];
        for (int i = 0; i < LONGITUD_ARREGLO; i++) {
            for (int j = 0; j < LONGITUD_ARREGLO; j++) {
                matriz[i][j] = generarNumeroAleatorioDeSeisDigitos();
            }
        }
        return matriz;
    }

    public static void main(String[] args) {
        try {
            int[][] datosAleatorios1 = generarMatrizAleatoria();
            int[][] datosAleatorios2 = generarMatrizAleatoria();
            // Guardar los datos en un archivo de texto
            guardarDatosEnArchivo(datosAleatorios1, "datos/src/matriz_1.txt");
            guardarDatosEnArchivo(datosAleatorios2, "datos/src/matriz_2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}