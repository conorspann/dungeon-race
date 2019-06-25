



package game;

import game.level.Level;
import game.menu.PauseMenu;
import game.menu.GameOverMenu;
import game.menu.WinGameMenu;
import game.menu.MainMenu;
import game.entity.Skeleton;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 *
 * @author Conor
 */
public class Game implements Runnable{
    private JFrame frame;
    private InputHandler input;
    private MainMenu mainMenu;
    private Thread thread;
    private Level currentLevel;
    private PauseMenu pauseMenu;
    private GameOverMenu gameOverMenu;
    private WinGameMenu winGameMenu;
    private BufferStrategy bufferStrat;
    
    
    private int screenWidth, screenHeight;
    private boolean gameInProgress, gameOver, winGame, paused;
    
    public Game(){
        screenWidth = 800;
        screenHeight = 800;
        frame = new JFrame("Dungeon");
        frame.setSize(screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        input = new InputHandler();
        frame.addKeyListener(input);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.createBufferStrategy(2);
        bufferStrat = frame.getBufferStrategy();
        mainMenu = new MainMenu();
        pauseMenu = new PauseMenu((screenWidth/2)-200, (screenHeight/2)-200);
        gameOverMenu = new GameOverMenu((screenWidth/2)-200, (screenHeight/2)-200);
        winGameMenu = new WinGameMenu((screenWidth/2)-200, (screenHeight/2)-200);
        gameInProgress = false;
        gameOver = false;
        winGame = false;
        paused = true;
        thread = new Thread(this);
    }
    public void start(){
        thread.start();
    }
    @Override
    public void run(){
        Thread current = Thread.currentThread();
        currentLevel = new Level("level//testLevel.txt", screenWidth, screenHeight, 50);
        if(currentLevel != null){
            gameInProgress = true;
        }
        while(current == thread){
            try{
                Graphics2D graphics = (Graphics2D)bufferStrat.getDrawGraphics();
                render(graphics);
                graphics.dispose();
                bufferStrat.show();
                
                
                
                if(gameInProgress && !gameOver && !winGame){
                    if(paused){
                        pauseMenu.control(input);
                        if(pauseMenu.hasQuit()){
                            paused = false;
                            pauseMenu.resetQuit();
                        }
                    }else{
                        currentLevel.getPlayer().collision(currentLevel);
                        currentLevel.getPlayer().control(currentLevel, input);
                        currentLevel.getBullet().control();
                        currentLevel.getBullet().collision(currentLevel);
                        currentLevel.getUI().setHealth(currentLevel.getPlayer());
                        currentLevel.getUI().setEnergy(currentLevel.getPlayer());
                        winGame = currentLevel.hasWon();
                        if(!currentLevel.getPlayer().isAlive() || currentLevel.timeOut()){
                           gameOver = true; 
                        }
                        //gameOver = !currentLevel.getPlayer().isAlive();
                        for(Skeleton skeleton: currentLevel.getSkeletons()){
                            skeleton.collision(currentLevel);
                            skeleton.control(currentLevel);
                        }
                        currentLevel.tick();
                        //gameOver = currentLevel.timeOut();
                        if(input.getKey(KeyEvent.VK_P)){
                            paused = true;
                        }
                    }
                }
                
                if(gameOver){
                    gameOverMenu.control(input);
                }else if(winGame){
                    winGameMenu.control(input);
                }
                if(gameOverMenu.hasQuit() || winGameMenu.hasQuit()){
                    //Exit game
                    System.exit(0);
                }
                
                
                Thread.sleep(5);
            }catch(InterruptedException e){
                System.out.println("Interrupted Thread Exception in Game main execution loop.");
            }
        }
    }
    
    
    
    
    
    private void render(Graphics2D graphics){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, screenWidth+(screenWidth/2), screenHeight+(screenHeight/2));
        if(gameInProgress){
            currentLevel.draw(graphics);
        }
        if(paused){
            pauseMenu.draw(graphics);
        }else if(winGame){
            winGameMenu.draw(graphics);
        }else if(gameOver){
            gameOverMenu.draw(graphics);
        }
    }
    
    
}
