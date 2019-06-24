

package game.level;

import game.ui.UI;
import game.sprite.Brick;
import game.sprite.BrickType;
import game.sprite.Door;
import game.sprite.Ladder;
import game.sprite.Key;
import game.sprite.Pistol;
import game.sprite.Spike;
import game.sprite.RedGem;
import game.sprite.MonkeyBar;
import game.entity.Player;
import game.entity.Skeleton;
import game.entity.Bullet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Conor
 */
public class Level {
    
    
    private String filename;
    private int screenWidth, screenHeight, sizeRatio, startTime, timeLeft, tick;
    private boolean winLevel;
    
    
    private ArrayList<Brick> bricks;
    private ArrayList<Key> keys;
    private ArrayList<Door> doors;
    private ArrayList<Ladder> ladders;
    private ArrayList<MonkeyBar> monkeyBars;
    private ArrayList<Spike> spikes;
    
    private ArrayList<Skeleton> skeletons;
    
    
    
    private Player player;
    private Pistol pistol;
    private Bullet bullet;
    
    private RedGem redGem;
    
    private UI ui;
    
    
    public Level(String filename, int screenWidth, int screenHeight, int sizeRatio){
        this.filename = filename;
        winLevel = false;
        startTime = 0;
        timeLeft = 0;
        tick = 0;
        bricks = new ArrayList<>();
        keys = new ArrayList<>();
        doors = new ArrayList<>();
        ladders = new ArrayList<>();
        monkeyBars = new ArrayList<>();
        spikes = new ArrayList<>();
        skeletons = new ArrayList<>();
        ui = new UI(0, screenHeight - 50);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.sizeRatio = sizeRatio;
        loadFromFile();
        centerAroundPlayer();
        bullet = new Bullet(player.getX(), player.getY() + (player.getHeight()/2)); //must be called after centerAroundPlayer();
    }
    
    
    
