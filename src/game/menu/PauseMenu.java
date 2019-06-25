


package game.menu;

import game.InputHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Conor
 */
public class PauseMenu extends Menu{
    
    public PauseMenu(int x, int y){
        super("img//splash_paused.png", x, y);
    }
    
    
    @Override
    public void control(InputHandler input){
        if(input.getKey(KeyEvent.VK_C)){
            quitMenu();
        }
    }
    
    @Override
    public void draw(Graphics2D graphics){
        super.draw(graphics);
        graphics.setColor(Color.yellow);
        graphics.setFont(new Font("Verdana", Font.BOLD, 20));
        graphics.drawString("Press C to continue!", getSplashX(), getSplashY() + getSplashHeight());
    }
    
}
