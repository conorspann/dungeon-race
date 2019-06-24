

package game.entity;

import game.level.Level;

/**
 *
 * @author Conor
 */
public class Skeleton extends Enemy{
    
    public Skeleton(int x, int y, int width, int height){
        super("img//sheets//enemy_skeleton_sheet.png", 4, 12, 17, x, y, width, height, 2);
        setState(State.STRAFING);
        setXRange(150);
        setXSight(150);
        setDirection(Direction.EAST);
        setDamage(1);
    }
    
    public void animate(){
        if(getCurrentFrame() < getNoFrames() - 2){
            setCurrentFrame(getCurrentFrame() + 1);
        }else{
            setCurrentFrame(0);
        }
    }
    
    @Override
    public void control(Level level){
        if(isAlive()){
            if(getSightBoundingRectangle().intersects(level.getPlayer().getBoundingRectangle())){
                setState(State.ATTACKING);
            }else{
                setState(State.IDLE);
            }
            if(getState() == State.ATTACKING){
                move();
                animate();
                if(level.getPlayer().getX() > getX()){
                    setDirection(Direction.EAST);
                }else if(level.getPlayer().getX() < getX()){
                    setDirection(Direction.WEST);
                }
            }
        }else{
            setCurrentFrame(3);
        }
    }
}
