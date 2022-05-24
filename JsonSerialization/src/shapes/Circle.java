package shapes;

import java.awt.Color;

import graphics.Canvas;

/**
 * Class representing a generic Circle.
 * Any Circle is a Shape with a center Point and a given radius.
 */
public class Circle extends Shape {

    private Point _centerPoint;
    private int _radius;
    
    public Circle(Color color, int xCenter, int yCenter, int radius) {
        super(color);
        _centerPoint = new Point(color, xCenter, yCenter);
        _radius = radius;
    }
    
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.circle(_centerPoint.getX(), _centerPoint.getY(), _radius);
    }
    
    @Override
    public void translate(int dx, int dy) {
        _centerPoint.translate(dx, dy);
    }
}
