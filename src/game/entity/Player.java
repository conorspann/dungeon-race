

package game.entity;



import game.sprite.Brick;
import game.sprite.Door;
import game.InputHandler;
import game.sprite.Key;
import game.sprite.Ladder;
import game.level.Level;
import game.sprite.MonkeyBar;
import game.sprite.Spike;
import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 *
 * @author Conor
 */
public class Player extends Entity{
    
    
    private Key currentKey;
    
    
    private int energy, energyTick;
    private boolean onLadder, onMonkeyBar, hasPistol;
    
    
    
    public Player(int x, int y, int width, int height){
        super("img//sheets//player_sheet.png", 7, 10, 16, x, y, width, height, 3);
        setSpeedMod(1);
        energy = 3;
        energyTick = 0;
        onLadder = false;
        onMonkeyBar = false;
        hasPistol = false;
    }
    
    
    
    public void energyTick(boolean deduct){
        if(energyTick < 100){
            energyTick++;
        }else{
            energyTick = 0;
            if(deduct){
                deductEnergy();
            }else{
                addEnergy();
            }
        }
    }
    
    public void animateWalk(){
        if(tick()){
            if(getCurrentFrame() < 2){
                setCurrentFrame(getCurrentFrame() + 1);
            }else{
                setCurrentFrame(0);
            }
        }
    }
    
    
    public void control(Level currentLevel, InputHandler input){
        if(input.getKey(KeyEvent.VK_RIGHT)){
            animateWalk();
            sideScroll(currentLevel, -getRightSpeed(), 0);
        }else if(input.getKey(KeyEvent.VK_LEFT)){
            animateWalk();
            sideScroll(currentLevel, getLeftSpeed(), 0);
        }else if(hasPistol){
            if(input.getKey(KeyEvent.VK_D)){
                setCurrentFrame(5);
                if(input.getKey(KeyEvent.VK_ENTER)){
                    //shoot to right
                    currentLevel.getBullet().fire(Direction.EAST);
                }
            }else if(input.getKey(KeyEvent.VK_A)){
                setCurrentFrame(4);
                if(input.getKey(KeyEvent.VK_ENTER)){
                    //shoot to left
                    currentLevel.getBullet().fire(Direction.WEST);
                }
            }else{
                setCurrentFrame(0);
            }
        }
        if(onLadder){
            if(input.getKey(KeyEvent.VK_UP)){
                sideScroll(currentLevel, 0, getUpSpeed());
            }
        }
        if(onMonkeyBar && input.getKey(KeyEvent.VK_SPACE) && energy > 0){
            setFallSpeed(0);
            energyTick(true);
        }else{
            energyTick(false);
        }
        
        sideScroll(currentLevel, 0, -getFallSpeed());
    }
    
    
    public void sideScroll(Level currentLevel, int xDir, int yDir){
        for(Brick brick: currentLevel.getBricks()){
            brick.getSprite().setX(brick.getSprite().getX() + xDir);
            brick.getSprite().setY(brick.getSprite().getY() + yDir);
        }
        for(Key key: currentLevel.getKeys()){
            key.setX(key.getX() + xDir);
            key.setY(key.getY() + yDir);
        }
        for(Door door: currentLevel.getDoors()){
            door.setX(door.getX() + xDir);
            door.setY(door.getY() + yDir);
        }
        for(Ladder ladder: currentLevel.getLadders()){
            ladder.setX(ladder.getX() + xDir);
            ladder.setY(ladder.getY() + yDir);
        }
        for(MonkeyBar monkeyBar: currentLevel.getMonkeyBars()){
            monkeyBar.setX(monkeyBar.getX() + xDir);
            monkeyBar.setY(monkeyBar.getY() + yDir);
        }
        for(Spike spike: currentLevel.getSpikes()){
            spike.setX(spike.getX() + xDir);
            spike.setY(spike.getY() + yDir);
        }
        for(Skeleton skeleton: currentLevel.getSkeletons()){
            skeleton.setX(skeleton.getX() + xDir);
            skeleton.setY(skeleton.getY() + yDir);
        }
        if(currentLevel.getPistol() != null){
            currentLevel.getPistol().setX(currentLevel.getPistol().getX() + xDir);
            currentLevel.getPistol().setY(currentLevel.getPistol().getY() + yDir);
        }
        if(currentLevel.getBullet().exists()){
           currentLevel.getBullet().setX(currentLevel.getBullet().getX() + xDir);
           currentLevel.getBullet().setY(currentLevel.getBullet().getY() + yDir);
        }
        if(currentLevel.getRedGem() != null){
            currentLevel.getRedGem().setX(currentLevel.getRedGem().getX() + xDir);
            currentLevel.getRedGem().setY(currentLevel.getRedGem().getY() + yDir);
        }
    }
    
    
    @Override
    public void collision(Level currentLevel){
        super.collision(currentLevel);
        if(currentKey == null){
            for (Iterator<Key> it = currentLevel.getKeys().iterator(); it.hasNext();) {
                Key key = it.next();
                if(getBoundingRectangle().intersects(key.getBoundingRectangle())){
                    currentKey = key;
                    it.remove();
                    currentLevel.getUI().setHasKey(true);
                }
            }
        }
        for(Door door: currentLevel.getDoors()){
            if(!door.isOpen() && currentKey != null){
                if(getRightRectangle().intersects(door.getBoundingRectangle()) || getLeftRectangle().intersects(door.getBoundingRectangle())){
                    door.open();
                    currentKey = null;
                    currentLevel.getUI().setHasKey(false);
                }
            }
        }
        onLadder = false;
        for(Ladder ladder: currentLevel.getLadders()){
            if(getBoundingRectangle().intersects(ladder.getInnerColumnRectangle())){
                onLadder = true;
            }
        }
        onMonkeyBar = false;
        for(MonkeyBar monkeyBar: currentLevel.getMonkeyBars()){
            if(getBoundingRectangle().intersects(monkeyBar.getBoundingRectangle())){
                onMonkeyBar = true;
            }
        }
        for(Skeleton skeleton: currentLevel.getSkeletons()){
            if(skeleton.isAlive()){
                if(getBoundingRectangle().intersects(skeleton.getBoundingRectangle())){
                    deductHealth(skeleton.getDamage());
                }
            }
        }
        if(currentLevel.getPistol() != null){
            if(getBoundingRectangle().intersects(currentLevel.getPistol().getBoundingRectangle())){
                hasPistol = true;
                currentLevel.removePistol();
                currentLevel.getUI().setHasPistol(true);
            }
        }
        if(currentLevel.getRedGem() != null){
            if(getBoundingRectangle().intersects(currentLevel.getRedGem().getBoundingRectangle())){
                currentLevel.win();
            }
        }
    }
    
    
    
    
    public void deductEnergy(){
        if(energy > 0){
            energy -= 1;
        }
    }
    public void addEnergy(){
        energyTick = 0;
        if(energy < 3){
            energy += 1;
        }
    }
    
    
    
    public int getEnergy(){
        return energy;
    }
    
    
    
}
