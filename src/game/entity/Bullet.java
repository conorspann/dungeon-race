


package game.entity;

import game.level.Level;
import java.awt.Graphics2D;

/**
 *
 * @author Conor
 */
public class Bullet extends Entity{
    
    private Direction direction;
    private boolean exists;
    private int xOrigin, yOrigin;
    
    public Bullet(int x, int y){
        super("img//bullet.png", 1, 2, 2, x, y, 8, 8, 0);
        this.xOrigin = x;
        this.yOrigin = y;
        setSpeedMod(10);
        setCanHover(true);
        exists = false;
    }
    
    public void fire(Direction direction){
        if(!exists){
            this.direction = direction;
            exists = true;
            if(direction == Direction.WEST){
               setX(xOrigin); 
            }else if(direction == Direction.EAST){
               setX(xOrigin + 50);
            }
            setY(yOrigin);
        }
    }
    
    public void control(){
        if(exists){
            if(direction == Direction.EAST){
                if(getRightSpeed() == 0){
                    exists = false;
                }else{
                    setX(getX() + getRightSpeed());
                }
            }else if(direction == Direction.WEST){
                if(getLeftSpeed() == 0){
                    exists = false;
                }else{
                    setX(getX() - getLeftSpeed());
                }
            }
        }
    }
    
    @Override
    public void collision(Level currentLevel){
        super.collision(currentLevel);
        if(exists){
            for(Skeleton skeleton: currentLevel.getSkeletons()){
                if(skeleton.isAlive()){
                    if(getBoundingRectangle().intersects(skeleton.getBoundingRectangle())){
                        skeleton.deductHealth(1);
                        exists = false;
                    }
                }
            }
        }
    }
    
    @Override
    public void draw(Graphics2D graphics){
        if(exists){
            graphics.drawImage(getImageFrame(0), getX(), getY(), getWidth(), getHeight(), null);
        }
    } 
    
    
    
    public boolean exists(){
        return exists;
    }
    
    
}
