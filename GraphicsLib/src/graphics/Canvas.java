package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;

import javax.imageio.ImageIO;

import graphics.KeyInterceptor.KeyHook;

/**
 * A generic drawing canvas supporting simple GUI operations in a 2D coordinates space.
 * This is built as a functional extension of UW's DrawingPanel.
 */
public class Canvas {
    // Region: Class default Constants
    private final int DEFAULT_SCALE = 1;
    private final boolean DEFAULT_PAINT_AXES = true;
    private final Color DEFAULT_AXES_COLOR = Color.LIGHT_GRAY;
    private final Color DEFAULT_PEN_COLOR = Color.BLACK;
    private final Color DEFAULT_LBL_COLOR = Color.BLUE;
    private final int DEFAULT_XMIN = -512;
    private final int DEFAULT_YMIN = -340;
    private final int DEFAULT_XMAX = 512;
    private final int DEFAULT_YMAX = 340;
    private final int DEFAULT_PINSIZE = 8;
    // EndRegion: Class default Constants

    // Region: Private & protected class field members
    protected int _scale;
    protected boolean _paintAxes;
    protected Color _color, _colorAxes;
    protected int _xMin, _yMin, _xMax, _yMax;
    protected static int _lastX = 0;
    protected static int _lastY = 0;
    
    private static BasicStroke _strokePin = new BasicStroke(1);
    private static BasicStroke _strokeLine = new BasicStroke(4);

    private KeyEvent _keyEvent = null;
    private Object _keySync = new Object();
    
    protected DrawingPanel _panel = null;

    protected int _lblBarHeight = 24;
    protected int _lblXOffset = 20;
    protected int _lblWordGap = 20;
    protected Color _lblColor = DEFAULT_LBL_COLOR;
    protected Font _lblFont = new Font("Tahoma", Font.BOLD, 14);
    protected String[] _labels = { "[S]tep", "[C]ontinue", "[space]FastFwd", "  [Q]uit" };
    protected KeyHook[] _keyHooks = {};
    // EndRegion: Class private & protected field members
    
    // Region: Protected Coordinate translation methods
    /*
     * Translates X coordinate from canvas space to panel space.
     */
    protected int transX(int x) {
        return (x - _xMin) * _scale;
    }

    /*
     * Translates Y coordinate from canvas space to panel space.
     */
    protected int transY(int y) {
        return _lblBarHeight + (_yMax - y) * _scale;
    }
    // EndRegion: Coordinate translation methods

    // Region: Internal key intercepter helper
    class CanvasKeyIntercept implements KeyListener {
        Canvas _parent;

        CanvasKeyIntercept(Canvas parent) {
            _parent = parent;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            _parent.keyIntercept(e);
        }

        @Override
        public void keyReleased(KeyEvent e) { }

        @Override
        public void keyTyped(KeyEvent e) { }
    }

    boolean anyKeyHook(KeyHook keyHook) {
        return keyHook instanceof KeyHook
            && !(keyHook instanceof KeyInterceptor.KeyUpHook)
            && !(keyHook instanceof KeyInterceptor.KeyDownHook)
            && !(keyHook instanceof KeyInterceptor.KeyLeftHook)
            && !(keyHook instanceof KeyInterceptor.KeyRightHook);
    }
    
