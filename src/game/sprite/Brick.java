

package game.sprite;


/**
 *
 * @author Conor
 */
public class Brick{
    
    private Sprite brick;
    
    public Brick(BrickType type, int x, int y, int width, int height){
        switch(type){
            case SOLID_BG:
                brick = new Sprite("img//brick_solid_grey.png", x, y, width, height);
                brick.setSolid(true);
                break;
            case BG:
                brick = new Sprite("img//brick_back_grey.png", x, y, width, height);
                break;
            default:
                brick = new Sprite("img//brick_back_grey.png", x, y, width, height);
        }
    }
    
    
    public Sprite getSprite(){
        return brick;
    }
    
}
