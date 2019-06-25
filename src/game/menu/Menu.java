



package game.menu;

import game.InputHandler;
import game.sprite.Sprite;
import java.awt.Graphics2D;

/**
 *
 * @author Conor
 */
public abstract class Menu {
    
    private Sprite splashScreen;
    private boolean quit;
    
    public Menu(String filename, int x, int y){
        this.splashScreen = new Sprite(filename, x, y, 400, 400);
        quit = false;
    }
    
    public int getSplashX(){
        return splashScreen.getX();
    }
    public int getSplashY(){
        return splashScreen.getY();
    }
    public int getSplashWidth(){
        return splashScreen.getWidth();
    }
    public int getSplashHeight(){
        return splashScreen.getHeight();
    }
    
    public void quitMenu(){
        quit = true;
    }
    public void resetQuit(){
        quit = false;
    }
    public boolean hasQuit(){
        return quit;
    }
    
    
    public abstract void control(InputHandler input);
    
    public void draw(Graphics2D graphics){
        graphics.drawImage(splashScreen.getImage(), splashScreen.getX(), splashScreen.getY(), splashScreen.getWidth(), splashScreen.getHeight(), null);
    }
    
}
