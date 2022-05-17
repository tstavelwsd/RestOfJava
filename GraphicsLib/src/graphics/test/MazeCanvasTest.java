package graphics.test;

import java.awt.Color;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class MazeCanvasTest {
    
    public static void genSnake(MazeCanvas mc) {
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                mc.step(10);
                mc.drawCell(r, c, Color.WHITE);
            }
        }
        mc.pause();
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                if (r != 0) {
                    mc.eraseWall(r, c, Side.Top);
                }
                if (r != mc.getRows()-1) {
                    mc.eraseWall(r, c, Side.Bottom);
                }
                if (r == 0) {
                    if (c % 2 == 0) {
                        mc.eraseWall(r, c, Side.Left);
                    } else {
                        mc.eraseWall(r, c, Side.Right);
                    }
                }
                if (r == mc.getRows()-1) {
                    if (c % 2 == 1) {
                        mc.eraseWall(r, c, Side.Left);
                    } else {
                        mc.eraseWall(r, c, Side.Right);
                    }
                }
            }
        }
        mc.pause();
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                mc.step();
                mc.drawPath(r, c, r == 0 ? c % 2 == 0 ? Side.Left : Side.Right : Side.Top, Color.RED);
                mc.drawCenter(r, c, Color.RED);
                mc.drawPath(r, c, r == mc.getRows()-1 ? c % 2 == 0 ? Side.Right : Side.Left : Side.Bottom, Color.RED);
            }
        }
    }
    
    public static void main(String[] args) {
        int row = 12;
        int col = 16;
        int cellWidth = 28;
        Color tan = new Color(253, 217, 181);
        Color pale = new Color(242, 242, 242);
        MazeCanvas mc = new MazeCanvas(row, col, cellWidth);
        mc.open();
        mc.step();
        
        // granular operations
        mc.drawCell(5, 5);
        mc.step();
        mc.drawShade(5, 5, Color.YELLOW);
        mc.step();
        mc.eraseWall(5, 5, Side.Top);
        mc.step();
        mc.drawPath(5, 5, Side.Top,  Color.RED);
        mc.step();
        mc.drawCenter(5, 5, new Color(161,0,0));
        mc.step();
        mc.drawPath(5, 5, Side.Right, Color.RED);
        mc.pause();
        
        mc.drawCell(1,1);
        mc.step();
        mc.drawCell(1, 1, Color.YELLOW);
        mc.step();
        
        // draw a few cells
        mc.drawCell(0, 0);
        mc.drawCell(0, 1, Color.GREEN);
        mc.drawCell(1, 0, Color.CYAN);
        mc.drawCell(2, 0, pale);
        mc.drawCell(1, 1, pale);
        mc.drawCell(0, 2, pale);
        mc.step();
        
        // break some walls between the top left cells
        mc.eraseWall(0, 0, Side.Right);
        mc.eraseWall(0, 1, Side.Left);
        mc.eraseWall(0, 0, Side.Bottom);
        mc.eraseWall(1, 0, Side.Top);
        mc.step();
        
        // enrich top left cells with a center and connecting paths
        mc.drawCenter(0, 0, Color.RED);
        mc.drawPath(0, 0, Side.Right, Color.RED);
        mc.drawPath(0, 1, Side.Left, Color.RED);
        mc.drawPath(0, 0, Side.Bottom, Color.RED);
        mc.drawPath(1, 0, Side.Top, Color.RED);
        mc.step();
        
        // enrich center cell with a center and connecting paths
        mc.drawCenter(1, 1, tan);
        mc.drawPath(1, 1, Side.Left, tan);
        mc.drawPath(1, 0, Side.Right, tan);
        mc.drawPath(1, 1, Side.Top, tan);
        mc.drawPath(0, 1, Side.Bottom, tan);
        mc.step();
        
        // raise wall back up on top left cell
        mc.drawWall(0, 0, Side.Right);
        mc.drawWall(0, 1, Side.Left);
        mc.drawWall(0, 0, Side.Bottom);
        mc.drawWall(1, 0, Side.Top);
        mc.step();
        
        // change the color for the paths from top left cell
        mc.drawPath(0, 0, Side.Right, Color.PINK);
        mc.drawPath(0, 1, Side.Left, Color.PINK);
        mc.drawPath(0, 0, Side.Bottom, Color.PINK);
        mc.drawPath(1, 0, Side.Top, Color.PINK);
        mc.step();
        
        // erase the center of the top left and center cells
        mc.eraseCenter(0, 0);
        mc.eraseCenter(1, 1);
        mc.step();
        
        // erase the paths connecting the top left cell
        mc.erasePath(0, 0, Side.Right);
        mc.erasePath(0, 0, Side.Bottom);
        mc.erasePath(0, 1, Side.Left);
        mc.erasePath(1, 0, Side.Top);
        mc.step();
        
        // erase the walls connecting the center cell
        mc.eraseWall(1, 1, Side.Left);
        mc.eraseWall(1, 0, Side.Right);
        mc.eraseWall(1, 1, Side.Top);
        mc.eraseWall(0, 1, Side.Bottom);
        mc.step();
        
        // draw a center on the center cell
        mc.drawCenter(1, 1, Color.ORANGE);
        mc.step();
        
        // change the shade of the top left and center cell
        mc.drawShade(0,  0, tan);
        mc.drawShade(1, 1, Color.YELLOW);
        mc.pause();
        
        // draw a center on the top left cell
        mc.drawCenter(0, 0, Color.BLUE);
        mc.drawPath(0, 0, Side.Right, Color.BLUE);
        mc.drawPath(0, 1, Side.Left, Color.BLUE);
        mc.drawWall(0, 0, Side.Right);
        mc.drawWall(0, 1, Side.Left);
        mc.step();
        
        // now last change of shade on the top left cell
        mc.drawShade(0, 0, Color.CYAN);
        mc.step();

        // draw and clear caption
        mc.drawCaption("This is a longer line of text, going in the caption area!");
        mc.step();
        mc.eraseCaption();
        mc.pause();
        
        // generate a snake-like circuit
        genSnake(mc);
        mc.pause();
        mc.close();
    }
}
