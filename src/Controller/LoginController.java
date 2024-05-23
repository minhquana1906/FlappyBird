package Controller;

import DataAccessObject.DAO_Player;
import Model.Player;
import View.Game;
import View.LoginForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginController implements ActionListener, KeyListener {

    private LoginForm login;
    private DAO_Player dao;
    private Game game;

    public LoginController(LoginForm login){
        this.login = login;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        dao = new DAO_Player();

        if(login.getBtn_play().getText().equals(cmd)){
            String name = login.getTextField_input().getText();

            Player player = new Player(name, 0);

            if(name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill your name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }



            login.dispose();
            game = new Game();
            game.setPlayer(player);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            String name = login.getTextField_input().getText();

            Player player = new Player(name, 0);

            if(name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill your name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            login.dispose();
            game = new Game();
            game.setPlayer(player);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
