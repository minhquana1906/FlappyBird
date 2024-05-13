package Controller;

import Main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener, BirdActionListener {
    private Game game;
    private SoundManager soundManager;

    public GameController(Game game){
        this.game = game;
        soundManager = new SoundManager();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(game.getCURRENT_SCREEN() == game.getBEGIN_SCREEN()){
                game.setCURRENT_SCREEN(game.getGAMEPLAY_SCREEN());
            }
            else if(game.getCURRENT_SCREEN() == game.getGAMEPLAY_SCREEN()){
                if(game.getBird().getLive()){
                    game.getBird().fly();
                }
            }
            else{
                game.setCURRENT_SCREEN(game.getBEGIN_SCREEN());
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_R){
            if(game.getCURRENT_SCREEN() == game.getGAMEPLAY_SCREEN()){
                game.resetGame();
                game.setCURRENT_SCREEN(game.getBEGIN_SCREEN());
            }
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            if(game.getCURRENT_SCREEN() == game.getGAMEPLAY_SCREEN()){
                game.togglePause();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void onBirdFlap() {
        soundManager.playFlapSound();
    }

    @Override
    public void onBirdCollide() {
        soundManager.playCollideSound();
    }

    @Override
    public void onBirdPass() {
        soundManager.playGetScoreSound();
    }
}
