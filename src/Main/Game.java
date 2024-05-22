package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.*;
import View.AFrameOnImage;
import View.Animation;
import Controller.GameController;

public class Game extends GameScreen {
	private BufferedImage birds;
	private Animation bird_anim;
	
	private Bird bird;
	private Ground ground;
	private ChimneyGroup chimneyGroup;

	private Score scoreManager;
	private boolean isGameStarted;

	private ImageIcon menu;
	private ImageIcon start;
	private ImageIcon exit;
	private ImageIcon logo;
	private ImageIcon gameOver;

	private Rectangle startButton;
	private Rectangle exitButton;
	
	private int BEGIN_SCREEN = 0;
	private int GAMEPLAY_SCREEN = 1;
	private int GAMEOVER_SCREEN = 2;
	private int MENU_SCREEN = 3;
	private int PAUSE_SCREEN = 4;
	private int CURRENT_SCREEN = MENU_SCREEN;


	public static float g = 0.15f;

	private GameController gameController;
	private boolean paused = false;

	public int getBEGIN_SCREEN() {
		return BEGIN_SCREEN;
	}

	public void setBEGIN_SCREEN(int BEGIN_SCREEN) {
		this.BEGIN_SCREEN = BEGIN_SCREEN;
	}

	public int getGAMEPLAY_SCREEN() {
		return GAMEPLAY_SCREEN;
	}

	public void setGAMEPLAY_SCREEN(int GAMEPLAY_SCREEN) {
		this.GAMEPLAY_SCREEN = GAMEPLAY_SCREEN;
	}

	public int getGAMEOVER_SCREEN() {
		return GAMEOVER_SCREEN;
	}

	public void setGAMEOVER_SCREEN(int GAMEOVER_SCREEN) {
		this.GAMEOVER_SCREEN = GAMEOVER_SCREEN;
	}

	public int getCURRENT_SCREEN() {
		return CURRENT_SCREEN;
	}

	public void setCURRENT_SCREEN(int CURRENT_SCREEN) {
		this.CURRENT_SCREEN = CURRENT_SCREEN;
	}

	public Bird getBird() {
		return bird;
	}

