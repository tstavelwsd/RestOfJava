package shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import graphics.Canvas;

/**
 * Class representing a generic Polygon.
 * Any Polygon is a Shape containing a list of Points.
 */
public class Polygon extends Shape {
    
    private List<Point> _points;

    public Polygon(Color color, Point... points) {
        super(color);
        _points = new ArrayList<Point>();
        for(Point p : points) {
            _points.add(new Point(color, p.getX(), p.getY()));
        }
    }
    
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for(int i = 0; i < _points.size(); i++) {
            int nextI = (i + 1) % _points.size();
            canvas.line(
                    _points.get(i).getX(), _points.get(i).getY(), 
                    _points.get(nextI).getX(), _points.get(nextI).getY());
        }
    }
    
    @Override
    public void translate(int dx, int dy) {
        for(Point p : _points) {
            p.translate(dx, dy);
        }
    }
}
