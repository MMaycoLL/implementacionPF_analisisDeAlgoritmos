package utilidades

import java.awt.BasicStroke
import java.awt.Shape
import java.awt.geom.RoundRectangle2D

class BorderRoundedStroke(width: Float) : BasicStroke(width) {
    override fun createStrokedShape(shape: Shape): Shape {
        val s = super.createStrokedShape(shape)
        return RoundRectangle2D.Float(
            s.bounds.x.toFloat(), s.bounds.y.toFloat(),
            s.bounds.width.toFloat(), s.bounds.height.toFloat(), 10f, 10f
        )
    }
}
