package shapes;

import java.awt.Color;

import graphics.Canvas;

/**
 * Class representing a generic Shape
 * Any Shape has a color and can be drawn and translated
 * on a canvas.
 */
public class Shape {
    private int _rColor;
    private int _gColor;
    private int _bColor;
    
    public Shape(Color color) {
        _rColor = color.getRed();
        _gColor = color.getGreen();
        _bColor = color.getBlue();
    }
    
    public void draw(Canvas canvas) {
        canvas.setColor(new Color(_rColor, _gColor, _bColor));
    }
    
    public void translate(int dx, int dy) {
    }
}
