package solution;

import java.awt.Color;
import java.util.Scanner;

import graphics.Canvas;

public class Program {
    // Canvas GUI instance providing the drawing area on which to plot the functions
    private static Canvas canvas;
    
    // If the interface Function was a functional interface -
    // meaning it contains only one method, calc(),
    // then fSine can be declared in a simplified form, like below:
    //
    // private static Function fSine = (x) -> { return 200 * Math.sin(Math.PI * x / 360); };
    //
    // When the interface contains two or more methods, then an instance
    // can be declared as an anonymous class, like below:
    private static Function fSine = new Function() { 
        public double calc(double x) { return 200 * Math.sin(Math.PI * x / 360); }
        public Color col() { return Color.red; }
    };
    
    private static Function fQuadratic = new Function() {
        public double calc(double x) { return (x - 200) * (x + 200) / 250; }
        public Color col() { return Color.blue; }
    };
    
    private static Function fLog = new Function() {
        public double calc(double x) { return 20 * Math.log(x); }
        public Color col() { return Color.darkGray; }
    };
    
    private static Function fCubic = new Function() {
        public double calc(double x) { return Math.pow(x, 3) / 200000; }
        public Color col() { return Color.green; }
    };
    
    private static Function fStep = new Function() {
        public double calc(double x) { return (x <= -100) ? -100 : (x < 100 ? x : 100); }
        public Color col() { return Color.magenta; }
    };
    
    private static void plotFunctions(Function... funcs) {
        for(Function f : funcs) {
            for (int x = -360; x <= 360; x++) {
                canvas.setColor(f.col());
                canvas.plot(x, (int)f.calc(x));
            }
        }
    }

    public static void main(String[] args) {
        
        // Create the canvas instance, set its range to x:[-360, 360] and y:[-240, 240] then 
        // open it on the screen. Use canvas.plot(pX, pY) to plot a point at the (pX, pY) coords
        canvas = new Canvas();
        canvas.setRange(-360, -240, 360, 240);
        canvas.open();

        Scanner input = new Scanner(System.in);
        System.out.print("function?> ");
        String cmd = input.nextLine();
        while(!cmd.isEmpty()) {
            switch(cmd.toUpperCase()) {
            case "S":
                plotFunctions(fSine);
                break;
            case "Q":
                plotFunctions(fQuadratic);
                break;
            case "L":
                plotFunctions(fLog);
                break;
            case "C":
                plotFunctions(fCubic);
                break;
            case "T":
                plotFunctions(fStep);
                break;
            case "A":
                // Plot all functions
                plotFunctions(fSine, fQuadratic, fLog, fCubic, fStep);
                break;
            default:
                canvas.clear();
            }
            System.out.print("function?> ");
            cmd = input.nextLine();
        }
        
        // Pause and close the canvas then terminate the program.
        //canvas.pause();
        canvas.close();
    }
}
