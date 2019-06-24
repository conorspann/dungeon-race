

package game.level;

import java.util.Random;

/**
 *
 * @author Conor
 */
public class LevelGenerator {
    
    private char[][] level;
    private int currentX, currentY, levelSize, xDir, yDir;
    private final int MAX_ROOM_SIZE;
    private Random rand;
    
    public LevelGenerator(int size){
        MAX_ROOM_SIZE = 10;
        this.levelSize = size;
        level = new char[size][size];
        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                level[x][y] = '1';
            }
        }
        currentX = size/2;
        currentY = size/2;
        xDir = 0;
        yDir = 0;
        rand = new Random();
        
    }
    
    
    public void build(){
        spawnRooms();
        spawnPlayer();
    }
    
    
    private void spawnPlayer(){
        for(int y = 0; y < levelSize; y++){
            for(int x = 0; x < levelSize; x++){
                if(level[x][y] == '0'){
                    level[x][y] = '2';
                    return;
                }
            }
        }
    }
    
    
    private void spawnRooms(){
        for(int y = 1 + MAX_ROOM_SIZE; y < levelSize - (MAX_ROOM_SIZE*2); y++){
            for(int x = 1 + MAX_ROOM_SIZE; x < levelSize - (MAX_ROOM_SIZE*2); x++){
                if(level[x][y] == '1'){
                    if(getRandInt(0, 100) == 0){
                        buildRoom(x, y);
                        if(x < levelSize - MAX_ROOM_SIZE && y < levelSize - MAX_ROOM_SIZE - 1){
                            x += MAX_ROOM_SIZE/2;
                            y += MAX_ROOM_SIZE/2;
                        }
                    }
                }
            }
        }
    }
    
    
    private void buildRoom(int xStart, int yStart){
        level[xStart][yStart] = '0';
        int randSize = getRandInt(3, MAX_ROOM_SIZE);
        for(int y = 0; y < randSize; y++){
            for(int x = 0; x < randSize; x++){
                level[xStart + x][yStart + y] = '0';
            }
        }
        level[xStart + randSize/2][yStart] = 'c';
        level[xStart + randSize/2][yStart + randSize - 1] = 'c';
        level[xStart + randSize - 1][yStart + randSize/2] = 'c';
        level[xStart][yStart + randSize/2] = 'c';
    }
    //where c is a tunnel connection point
    
    
    private void setRandDir(){
        switch(getRandInt(0, 3)){
            case 0:
                xDir = 1;
                yDir = 0;
                break;
            case 1:
                xDir = -1;
                yDir = 0;
                break;
            case 2:
                xDir = 0;
                yDir = 1;
                break;
            case 3:
                xDir = 0;
                yDir = -1;
                break;
            default:
                xDir = 1;
                yDir = 0;
        }
    }
    
    private int getRandInt(int min, int max){
        return rand.nextInt((max - min) + 1) + min;
    }
    
    public char[][] generateLevel(){
        
        return level;
    }
    
    
    
    
    
    
    
    
    
}
