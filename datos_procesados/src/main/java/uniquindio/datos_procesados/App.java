package uniquindio.datos_procesados;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App extends Application {

    private static final String FILE_PATH1 = "datos/src/tiempos_java.txt";
    private static final String FILE_PATH2 = "datos/src/tiempos_kotlin.txt";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Comparación de Tiempos de Ejecución");
        primaryStage.setFullScreen(true); // Iniciar en pantalla completa

        // Leer datos de los archivos
        List<AlgoritmoTiempo> tiempos1 = readDataFromFile(FILE_PATH1);
        List<AlgoritmoTiempo> tiempos2 = readDataFromFile(FILE_PATH2);

        // Ordenar datos por tiempos para una mejor visualización
        tiempos1.sort(Comparator.comparingDouble(AlgoritmoTiempo::tiempo));
        tiempos2.sort(Comparator.comparingDouble(AlgoritmoTiempo::tiempo));

        // Encontrar el tiempo más alto
        double maxTiempoJava = tiempos1.stream().mapToDouble(AlgoritmoTiempo::tiempo).max().orElse(1);
        double maxTiempoKotlin = tiempos2.stream().mapToDouble(AlgoritmoTiempo::tiempo).max().orElse(1);
        double maxTiempo = Math.max(maxTiempoJava, maxTiempoKotlin);

        // Crear los ejes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickLabelFont(Font.font("Arial", 14));
        xAxis.setTickLabelRotation(45);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Tiempo de Ejecución (ms)");
        yAxis.setTickLabelFont(Font.font("Arial", 14));

        // Crear el gráfico de barras
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Comparación de Tiempos de Ejecución");
        barChart.setLegendVisible(true);
        barChart.setStyle("-fx-background-color: #f4f4f4;");

        // Ajustar el ancho de las barras
        barChart.setBarGap(2); // Espacio entre las barras
        barChart.setCategoryGap(6); // Espacio entre las categorías

        // Factor de escala para ajustar los valores
        double scaleFactor = 4;  // Ajusta este valor según sea necesario

        // Crear las series de datos
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("JAVA");
        for (AlgoritmoTiempo tiempo : tiempos1) {
            double displayedTiempo = tiempo.tiempo() == maxTiempo ? maxTiempo : tiempo.tiempo() * scaleFactor;
            XYChart.Data<String, Number> data = new XYChart.Data<>(tiempo.nombre(), displayedTiempo);
            series1.getData().add(data);
            data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    addLabelToBar(data, tiempo.tiempo(), maxTiempo);
                }
            });
        }

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("KOTLIN");
        for (AlgoritmoTiempo tiempo : tiempos2) {
            double displayedTiempo = tiempo.tiempo() == maxTiempo ? maxTiempo : tiempo.tiempo() * scaleFactor;
            XYChart.Data<String, Number> data = new XYChart.Data<>(tiempo.nombre(), displayedTiempo);
            series2.getData().add(data);
            data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    addLabelToBar(data, tiempo.tiempo(), maxTiempo);
                }
            });
        }

        // Añadir las series al gráfico
        barChart.getData().addAll(series1, series2);

        // Crear el layout y añadir el gráfico
        BorderPane root = new BorderPane();
        root.setCenter(barChart);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setStyle("-fx-background-color: #ffffff;");

        // Crear la escena y mostrar el escenario
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addLabelToBar(XYChart.Data<String, Number> data, double originalTiempo, double maxTiempo) {
        StackPane barNode = (StackPane) data.getNode();
        Label label = new Label(String.format("%.1f", originalTiempo));
        label.setFont(Font.font("Arial", 9));
        label.setTextFill(Color.BLACK);
        label.setRotate(45); // Rotar la etiqueta 45 grados para que esté diagonal

        // Alinear y ajustar la posición de la etiqueta
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setMargin(label, new Insets(-20, -20, -40, -20));

        barNode.getChildren().add(label);

        // Resaltar la barra con el valor más alto
        if (originalTiempo == maxTiempo) {
            barNode.setStyle("-fx-bar-fill: #ff0000;"); // Cambiar el color de la barra más alta a rojo
        }
    }

    private List<AlgoritmoTiempo> readDataFromFile(String filePath) {
        List<AlgoritmoTiempo> tiempos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length != 2) {
                    System.err.println("Línea mal formateada: " + line);
                    continue;
                }
                try {
                    String nombre = parts[0].trim();
                    double tiempo = Double.parseDouble(parts[1].replace(" ms", "").trim());
                    tiempos.add(new AlgoritmoTiempo(nombre, tiempo));
                } catch (NumberFormatException e) {
                    System.err.println("Número mal formateado en la línea: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tiempos;
    }

    private record AlgoritmoTiempo(String nombre, double tiempo) {
    }

    public static void main(String[] args) {
        launch(args);
    }
}



