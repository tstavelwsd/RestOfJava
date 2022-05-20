package shapes;

import java.awt.Color;

import graphics.Canvas;

/**
 * Class representing a generic Shape
 * Any Shape has a color and can be drawn and translated
 * on a canvas.
 */
public class Shape {
    private Color _color;
    
    public Shape(Color color) {
        _color = color;
    }
    
    public void draw(Canvas canvas) {
        canvas.setColor(_color);
    }
    
    public void translate(int dx, int dy) {
    }
}
