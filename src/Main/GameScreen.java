package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public abstract class GameScreen extends JFrame {

    private Game game;
    public static int MASTER_WIDTH = 500, MASTER_HEIGHT = 500;
    public int CUSTOM_WIDTH  = 500;
    public int CUSTOM_HEIGHT = 500;
    private GameThread G_Thread;
    
    public GameScreen(int w, int h){
        this.CUSTOM_WIDTH = w;
        this.CUSTOM_HEIGHT = h;
        MASTER_WIDTH = CUSTOM_WIDTH;
        MASTER_HEIGHT = CUSTOM_HEIGHT;
        InitThread();
        InitScreen();
    }
    
    public BufferedImage getImageWithID(int id){
        return null;
    }
    
    private void InitScreen(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(CUSTOM_WIDTH, CUSTOM_HEIGHT);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public void BeginGame(){
        G_Thread.StartThread();
    }
    
    private void InitThread(){
        G_Thread = new GameThread(this);
        add(G_Thread);
    }

    
    public abstract void GAME_UPDATE(long deltaTime);
    public abstract void GAME_PAINT(Graphics2D g2);

    
}
