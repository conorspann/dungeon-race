

package game.sprite;

import java.awt.Graphics2D;

/**
 *
 * @author Conor
 */
public class Door extends Sprite{
    
    private boolean open;
    
    public Door(int x, int y, int width, int height){
        super("img/door_white.png", x, y, width, height);
        open = false;
    }
    
    
    public void open(){
        open = true;
    }
    
    public boolean isOpen(){
        return open;
    }
    
    
    @Override
    public void draw(Graphics2D graphics){
        if(!open){
            graphics.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
        }
    }
    
    
}