    void keyIntercept(KeyEvent keyEvent) {
        for(KeyHook kh : _keyHooks) {
            if (anyKeyHook(kh)) {
                kh.onKeyEvent(keyEvent);
            }
        }
        
        synchronized (_keySync) {
            switch (keyEvent.getKeyCode()) {
            case 'C':
            case ' ':
                // Continue
                _keyEvent = keyEvent;
                _keySync.notify();
                break;
            case 'S':
                // Step
                _keyEvent = keyEvent;
                _keySync.notify();
                break;
            case 'Q':
                System.exit(0);
            case KeyEvent.VK_UP:
                for(KeyHook kh : _keyHooks) {
                    if (kh instanceof KeyInterceptor.KeyUpHook) {
                        kh.onKeyEvent(keyEvent);
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                for(KeyHook kh : _keyHooks) {
                    if (kh instanceof KeyInterceptor.KeyDownHook) {
                        kh.onKeyEvent(keyEvent);
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                for(KeyHook kh : _keyHooks) {
                    if (kh instanceof KeyInterceptor.KeyLeftHook) {
                        kh.onKeyEvent(keyEvent);
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                for(KeyHook kh : _keyHooks) {
                    if (kh instanceof KeyInterceptor.KeyRightHook) {
                        kh.onKeyEvent(keyEvent);
                    }
                }
                break;
            }
        }
    }
    // EndRegion: Internal key intercepter helper
    
    // Region: Paint methods
    private void paint() {
        Graphics g = _panel.getGraphics();
        paintAxes(g);
        paintLabels(g);
        g.setColor(_color);
    }

    private void paintLabels(Graphics g) {
        g.setColor(_lblColor);
        g.setFont(_lblFont);
        int xOffset = this._lblXOffset;
        for (String label : _labels) {
            g.drawString(label, xOffset, this._lblBarHeight);
            xOffset += g.getFontMetrics().stringWidth(label) + this._lblWordGap;
        }
    }

    private void paintAxes(Graphics g) {
        if (_paintAxes) {
            int xo = transX(0);
            int yo = transY(0);
            g.setColor(_colorAxes);
            g.fillRect(0, yo, getWidth() * _scale, _scale);
            g.fillRect(xo, _lblBarHeight, _scale, getHeight() * _scale);
        }
    }
    // EndRegion: Paint methods

    public Canvas() {
        reset();
    }
    
    public Canvas(int xMin, int yMin, int xMax, int yMax, int scale) {
        reset();
        setRange(xMin, yMin, xMax, yMax, scale);
    }

    // Region: Internal getters
    Graphics getGraphics() {
        return _panel.getGraphics();
    }
    // EndRegion: Internal getters
    
    // Region: Public panel geometry methods
    public void setRange(int xMin, int yMin, int xMax, int yMax) {
        setRange(xMin, yMin, xMax, yMax, 1);
    }

    public void setRange(int xMin, int yMin, int xMax, int yMax, int scale) {
        if (xMin >= xMax || yMin >= yMax) {
            throw new InvalidParameterException("Range values should have min < max");
        }
        if (_xMin != xMin || _yMin != yMin || _xMax != xMax || _yMax != yMax || _scale != scale) {
	        _xMin = xMin;
	        _yMin = yMin;
	        _xMax = xMax;
	        _yMax = yMax;
	        _scale = scale;
	        if (isOpened()) {
	            close();
	            open();
	        }
        }
    }

    public int getWidth() {
        return (_xMax - _xMin + 1);
    }

    public int getHeight() {
        return (_yMax - _yMin + 1);
    }

    public int getXMin() {
        return _xMin;
    }

    public int getXMax() {
        return _xMax;
    }

    public int getYMin() {
        return _yMin;
    }

    public int getYMax() {
        return _yMax;
    }
    // EndRegion: Panel geometry methods
    
    // Region: Public state and action methods
    public void reset() {
        _scale = DEFAULT_SCALE;
        _paintAxes = DEFAULT_PAINT_AXES;
        _color = DEFAULT_PEN_COLOR;
        _colorAxes = DEFAULT_AXES_COLOR;
        _xMin = DEFAULT_XMIN;
        _xMax = DEFAULT_XMAX;
        _yMin = DEFAULT_YMIN;
        _yMax = DEFAULT_YMAX;
        if (isOpened()) {
            close();
            open();
        }
    }
 
    public boolean isOpened() {
        return (_panel != null && !_panel.isClosed());
    }

    /***
     * Opens the canvas window.
     * @return true if successful, false in case of error: the window is already opened.
     * @see Canvas#close()
     */
    public boolean open() {
        boolean success = !isOpened();
        if (success) {
            _panel = new DrawingPanel(
                    (_xMax - _xMin + 1) * _scale, 
                    _lblBarHeight + (_yMax - _yMin + 1) * _scale);
            _panel.addKeyListener(new CanvasKeyIntercept(this));
            paint();
        }
        return success;
    }

    /***
     * Clears the canvas window restoring it to its default layout.
     * @return true if successful, false in case of error: the window is not opened.
     * @see Canvas#open()
     */
    public boolean clear() {
        boolean success = isOpened();
        if (success) {
            _panel.clear();
            paint();
        }
        return success;
    }

    /***
     * Closes the canvas window.
     * @return true if successful, false in case of error: the window is already closed.
     * @see Canvas#open()
     */
    public boolean close() {
        boolean success = isOpened();
        if (success) {
            _panel.close();
            _panel = null;
        }
        return success;
    }
    // EndRegion: Panel state and action methods

    // Region: Public Keyboard interaction methods
    public void setKeyHooks(KeyHook... keyHooks) {
        _keyHooks = keyHooks;
    }
    
    /**
     * Stops the execution awaiting for either [C]ontinue, [S]tep or [Q]uit to be pressed.
     * Stopping won't happen however, if [C]ontinue was previously pressed.
     * @return true if successful, false otherwise.
     */
    public boolean step() {
        return step(0);
    }
    
    /**
     * Stops the execution or, if [C]ontinue was previously selected it will pause 
     * for the specified number of milliseconds.
     * @param millis - number of milliseconds to pause.
     * @return true if successful, false otherwise.
     */
    public boolean step(long millis) {
        boolean success = isOpened();
        if (success) {
            synchronized (_keySync) {
                try {
                    char keyChar = (_keyEvent == null) ? 'S' : (char) _keyEvent.getKeyCode();
                    if (keyChar == 'C' && millis > 0) {
                        Thread.sleep(millis);
                    }
                    if (keyChar != 'C' && keyChar != ' ') {
                        _keySync.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return success;
    }

    /**
     * Pauses the execution awaiting for either [C]ontinue, [S]tep or [Q]uit to be pressed. 
     * @return true if successful, false otherwise.
     */
    public boolean pause() {
        boolean success = isOpened();
        if (success) {
            synchronized (_keySync) {
                try {
                    _keySync.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return success;
    }
    // EndRegion: Keyboard interaction methods

    public boolean drawImageStream(InputStream input) {
    	boolean success = false;
		try {
			BufferedImage img = ImageIO.read(input);
			success = true;
		    setRange(0, 0, img.getWidth(), img.getHeight());
		    if (!isOpened()) {
		    	success = open();
		    }
			
			if (success) {
				getGraphics().drawImage(img, 0, 0, null);
			}
		} catch (IOException e) {
			System.out.println("Input stream is not an image!");
		}
		return success;
    }
    
//    public boolean plot(int x, int y) {
//        boolean success = isOpened();
//        if (success) {
//            Graphics g = _panel.getGraphics();
//            int xp = transX(x);
//            int yp = transY(y);
//            g.fillRect(xp, yp, _scale, _scale);
//        }
//        return success;
//    }
    
    /**
     * Draws a point/bubble on canvas, at the given coordinates
     * @param x - x coordinate
     * @param y - y coordinate
     * @return true if successful, false otherwise
     */
    public boolean plot(int x, int y) {
        boolean success = isOpened();
        if (success) {
            Graphics2D g = (Graphics2D)getGraphics();
            g.setStroke(_strokePin);
            g.fillOval(
                    transX(x) - DEFAULT_PINSIZE/2,
                    transY(y) - DEFAULT_PINSIZE/2,
                    DEFAULT_PINSIZE, DEFAULT_PINSIZE);
            _lastX = x;
            _lastY = y;
        }
        return success;
    }
    
    /**
     * Draws a line from the last position drawn on the map to the given coordinate.
     * i.e.: if last draw operation was line(10, 10, 20, 20), the line drawn by
     * line(42, 64) has the starting point at (20, 20) and the ending point at (42, 64)
     * @param x - x coordinate of the ending point of the line
     * @param y - y coordinate of the ending point of the line
     * @return true if successful, false otherwise
     */
    public boolean lineTo(int x, int y) {
        boolean success = isOpened();
        if (success) {
            Graphics2D g = (Graphics2D)getGraphics();
            g.setStroke(_strokeLine);
            g.drawLine(transX(_lastX), transY(_lastY), transX(x), transY(y));
            _lastX = x;
            _lastY = y;
            plot(_lastX, _lastY);
        }
        return success;
    }
    
    /**
     * Draws a line in between the (xFrom, yFrom) and (xTo, yTo) coordinates.
     * @param xFrom - x coordinate of the starting point
     * @param yFrom - y coordinate of the starting point
     * @param xTo - x coordinate of the ending point
     * @param yTo - y coordinate of the ending point
     * @return true if successful, false otherwise
     */
    public boolean line(int xFrom, int yFrom, int xTo, int yTo) {
        boolean success = isOpened();
        if (success) {
            plot(xFrom, yFrom);
            lineTo(xTo, yTo);
        }
        return success;
    }
    
    /**
     * Draws a circle with the given center and of the given diameter. If the circle is not
     * fully contained on the map, the method will not fail, but only the portions that 
     * intersect the map area will be shown.
     * @param xCenter - x coordinate of the center of the circle.
     * @param yCenter - y coordinate of the center of the circle.
     * @param radius - the radius of the circle
     * @return true if successful, false otherwise
     */
    public boolean circle(int xCenter, int yCenter, int radius) {
        boolean success = isOpened();
        if (success) {
            Graphics2D g = (Graphics2D)getGraphics();
            g.setStroke(_strokeLine);
            g.drawOval(
                    transX(xCenter - radius), 
                    transY(yCenter + radius), 
                    2 * radius * _scale, 
                    2 * radius * _scale);
            _lastX = xCenter;
            _lastY = yCenter;

        }
        return success;
    }
    
    /**
     * Draws an oval with the given center and of the given width and height. 
     * If the oval is not fully contained on the map, the method will not fail,
     * but only the portions that intersect the map area will be shown. 
     * @param xCenter - x coordinate of the center of the circle.
     * @param yCenter - y coordinate of the center of the circle.
     * @param radius - the radius of the circle
     * @return true if successful, false otherwise
     */
    public boolean oval(int xCenter, int yCenter, int width, int height) {
        boolean success = isOpened();
        if (success) {
            Graphics2D g = (Graphics2D)getGraphics();
            g.setStroke(_strokeLine);
            g.drawOval(
                    transX(xCenter - width / 2), 
                    transY(yCenter + height / 2), 
                    width * _scale, 
                    height * _scale);
            _lastX = xCenter;
            _lastY = yCenter;

        }
        return success;
    }
    
    public Color getColor(int x, int y) {
        Color c = Color.BLACK;
        if (isOpened()) {
        	c = _panel.getColor(x, y);
        }
        return c;
    }
    
    public boolean setColor(Color c) {
        boolean success = isOpened();
        if (success) {
            Graphics g = _panel.getGraphics();
            g.setColor(c);
        }
        return success;
    }
}
