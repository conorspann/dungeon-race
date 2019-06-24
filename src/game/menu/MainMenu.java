


package game.menu;

import game.InputHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Conor
 */
public class MainMenu {
    
    
    public MainMenu(){
        
    }
    
    
    public int control(InputHandler input){
        int levelID = -1;
        if(input.getKey(KeyEvent.VK_0)){
            levelID = -2;
        }else if(input.getKey(KeyEvent.VK_1)){
            levelID = 0;
        }else if(input.getKey(KeyEvent.VK_2)){
            levelID = 1;
        }
        return levelID;
    }
    
    public void draw(Graphics2D graphics){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 800, 600);
        graphics.setColor(Color.GREEN);
        graphics.drawString("Main Menu", 400, 100);
        graphics.drawString("<1>: Level One", 300, 200);
        graphics.drawString("<0>: Quit", 300, 300);
    }
    
    
    
}
