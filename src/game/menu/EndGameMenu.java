

package game.menu;


import game.InputHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

import java.awt.event.KeyEvent;

/**
 *
 * @author Conor
 */
public class EndGameMenu extends Menu{
    
    public EndGameMenu(String endGameSplash, int x, int y){
        //super("img//splash_gameover.png", x, y);
        super(endGameSplash, x, y);
    }
    
    @Override
    public void control(InputHandler input){
        if(input.getKey(KeyEvent.VK_ESCAPE)){
            quitMenu();
        }
    }
    
    @Override
    public void draw(Graphics2D graphics){
        //Draw splashscreen
        super.draw(graphics);
        //Add text
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Verdana", Font.BOLD, 20));
        graphics.drawString("Press the escape key to exit", getSplashX(), getSplashY() + getSplashHeight());
    }
    
}