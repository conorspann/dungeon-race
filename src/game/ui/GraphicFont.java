

package game.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Conor
 */
public class GraphicFont {
    
    private BufferedImage fontSheet;
    private String ascii = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789:";
    
    private final int SYMBOL_SIZE = 10;
    
    public GraphicFont(){
        fontSheet = null;
        try{
            fontSheet = ImageIO.read(new File("img//font//font.png"));
        }catch(IOException e){
            System.out.println("Exception when loading font sheet image");
        }
    }
    
    private int getAsciiIndex(char symbol){
        for(int i = 0; i < ascii.length(); i++){
            if(symbol == ascii.charAt(i)){
                return i;
            }
        }
        return -1;
    }
    
    private BufferedImage getSymbol(char symbol){
        return fontSheet.getSubimage(SYMBOL_SIZE * getAsciiIndex(symbol), 0, SYMBOL_SIZE, SYMBOL_SIZE);
    }
    
    public void drawString(String text, int x, int y, Graphics2D graphics){
        for(int symbolNo = 0; symbolNo < text.length(); symbolNo++){
            graphics.drawImage(getSymbol(text.charAt(symbolNo)), x + (symbolNo * SYMBOL_SIZE), y, null);
        }
    }
    public void drawString(String text, int x, int y, int size, Graphics2D graphics){
        for(int symbolNo = 0; symbolNo < text.length(); symbolNo++){
            graphics.drawImage(getSymbol(text.charAt(symbolNo)), (x + (symbolNo * SYMBOL_SIZE)) + (symbolNo*size), y, size, size, null);
        }
    }
    
    
    
    
    
}
