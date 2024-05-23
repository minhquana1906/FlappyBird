package Controller;

import View.Game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameController implements KeyListener, BirdActionListener, MouseListener {
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
        if(game.getCURRENT_SCREEN() == game.getMENU_SCREEN()){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                game.setCURRENT_SCREEN(game.getBEGIN_SCREEN());
                game.setGameStarted(true);
            }
        }

        //begin screen
        else if(game.getCURRENT_SCREEN() == game.getBEGIN_SCREEN()){
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                game.setCURRENT_SCREEN(game.getGAMEPLAY_SCREEN());
                game.setGameStarted(true);
            }
        }

        //gameplay screen
        else if(game.getCURRENT_SCREEN() == game.getGAMEPLAY_SCREEN()){
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                if(game.getBird().getLive()){
                    game.getBird().fly();
                }
            }
            else if(e.getKeyCode() == KeyEvent.VK_R){
                game.resetGame();
                game.setCURRENT_SCREEN(game.getBEGIN_SCREEN());
            }
            else if (e.getKeyCode() == KeyEvent.VK_P) {
                game.togglePause();
            }
        }

        //game over
        if(game.getCURRENT_SCREEN() == game.getGAMEOVER_SCREEN()){
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                game.resetGame();
                game.setCURRENT_SCREEN(game.getBEGIN_SCREEN());
            }
        }

        //exit in all conditions
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(!game.isPaused()){
                game.togglePause();
            }
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION)
                System.exit(0);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if(game.getCURRENT_SCREEN() == game.getMENU_SCREEN()){
            if(game.getStartButton().contains(e.getPoint())){
                System.out.println("switch");
                game.setCURRENT_SCREEN(game.getBEGIN_SCREEN());
                game.setGameStarted(true);
            }
            else if(game.getExitButton().contains(e.getPoint())){
                game.togglePause();
                int choice = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
