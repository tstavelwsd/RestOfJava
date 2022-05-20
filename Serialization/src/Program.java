import java.awt.Color;

import graphics.Canvas;
import shapes.Circle;
import shapes.Line;
import shapes.Point;
import shapes.Polygon;
import shapes.Shape;

public class Program {
    
    // Canvas to be used for all drawings.
    private static Canvas _canvas = new Canvas(-200, -120, 200, 120, 2);
    
    // Array of Shapes to be drawn on the canvas.
    private static Shape[] _shapes;
    
    /**
     * Creates a bunch of shapes and fills them in the _shapes array.
     */
    private static void createShapes() {
        _shapes = new Shape[] {
                new Point(Color.RED, 40, 10),
                new Line(Color.BLUE, 30, 15, 120, 80),
                new Circle(Color.PINK, -100, 40, 60),
                new Polygon(Color.GREEN,
                    new Point[] {
                            new Point(0, 0),
                            new Point(100, 0),
                            new Point(50, 80)
                    }),
        };
    }
    
    /**
     * Draws all the shapes on the canvas.
     */
    private static void drawShapes() {
        for (Shape s : _shapes) {
            s.draw(_canvas);
        }
    }
    
    /**
     * Translates all shapes by a given amount on the x and y axis.
     */
    public static void translateShapes(int dx, int dy) {
        for(Shape s : _shapes) {
            s.translate(dx, dy);
        }
    }

    /**
     * Main method.
     */
    public static void main(String[] args) {
        _canvas.open();
        
        // create a bunch of shapes
        createShapes();
        
        // draw them all on the canvas
        drawShapes();
        _canvas.pause();
        
        // translate all shapes by 20 on X and -15 on Y.
        _canvas.clear();
        translateShapes(20, -15);
        drawShapes();
        _canvas.pause();
        
        // close the canvas
        _canvas.close();
    }
}
