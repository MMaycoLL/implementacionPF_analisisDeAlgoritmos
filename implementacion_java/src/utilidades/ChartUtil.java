package utilidades;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;

import javax.swing.*;
import java.awt.*;

public class ChartUtil {
    public static JPanel createChartPanel(AlgoritmoTiempo[] arregloDeTiempos) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (AlgoritmoTiempo algoritmoTiempo : arregloDeTiempos) {
            dataset.addValue(algoritmoTiempo.getTiempo(), "Tiempo de Ejecución", algoritmoTiempo.getNombre());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Comparación de Tiempos de Ejecución",
                "Algoritmos",
                "Tiempo de Ejecución (ms)",
                dataset
        );


        // Ajustes estéticos del gráfico
        CategoryPlot plot = chart.getCategoryPlot();
        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.BOLD, 14));
        plot.getDomainAxis().setLabelPaint(Color.BLACK);
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.BOLD, 14));
        plot.getRangeAxis().setLabelPaint(Color.BLACK);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));
        chart.getTitle().setPaint(Color.BLUE);

        // Configuración del renderizador de barras
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new GradientBarPainter()); // Usamos GradientBarPainter para el efecto de gradiente

        // Redondear los bordes de las barras
        renderer.setSeriesStroke(0, new BorderRoundedStroke(2.0f)); // Grosor y redondez del borde

        // Orientación vertical de las etiquetas del eje X
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Verdana", Font.BOLD, 13));
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        renderer.setShadowVisible(true);
        renderer.setShadowPaint(Color.GRAY);
        renderer.setShadowXOffset(2);
        renderer.setShadowYOffset(2);

        plot.setBackgroundPaint(new GradientPaint(0, 0, Color.WHITE, 0, 400, Color.LIGHT_GRAY));
        plot.setOutlinePaint(Color.BLACK);
        plot.setOutlineStroke(new BasicStroke(2));

        // Agregar leyenda
        chart.getLegend().setVisible(true);
        chart.getLegend().setItemFont(new Font("Arial", Font.BOLD, 15));
        chart.getLegend().setItemPaint(Color.BLACK);

        // Redondear los bordes del gráfico
        chart.getCategoryPlot().setOutlineVisible(false); // Ocultar el borde del gráfico
        chart.getCategoryPlot().setDomainGridlinesVisible(true); // Mostrar líneas de cuadrícula en el eje X
        chart.getCategoryPlot().setRangeGridlinesVisible(true); // Mostrar líneas de cuadrícula en el eje Y
        chart.getCategoryPlot().setDomainGridlineStroke(new BasicStroke(0.0f)); // Grosor de la línea de cuadrícula en el eje X
        chart.getCategoryPlot().setRangeGridlineStroke(new BasicStroke(0.0f)); // Grosor de la línea de cuadrícula en el eje Y
        chart.getCategoryPlot().setOutlineStroke(new BasicStroke(2.0f)); // Grosor del borde del gráfico

        return new ChartPanel(chart);
    }
}

