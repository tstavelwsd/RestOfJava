package shapes;

import java.awt.Color;

import graphics.Canvas;

/**
 * Class representing a generic Line.
 * Any Line is a Shape containing two Points.
 */
public class Line extends Shape {

    private Point _pointFrom;
    private Point _pointTo;
    
    public Line(Color color, int xFrom, int yFrom, int xTo, int yTo) {
        super(color);
        _pointFrom = new Point(color, xFrom, yFrom);
        _pointTo = new Point(color, xTo, yTo);
    }
    
    public Line(int xFrom, int yFrom, int xTo, int yTo) {
        this(Color.BLACK, xFrom, yFrom, xTo, yTo);
    }
    
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        _pointFrom.draw(canvas);
        canvas.lineTo(_pointTo.getX(), _pointTo.getY());
        _pointTo.draw(canvas);
    }
    
    @Override
    public void translate(int dx, int dy) {
        _pointFrom.translate(dx, dy);
        _pointTo.translate(dx, dy);
    }
}
