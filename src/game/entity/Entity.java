


package game.entity;

import game.sprite.AnimatedSprite;
import game.sprite.Brick;
import game.sprite.Door;
import game.level.Level;
import game.sprite.Spike;


/**
 *
 * @author Conor
 */
public class Entity extends AnimatedSprite{
    
    
    private int health, ivTick, fallSpeed, rightSpeed, leftSpeed, upSpeed, speedMod;
    private final int MAX_HEALTH;
    private boolean isInvulnerable, canHover;
    
    public Entity(String spriteSheet, int noFrames, int frameWidth, int frameHeight, int x, int y, int width, int height, int maxHealth){
        super(spriteSheet, noFrames, frameWidth, frameHeight, x, y, width, height);
        this.MAX_HEALTH = maxHealth;
        health = maxHealth;
        ivTick = 0;
        fallSpeed = 0;
        rightSpeed = 1;
        leftSpeed = 1;
        upSpeed = 5;
        speedMod = 0;
        isInvulnerable = false;
        canHover = false;
    }
    
    public void ivTick(){
        if(isInvulnerable){
            if(ivTick < 100){
                ivTick++;
            }else{
                isInvulnerable = false;
                ivTick = 0;
            }
        }
    }
    
    public void collision(Level currentLevel){
        fallSpeed = 4 + speedMod;
        rightSpeed = 1 + speedMod;
        leftSpeed = 1 + speedMod;
        upSpeed = 5 + speedMod;
        for(Brick brick: currentLevel.getBricks()){
            if(currentLevel.getLevelBounds().contains(brick.getSprite().getBoundingRectangle())){
            if(brick.getSprite().isSolid()){
                if(!canHover){
                    if(getFloorRectangle().intersects(brick.getSprite().getTallTopRectangle())){
                        fallSpeed = 1;
                    }else if(getFloorRectangle().intersects(brick.getSprite().getTopRectangle())){
                        fallSpeed = 0;
                    }
                }
                if(getBoundingRectangle().intersects(brick.getSprite().getRightRectangle())){
                    leftSpeed = 0;
                }else if(getBoundingRectangle().intersects(brick.getSprite().getLeftRectangle())){
                    rightSpeed = 0;
                }
                if(getBoundingRectangle().intersects(brick.getSprite().getFloorRectangle())){
                    upSpeed = 0;
                }
            }
            }
        }
        for(Door door: currentLevel.getDoors()){
            if(!door.isOpen()){
                if(getBoundingRectangle().intersects(door.getRightRectangle())){
                    setLeftSpeed(0);
                }else if(getBoundingRectangle().intersects(door.getLeftRectangle())){
                    setRightSpeed(0);
                }
            }
        }
        for(Spike spike: currentLevel.getSpikes()){
            if(getBoundingRectangle().intersects(spike.getBoundingRectangle())){
                deductHealth(spike.getDamage());
            }
        }
        ivTick();
    }
    
    
    public void deductHealth(int deduction){
        if(!isInvulnerable){
            isInvulnerable = true;
            if(health - deduction < 0){
                health = 0;
            }else{
                health -= deduction;
            }
        }
    }
    public void addHealth(int increment){
        if(health + increment > MAX_HEALTH){
            health = MAX_HEALTH;
        }else{
            health += increment;
        }
    }
    
    
    public void setFallSpeed(int fallSpeed){
        this.fallSpeed = fallSpeed;
    }
    public void setRightSpeed(int rightSpeed){
       this.rightSpeed = rightSpeed; 
    }
    public void setLeftSpeed(int leftSpeed){
        this.leftSpeed = leftSpeed;
    }
    public void setUpSpeed(int upSpeed){
        this.upSpeed = upSpeed;
    }
    public final void setSpeedMod(int speed){
        this.speedMod = speed;
    }
    public final void setCanHover(boolean canHover){
        this.canHover = canHover;
    }
    
    public int getHealth(){
        return health;
    }
    public int getMaxHealth(){
        return MAX_HEALTH;
    }
    public int getFallSpeed(){
        return fallSpeed;
    }
    public int getRightSpeed(){
        return rightSpeed;
    }
    public int getLeftSpeed(){
        return leftSpeed;
    }
    public int getUpSpeed(){
        return upSpeed;
    }
    public int getSpeedMod(){
        return speedMod;
    }
    public boolean isInvulnerable(){
        return isInvulnerable;
    }
    public boolean isAlive(){
        return health > 0;
    }
    
    
    
}
