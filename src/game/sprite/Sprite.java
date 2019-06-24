


package game.sprite;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Conor
 */
public class Sprite {
    
    private int x, y, width, height, damage;
    private BufferedImage image;
    private boolean solid, drawDefaultSize;
    
    
    
    
    public Sprite(String filename){
        x = 0;
        y = 0;
        width = 1;
        height = 1;
        damage = 0;
        solid = false;
        drawDefaultSize = false;
        setImage(filename);
    }
    
    public Sprite(String filename, int x, int y){
        this.x = x;
        this.y = y;
        width = 1;
        height = 1;
        damage = 0;
        solid = false;
        drawDefaultSize = false;
        setImage(filename);
    }
    
    public Sprite(String filename, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        damage = 0;
        solid = false;
        drawDefaultSize = false;
        setImage(filename);
    }
    
    public Sprite(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        damage = 0;
        solid = false;
        drawDefaultSize = false;
    }
    
    private void setImage(String filename){
        image = loadImage(filename);
    }
    
    public BufferedImage loadImage(String filename){
        BufferedImage i = null;
        try{
            i = ImageIO.read(new File(filename));
        }catch(IOException e){
            System.out.println("Exception loading image from: " + filename);
        }
        return i;
    }
    
    public void draw(Graphics2D graphics){
        if(drawDefaultSize){
            graphics.drawImage(image, x, y, null);
        }else{
            graphics.drawImage(image, x, y, width, height, null);
        }
    }
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public final void setDamage(int damage){
        this.damage = damage;
    }
    public void setSolid(boolean solid){
        this.solid = solid;
    }
    public void setDrawDefaultSize(boolean drawDefaultSize){
        this.drawDefaultSize = drawDefaultSize;
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getDamage(){
        return damage;
    }
    
    public boolean isSolid(){
        return solid;
    }
    public boolean drawDefaultSize(){
        return drawDefaultSize;
    }
    
    public Rectangle getBoundingRectangle(){
        return new Rectangle(x, y, width, height);
    }
    public Rectangle getTopRectangle(){
        return new Rectangle(x, y - 1, width, 1);
    }
    public Rectangle getTallTopRectangle(){
        return new Rectangle(x, y - 5, width, 4);
    }
    public Rectangle getFloorRectangle(){
        return new Rectangle(getX(), getY() + (getHeight()-1), getWidth(), 1);
    }
    public Rectangle getRightRectangle(){
        return new Rectangle(x + width, y, 1, height);
    }
    public Rectangle getLeftRectangle(){
        return new Rectangle(x - 1, y, 1, height);
    }
    public Rectangle getInnerColumnRectangle(){
        return new Rectangle(x + (width)/2, y, 1, height);
    }
    
    public final BufferedImage getImage(){
        return image;
    }
}
