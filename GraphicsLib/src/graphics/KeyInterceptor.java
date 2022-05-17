package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInterceptor implements KeyListener {

    // Region: KeyHooks
    public interface KeyHook {
        void onKeyEvent(KeyEvent e);
    }
    
    public interface KeyUpHook extends KeyHook { }
    
    public interface KeyDownHook extends KeyHook { }
    
    public interface KeyLeftHook extends KeyHook { }
    
    public interface KeyRightHook extends KeyHook { }
    // EndRegion
    
	// Region: Data fields
    private KeyEvent _keyEvent = null;
    private Object _keySync = new Object();
    // EndRegion: Data fields
    
    // Region: KeyListener overrides
	@Override
	public void keyPressed(KeyEvent keyEvent) {
        synchronized (_keySync) {
            switch (keyEvent.getKeyCode()) {
            case 'C':
            case 'S':
            case ' ':
                // Supported key presses are unblocking any potentially waiting thread
                _keyEvent = keyEvent;
                _keySync.notify();
                break;
            case 'Q':
                System.exit(0);
            }
        }
	}

	@Override
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }
	// EndRegion: KeyListener overrides
	
	// Region: KeyInterceptor methods
    /**
     * Stops the execution or, if [C]ontinue was previously selected it will pause 
     * for the specified number of milliseconds.
     * @param millis - number of milliseconds to pause.
     */
    public void step(long millis) {
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

    /**
     * Pauses the execution awaiting for either [C]ontinue, [S]tep or [Q]uit to be pressed. 
     */
    public void pause() {
        synchronized (_keySync) {
            try {
                _keySync.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    // EndRegion: KeyInterceptor methods
}
