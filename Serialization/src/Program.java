import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

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
    private static AllShapes _shapes = new AllShapes();
    
    public static void saveShapes(String jsonFileName) throws FileNotFoundException {
        Gson serializer = new Gson();
        String jsonAllShapes = serializer.toJson(_shapes);
        PrintStream jsonFile = new PrintStream(new File(jsonFileName));
        jsonFile.println(jsonAllShapes);
    }
    
    public static void loadShapes(String jsonFileName) throws FileNotFoundException {
        Scanner jsonFile = new Scanner(new File(jsonFileName));
        String jsonAllShapes = jsonFile.nextLine();
        
        Type allShapesType = new TypeToken<AllShapes>(){}.getType();
        
        RuntimeTypeAdapterFactory<Shape> shapeTypeFactory = RuntimeTypeAdapterFactory  
                .of(Shape.class, "_type", true)
                .registerSubtype(Point.class, Point.class.getName())
                .registerSubtype(Circle.class, Circle.class.getName())
                .registerSubtype(Line.class, Line.class.getName())
                .registerSubtype(Polygon.class, Polygon.class.getName());
        
        Gson deserializer = new GsonBuilder()
                .registerTypeAdapterFactory(shapeTypeFactory)
                .create();

        _shapes = deserializer.fromJson(jsonAllShapes, allShapesType);
    }
    
    /**
     * Main method.
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {
        _canvas.open();
        
        // create a bunch of shapes
        //_shapes.createShapes();
        loadShapes("allShapes.json");
        
        // draw them all on the canvas
        _shapes.drawShapes(_canvas);
        _canvas.pause();
        
        // translate all shapes by 20 on X and -15 on Y.
        _canvas.clear();
        _shapes.translateShapes(20, -15);
        _shapes.drawShapes(_canvas);
        _canvas.pause();
        saveShapes("allShapes.json");
        
        // close the canvas
        _canvas.close();
    }
}
