

package game.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Conor
 */
public class AnimatedSprite extends Sprite{
    
    private ArrayList<BufferedImage>images;
    private int currentFrame, tick;
    
    
    /*
     *  If this constructor is used, each image frame must be set
     *  individually with calls to setImageFrame(filename)
     */
    public AnimatedSprite(int noFrames, int x, int y, int width, int height){
        super(x, y, width, height);
        images = new ArrayList<>();
        currentFrame = 0;
        tick = 0;
    }
    
    public AnimatedSprite(String spriteSheet, int noFrames, int frameWidth, int frameHeight, int x, int y, int width, int height){
        super(spriteSheet, x, y, width, height);
        images = new ArrayList<>();
        for(int i = 0; i < noFrames*frameWidth; i += frameWidth){
            images.add(getImage().getSubimage(i, 0, frameWidth, frameHeight));
        }
        currentFrame = 0;
        tick = 0;
    }
    
    public void setImageFrame(String filename){
        images.add(loadImage(filename));
    }
    
    public void setCurrentFrame(int currentFrame){
        this.currentFrame = currentFrame;
    }
    
    public boolean tick(){
        boolean proceed = false;
        if(tick < 10){
            tick++;
        }else{
            tick = 0;
            proceed = true;
        }
        return proceed;
    }
    
    public int getNoFrames(){
        return images.size();
    }
    
    public int getCurrentFrame(){
        return currentFrame;
    }
    
    public BufferedImage getImageFrame(int frameIndex){
        if(frameIndex < images.size() && frameIndex > -1){
            return images.get(frameIndex);
        }
        return null;
    }
    
    public ArrayList<BufferedImage> getImages(){
        return images;
    }
    
    
    
    
    
    @Override
    public void draw(Graphics2D graphics){
        if(drawDefaultSize()){
            graphics.drawImage(images.get(currentFrame), getX(), getY(), null);
        }else{
            graphics.drawImage(images.get(currentFrame), getX(), getY(), getWidth(), getHeight(), null);
        }
    }
    
}
