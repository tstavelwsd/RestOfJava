package graphics.test;

import graphics.Canvas;

public class CanvasTest {

    public static void main(String[] args) {
        Canvas c = new Canvas();
        c.setRange(-80, -80, 180, 100, 4);
        
        System.out.print("Opening canvas ... ");
        c.open();
        System.out.println("OPENED.");
        c.pause();
        System.out.print("Plotting diagonal ... ");
        for (int i = 0; i < 100; i++) {
            c.plot(-i,  -i);
            c.step();
        }
        System.out.println("PLOTTED.");
        c.pause();
        System.out.print("Clearing canvas ... ");
        c.step();
        c.clear();
        System.out.println("CLEARED.");
        c.pause();
        System.out.print("Resetting canvas ... ");
        c.step();
        c.reset();
        System.out.println("RESET.");
        c.pause();
        System.out.print("Closing canvas ... ");
        c.step();
        c.close();
        System.out.println("CLOSED.");
        c.pause();
    }
}
