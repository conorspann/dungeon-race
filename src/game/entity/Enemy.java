

package game.entity;

import game.level.Level;
import java.awt.Rectangle;

/**
 *
 * @author Conor
 */
public abstract class Enemy extends Entity{
    
    private State state;
    private Direction direction;
    private int xRange, yRange, xSight, ySight;
    
    public Enemy(String spriteSheet, int noFrames, int frameWidth, int frameHeight, int x, int y, int width, int height, int maxHealth){
        super(spriteSheet, noFrames, frameWidth, frameHeight, x, y, width, height, maxHealth);
        state = State.IDLE;
        xRange = 0;
        yRange = 0;
        xSight = 0;
        ySight = 0;
        direction = Direction.EAST;
    }
    
    public final void setState(State newState){
        state = newState;
    }
    public final void setDirection(Direction direction){
        this.direction = direction;
    }
    public final void setXRange(int xRange){
        this.xRange = xRange;
    }
    public final void setYRange(int yRange){
        this.yRange = yRange;
    }
    public final void setXSight(int xSight){
        this.xSight = xSight;
    }
    public final void setYSight(int ySight){
        this.ySight = ySight;
    }
    
    public final State getState(){
        return state;
    }
    public final Direction getDirection(){
        return direction;
    }
    public final int getXRange(){
        return xRange;
    }
    public final int getYRange(){
        return yRange;
    }
    public final int getXSight(){
        return xSight;
    }
    public final int getYSight(){
        return ySight;
    }
    
    public Rectangle getSightBoundingRectangle(){
        return new Rectangle(getX() - xSight, getY() - ySight, xSight + getWidth() + xSight, ySight + getHeight() + ySight);
    }
    
    public void move(){
        switch(direction){
            case EAST:
                setX(getX() + getRightSpeed());
                break;
            case WEST:
                setX(getX() - getLeftSpeed());
                break;
        }
        setY(getY() + getFallSpeed());
    }
    
    //how and what states the enemy will decide, depending on Enemy child?
    public abstract void control(Level level);
    
    
}