	public void setBird(Bird bird) {
		this.bird = bird;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isGameStarted() {
		return isGameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		isGameStarted = gameStarted;
	}

	public int getMENU_SCREEN() {
		return MENU_SCREEN;
	}

	public Rectangle getStartButton() {
		return startButton;
	}

	public void setStartButton(Rectangle startButton) {
		this.startButton = startButton;
	}

	public Rectangle getExitButton() {
		return exitButton;
	}

	public void setExitButton(Rectangle exitButton) {
		this.exitButton = exitButton;
	}

	public Game() {
		super(800, 600);
		loadImage();
		initVariables();
		createAnimation();

		BeginGame();
		initGameController();
	}

	private void loadImage(){
		try {
			menu = new ImageIcon("src/Sprites/menu.jpg");
			start  = new ImageIcon("src/Sprites/start_S.jpg");
			exit = new ImageIcon("src/Sprites/exit_S.jpg");
			logo = new ImageIcon("src/Sprites/Flappy_Logo.png");
			gameOver = new ImageIcon("src/Sprites/gameOver.png");
			birds = ImageIO.read(new File("src/Sprites/bird_sprite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initVariables() {
		bird = new Bird(350, 250, 45, 45);
		ground = new Ground();
		chimneyGroup = new ChimneyGroup();
		scoreManager = new Score();

		isGameStarted = false;
		startButton = new Rectangle(180, 330, start.getIconWidth(), start.getIconHeight());
		exitButton = new Rectangle(190, 450, exit.getIconWidth(), exit.getIconHeight());

	}

	private void initGameController(){
		gameController = new GameController(this);
		addKeyListener(gameController);

		bird.setBirdActionListener(gameController);
		this.addMouseListener(gameController);
	}

	public void togglePause(){
		paused = !paused;
	}

	private void createAnimation(){
		bird_anim = new Animation(100);
		AFrameOnImage f;
		f = new AFrameOnImage(0, 0, 60, 60);
		bird_anim.AddFrame(f);
		f = new AFrameOnImage(60, 0, 60, 60);
		bird_anim.AddFrame(f);
		f = new AFrameOnImage(120, 0, 60, 60);
		bird_anim.AddFrame(f);
		f = new AFrameOnImage(60, 0, 60, 60);
		bird_anim.AddFrame(f);
	}

	public void resetGame() {
		bird.setPos(350, 250);
		bird.setV(0);
		bird.setLive(true);

		scoreManager.setScore(0);
		chimneyGroup.resetChimneys();
	}
	
	@Override
	public void GAME_UPDATE(long deltaTime) {
		if(isPaused()){
			return;
		}
		if(CURRENT_SCREEN == BEGIN_SCREEN) {
			resetGame();
		}else if(CURRENT_SCREEN == GAMEPLAY_SCREEN) {

			if(bird.getLive()) bird_anim.Update_Me(deltaTime);
			bird.update(deltaTime);
			ground.update();
			chimneyGroup.update();

			for(int i = 0; i < ChimneyGroup.SIZE; i++) {
				if(bird.getRect().intersects(chimneyGroup.getChimney(i).getRect())) {
					bird.setLive(false);
				}		
			}

			for(int i = 0; i < ChimneyGroup.SIZE; i++) {
				if(bird.getPosX() > chimneyGroup.getChimney(i).getPosX() + chimneyGroup.getChimney(i).getW() && !chimneyGroup.getChimney(i).getIsBehindBird() && i%2==0) {
					scoreManager.increaseScore();
					gameController.onBirdPass();
					chimneyGroup.getChimney(i).setIsBehindBird(true);
				}
			}
			
			if(bird.getPosY() + bird.getH() > ground.getYGround() || bird.getPosY() + bird.getH() <= 0) {
				CURRENT_SCREEN = GAMEOVER_SCREEN; 
				scoreManager.updateHighScore();
			}
			
		}
	}

	@Override
	public void GAME_PAINT(Graphics2D g2) {
		g2.drawImage(menu.getImage(), 0, 0, 800, 600, this);
		g2.drawImage(start.getImage(), 180, 330, null);
		g2.drawImage(exit.getImage(), 190, 420, null);

		//ve game
		if(isGameStarted()){
			g2.setFont(new Font("Arial", Font.BOLD, 24));
			g2.setColor(Color.decode("#b8daef"));

			//ve background
			g2.fillRect(0, 0, MASTER_WIDTH, MASTER_HEIGHT);

			chimneyGroup.paint(g2);
			ground.paint(g2);


			if(bird.getIsFlying()) bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, -1);
			else bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);

			if(CURRENT_SCREEN == BEGIN_SCREEN) {
				if(isPaused())
					togglePause();
				g2.setColor(Color.white);
				g2.drawString("Press SPACE button to play game!", 200, 350);
				g2.drawImage(logo.getImage(), (800 - logo.getIconWidth())/2, 100, null);

			}else if(CURRENT_SCREEN == GAMEOVER_SCREEN) {
				g2.drawImage(gameOver.getImage(), (800 - logo.getIconWidth())/2, 100, null);
				g2.setColor(new Color(252, 160, 72));
				g2.drawString("Press SPACE to turn back begin screen!", 200, 350);
				g2.setColor(Color.white);
				g2.drawString("Score:" + scoreManager.getScore(), 200, 300);
				g2.setColor(Color.white);
				if(scoreManager.getHighScore() > 0) g2.drawString("High score:" + scoreManager.getHighScore(), 200, 250);
				else g2.drawString("High score:" + "0", 200, 250);
			}

			g2.setColor(Color.white);
			g2.drawString("Score:" + scoreManager.getScore(), 10, 20);
		}

		if(isPaused() && CURRENT_SCREEN == GAMEPLAY_SCREEN){
			g2.setColor(Color.white);
			g2.drawString("Game Paused", 300, 200);
			g2.drawString("Press 'P' to resume", 250, 250);
			g2.drawString("Press 'N' for new game", 250, 280);
			g2.drawString("Press 'Esc' to exit", 250, 310);
		}
	}
}