    private void loadFromGen(){
        LevelGenerator gen = new LevelGenerator(150);
        gen.build();
        char[][] level = gen.generateLevel();
        boolean hasPlayer = false;
        for(int y = 0; y < level.length; y++){
            for(int x = 0; x < level.length; x++){
                switch(level[x][y]){
                    case '0':
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        break;
                    case '1':
                        bricks.add(new Brick(BrickType.SOLID_BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        break;
                    case '2':
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        if(!hasPlayer){
                            player = new Player(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio);
                            hasPlayer = true;
                        }
                        break;
                    case '3':
                        //key
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        keys.add(new Key(x*sizeRatio, (y*sizeRatio)+(sizeRatio/2), sizeRatio/2, sizeRatio/2));
                        break;
                    case '4':
                        //door
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        doors.add(new Door(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        break;
                    case '5':
                        //ladder
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        ladders.add(new Ladder(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        break;
                    case '6':
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        monkeyBars.add(new MonkeyBar(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        break;
                    case '7':
                        //spike
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        spikes.add(new Spike(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        break;
                    case '8':
                        //skeleton
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        skeletons.add(new Skeleton(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        break;
                    case '9':
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        if(pistol == null){
                            pistol = new Pistol(x*sizeRatio, (y*sizeRatio)+(sizeRatio/2), sizeRatio/2, sizeRatio/2);
                        }
                    case 'R':
                    case 'r':
                        bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                        if(redGem ==  null){
                            redGem = new RedGem(x*sizeRatio, (y*sizeRatio)+(sizeRatio/2), sizeRatio/2, sizeRatio/2);
                        }
                        break;

                }
            }
        }
    }
    
    
    
    
    
    private void loadFromFile(){
        boolean hasPlayer = false;
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String readLine;
            
            startTime = Integer.parseInt(buffer.readLine());
            timeLeft = startTime;
            
            int y = 0;
            while((readLine = buffer.readLine()) != null){
                char[] objectCodes = readLine.toCharArray();
                for(int x = 0; x < objectCodes.length; x++){
                    switch(objectCodes[x]){
                        case '0':
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            break;
                        case '1':
                            bricks.add(new Brick(BrickType.SOLID_BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            break;
                        case '2':
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            if(!hasPlayer){
                                player = new Player(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio);
                                hasPlayer = true;
                            }
                            break;
                        case '3':
                            //key
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            keys.add(new Key(x*sizeRatio, (y*sizeRatio)+(sizeRatio/2), sizeRatio/2, sizeRatio/2));
                            break;
                        case '4':
                            //door
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            doors.add(new Door(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            break;
                        case '5':
                            //ladder
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            ladders.add(new Ladder(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            break;
                        case '6':
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            monkeyBars.add(new MonkeyBar(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            break;
                        case '7':
                            //spike
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            spikes.add(new Spike(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            break;
                        case '8':
                            //skeleton
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            skeletons.add(new Skeleton(x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            break;
                        case '9':
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            if(pistol == null){
                                pistol = new Pistol(x*sizeRatio, (y*sizeRatio)+(sizeRatio/2), sizeRatio/2, sizeRatio/2);
                            }
                        case 'R':
                        case 'r':
                            bricks.add(new Brick(BrickType.BG, x*sizeRatio, y*sizeRatio, sizeRatio, sizeRatio));
                            if(redGem ==  null){
                                redGem = new RedGem(x*sizeRatio, (y*sizeRatio)+(sizeRatio/2), sizeRatio/2, sizeRatio/2);
                            }
                            break;
                            
                    }
                }
                y++;
            }
        }catch(IOException e){
            
        }
    }
    
    
    private void centerAroundPlayer(){
        int xdif = ((screenWidth/2) - (player.getWidth()/2)) - player.getX();
        int ydif = ((screenHeight/2) - (player.getHeight()/2)) - player.getY();
        player.setX(player.getX() + xdif);
        player.setY(player.getY() + ydif);
        for(Brick brick: bricks){
            brick.getSprite().setX(brick.getSprite().getX() + xdif);
            brick.getSprite().setY(brick.getSprite().getY() + ydif);
        }
        for(Key key: keys){
            key.setX(key.getX() + xdif);
            key.setY(key.getY() + ydif);
        }
        for(Door door: doors){
            door.setX(door.getX() + xdif);
            door.setY(door.getY() + ydif);
        }
        for(Ladder ladder: ladders){
            ladder.setX(ladder.getX() + xdif);
            ladder.setY(ladder.getY() + ydif);
        }
        for(MonkeyBar monkeyBar: monkeyBars){
            monkeyBar.setX(monkeyBar.getX() + xdif);
            monkeyBar.setY(monkeyBar.getY() + ydif);
        }
        for(Spike spike: spikes){
            spike.setX(spike.getX() + xdif);
            spike.setY(spike.getY() + ydif);
        }
        for(Skeleton skeleton: skeletons){
            skeleton.setX(skeleton.getX() + xdif);
            skeleton.setY(skeleton.getY() + ydif);
        }
        if(pistol != null){
            pistol.setX(pistol.getX() + xdif);
            pistol.setY(pistol.getY() + ydif);
        }
        if(redGem != null){
            redGem.setX(redGem.getX() + xdif);
            redGem.setY(redGem.getY() + ydif);
        }
    }
    
    private void drawDarkness(Graphics2D graphics){
        int squareWidth = screenWidth/8;
        int squareHeight = screenHeight/8;
        for(int y = 0; y < screenHeight; y += squareHeight){
            for(int x = 0; x < screenWidth; x += squareWidth){
                int alpha;
                if(x > 100 && x < 600 && y > 100 && y < 600){
                    alpha = 200;
                }else{
                    alpha = 245;
                }
                Color color = new Color(20, 20, 20, alpha);
                graphics.setColor(color);
                graphics.fillRect(x, y, squareWidth, squareHeight);
            }
        }
    }
    
    public void draw(Graphics2D graphics){
        for(Brick brick: bricks){
            if(getLevelBounds().contains(brick.getSprite().getBoundingRectangle())){
                brick.getSprite().draw(graphics);
            }
        }
        for(Key key: keys){
            if(getLevelBounds().contains(key.getBoundingRectangle())){
                key.draw(graphics);
            }
        }
        for(Door door: doors){
            if(getLevelBounds().contains(door.getBoundingRectangle())){
                door.draw(graphics);
            }
        }
        for(Ladder ladder: ladders){
            if(getLevelBounds().contains(ladder.getBoundingRectangle())){
                ladder.draw(graphics);
            }
        }
        for(MonkeyBar monkeyBar: monkeyBars){
            if(getLevelBounds().contains(monkeyBar.getBoundingRectangle())){
                monkeyBar.draw(graphics);
            }
        }
        for(Spike spike: spikes){
            if(getLevelBounds().contains(spike.getBoundingRectangle())){
                spike.draw(graphics);
            }
            
        }
        for(Skeleton skeleton: skeletons){
            if(getLevelBounds().contains(skeleton.getBoundingRectangle())){
                skeleton.draw(graphics);
            }
        }
        player.draw(graphics);
        if(pistol != null){
            if(getLevelBounds().contains(pistol.getBoundingRectangle())){
                pistol.draw(graphics);
            }
        }
        if(redGem != null){
            if(getLevelBounds().contains(redGem.getBoundingRectangle())){
                redGem.draw(graphics);
            }
        }
        //drawDarkness(graphics);
        if(getLevelBounds().contains(bullet.getBoundingRectangle())){
            bullet.draw(graphics);
        }
        
        ui.draw(graphics);
    }
    
    public void tick(){
        if(tick == 100){
            if(timeLeft > 0){
                timeLeft -= 1;
            }else{
                //gameOver
            }
            tick = 0;
        }else{
            tick++;
        }
        ui.setTimeLeft(timeLeft);
    }
    
    public void removePistol(){
        pistol = null;
    }
    public void win(){
        winLevel = true;
    }
    
    public boolean hasWon(){
        return winLevel;
    }
    public boolean timeOut(){
        return timeLeft == 0;
    }
    
    public Rectangle getLevelBounds(){
        return new Rectangle(-sizeRatio, -sizeRatio, screenWidth+(sizeRatio*3), screenHeight+(sizeRatio*3));
    }
    
    public Player getPlayer(){
        return player;
    }
    public Pistol getPistol(){
        return pistol;
    }
    public Bullet getBullet(){
        return bullet;
    }
    public RedGem getRedGem(){
        return redGem;
    }
    public UI getUI(){
        return ui;
    }
    
    
    public ArrayList<Brick> getBricks(){
        return bricks;
    }
    public ArrayList<Key> getKeys(){
        return keys;
    }
    public ArrayList<Door> getDoors(){
        return doors;
    }
    public ArrayList<Ladder> getLadders(){
        return ladders;
    }
    public ArrayList<MonkeyBar> getMonkeyBars(){
        return monkeyBars;
    }
    public ArrayList<Spike> getSpikes(){
        return spikes;
    }
    public ArrayList<Skeleton> getSkeletons(){
        return skeletons;
    }
    
    
}
