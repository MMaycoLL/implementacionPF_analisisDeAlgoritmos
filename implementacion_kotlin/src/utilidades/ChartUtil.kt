package utilidades

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.axis.CategoryLabelPositions
import org.jfree.chart.renderer.category.BarRenderer
import org.jfree.chart.renderer.category.GradientBarPainter
import org.jfree.data.category.DefaultCategoryDataset
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.GradientPaint
import javax.swing.JPanel

object ChartUtil {
    fun createChartPanel(arregloDeTiempos: Array<AlgoritmoTiempo?>): JPanel {
        val dataset = DefaultCategoryDataset()
        for (algoritmoTiempo in arregloDeTiempos) {
            dataset.addValue(algoritmoTiempo?.tiempo, "Tiempo de Ejecución", algoritmoTiempo?.nombre)
        }

        val chart = ChartFactory.createBarChart(
            "Comparación de Tiempos de Ejecución",
            "Algoritmos",
            "Tiempo de Ejecución (ms)",
            dataset
        )


        // Ajustes estéticos del gráfico
        val plot = chart.categoryPlot
        plot.domainAxis.labelFont = Font("Arial", Font.BOLD, 14)
        plot.domainAxis.labelPaint = Color.BLACK
        plot.rangeAxis.labelFont = Font("Arial", Font.BOLD, 14)
        plot.rangeAxis.labelPaint = Color.BLACK
        chart.title.font = Font("Arial", Font.BOLD, 18)
        chart.title.paint = Color.BLUE

        // Configuración del renderizador de barras
        val renderer = plot.renderer as BarRenderer
        renderer.barPainter = GradientBarPainter() // Usamos GradientBarPainter para el efecto de gradiente

        // Redondear los bordes de las barras
        renderer.setSeriesStroke(0, BorderRoundedStroke(2.0f)) // Grosor y redondez del borde

        // Orientación vertical de las etiquetas del eje X
        val domainAxis = plot.domainAxis
        domainAxis.tickLabelFont = Font("Verdana", Font.BOLD, 13)
        plot.domainAxis.categoryLabelPositions = CategoryLabelPositions.UP_45

        renderer.setShadowVisible(true)
        renderer.shadowPaint = Color.GRAY
        renderer.shadowXOffset = 2.0
        renderer.shadowYOffset = 2.0

        plot.backgroundPaint = GradientPaint(0f, 0f, Color.WHITE, 0f, 400f, Color.LIGHT_GRAY)
        plot.outlinePaint = Color.BLACK
        plot.outlineStroke = BasicStroke(2f)

        // Agregar leyenda
        chart.legend.isVisible = true
        chart.legend.itemFont = Font("Arial", Font.BOLD, 15)
        chart.legend.itemPaint = Color.BLACK

        // Redondear los bordes del gráfico
        chart.categoryPlot.isOutlineVisible = false // Ocultar el borde del gráfico
        chart.categoryPlot.isDomainGridlinesVisible = true // Mostrar líneas de cuadrícula en el eje X
        chart.categoryPlot.isRangeGridlinesVisible = true // Mostrar líneas de cuadrícula en el eje Y
        chart.categoryPlot.domainGridlineStroke = BasicStroke(0.0f) // Grosor de la línea de cuadrícula en el eje X
        chart.categoryPlot.rangeGridlineStroke = BasicStroke(0.0f) // Grosor de la línea de cuadrícula en el eje Y
        chart.categoryPlot.outlineStroke = BasicStroke(2.0f) // Grosor del borde del gráfico

        return ChartPanel(chart)
    }
}

