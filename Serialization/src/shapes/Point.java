package shapes;

import java.awt.Color;

import graphics.Canvas;

/**
 * Class representing a generic Point.
 * Any Point is a Shape with an x and y coordinate.
 */
public class Point extends Shape {
    private int _x;
    private int _y;
    
    public Point(Color c, int x, int y) {
        super(c);
        _x = x;
        _y = y;
    }
    
    public Point(int x, int y) {
        this(Color.BLACK, x, y);
    }
    
    public int getX() {
        return _x;
    }
    
    public int getY() {
        return _y;
    }
    
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.plot(_x, _y);
    }
    
    @Override
    public void translate(int dx, int dy) {
        _x += dx;
        _y += dy;
    }
}
