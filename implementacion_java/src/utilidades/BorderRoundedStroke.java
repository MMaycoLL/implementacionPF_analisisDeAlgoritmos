package utilidades;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class BorderRoundedStroke extends BasicStroke {
    public BorderRoundedStroke(float width) {
        super(width);
    }

    @Override
    public Shape createStrokedShape(Shape shape) {
        Shape s = super.createStrokedShape(shape);
        return new RoundRectangle2D.Float(
                s.getBounds().x, s.getBounds().y,
                s.getBounds().width, s.getBounds().height, 10f, 10f
        );
    }
}
