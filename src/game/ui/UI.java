

package game.ui;

import game.sprite.AnimatedSprite;
import game.entity.Player;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Conor
 */
public class UI{
    
    
    private AnimatedSprite keyUI, healthUI, energyUI, pistolUI;
    private GraphicFont font;
    private int x, y, mins, secs;
    
    public UI(int x, int y){
        this.x = x;
        this.y = y;
        healthUI = new AnimatedSprite("img//sheets//ui_health_sheet.png", 4, 25, 7, x + 10, y, 70, 20);
        
        energyUI = new AnimatedSprite("img//sheets//ui_energy_sheet.png", 4, 25, 7, x + 100, y, 70, 20);
        
        keyUI = new AnimatedSprite("img//sheets//ui_key_sheet.png",       2, 16, 7, x + 200, y, 40, 20);
        
        pistolUI = new AnimatedSprite("img//sheets//ui_pistol_sheet.png", 2, 16, 7, x + 270, y, 40, 20);
        
        font = new GraphicFont();
        mins = 0;
        secs = 0;
    }
    
    
    public void setHasKey(boolean hasKey){
        if(hasKey){
            keyUI.setCurrentFrame(1);
        }else{
            keyUI.setCurrentFrame(0);
        }
    }
    public void setHasPistol(boolean hasPistol){
        if(hasPistol){
            pistolUI.setCurrentFrame(1);
        }else{
            pistolUI.setCurrentFrame(0);
        }
    }
    public void setHealth(Player player){
        healthUI.setCurrentFrame(player.getHealth());
    }
    public void setEnergy(Player player){
        energyUI.setCurrentFrame(player.getEnergy());
    }
    public void setTimeLeft(int timeLeft){
        mins = timeLeft / 60;
        secs = timeLeft % 60;
    }
    
    
    
    
    
    
    public void draw(Graphics2D graphics){
        graphics.setColor(new Color(50, 10, 50));
        graphics.fillRect(x, y-10, 350, 40);
        healthUI.draw(graphics);
        energyUI.draw(graphics);
        keyUI.draw(graphics);
        pistolUI.draw(graphics);
        font.drawString("" + mins + ":" + secs, x+500, y, 35, graphics);
    }
    
    
}
