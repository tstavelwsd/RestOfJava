import java.awt.Color;

import graphics.Canvas;
import shapes.Circle;
import shapes.Line;
import shapes.Point;
import shapes.Polygon;
import shapes.Shape;

public class AllShapes {
    private Point _point;
    private Line _line;
    private Circle _circle;
    private Polygon _polygon;

    /**
     * Creates a bunch of shapes and fills them in the _shapes array.
     */
    public void createShapes() {
        _point = new Point(Color.RED, 40, 10);
        _line = new Line(Color.BLUE, 30, 15, 120, 80);
        _circle = new Circle(Color.PINK, -100, 40, 60);
        _polygon = new Polygon(Color.GREEN,
                new Point[] {
                        new Point(0, 0),
                        new Point(100, 0),
                        new Point(50, 80)
                });
    }

    /**
     * Draws all the shapes on the canvas.
     */
    public void drawShapes(Canvas canvas) {
        Shape[] _shapes = {_point, _line, _circle, _polygon };
        for (Shape s : _shapes) {
            s.draw(canvas);
        }
    }
    
    /**
     * Translates all shapes by a given amount on the x and y axis.
     */
    public void translateShapes(int dx, int dy) {
        Shape[] _shapes = {_point, _line, _circle, _polygon };
        for(Shape s : _shapes) {
            s.translate(dx, dy);
        }
    }
}
