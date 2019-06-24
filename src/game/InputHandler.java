

package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Conor
 */
public class InputHandler implements KeyListener{

    
    private boolean[] keys;
    private final int NUM_KEYS = 255;
    
    public InputHandler(){
        keys = new boolean[NUM_KEYS];
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    public boolean[] getKeys(){
        return keys;
    }
 
    public boolean getKey(int keyCode){
        return keys[keyCode];
    }
    
    public boolean anyKeyPressed(){
        for(int i = 0; i < NUM_KEYS; i++){
            if(keys[i]){
                //At least one key is pressed
                return true;
            }
        }
        //No key is pressed
        return false;
    }
    
}
