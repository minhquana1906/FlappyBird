package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import Model.Bird;
import Model.ChimneyGroup;
import Model.Ground;
import View.AFrameOnImage;
import View.Animation;
import Controller.GameController;

public class Game extends GameScreen {

	private BufferedImage birds;
	private Animation bird_anim;
	
	private Bird bird;
	private Ground ground;
	private ChimneyGroup chimneyGroup;
	
	private int SCORE = 0;
	private String HIGH_SCORE = "";
	
	private int BEGIN_SCREEN = 0;
	private int GAMEPLAY_SCREEN = 1;
	private int GAMEOVER_SCREEN = 2;
	
	private int CURRENT_SCREEN = BEGIN_SCREEN;
	
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
			birds = ImageIO.read(new File("src/Sprites/bird_sprite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initVariables() {
		bird = new Bird(350, 250, 45, 45);
		ground = new Ground();
		chimneyGroup = new ChimneyGroup();
    }

	private void initGameController(){
		gameController = new GameController(this);
		addKeyListener(gameController);

		bird.setBirdActionListener(gameController);
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
		
		SCORE = 0;
		
		chimneyGroup.resetChimneys();
	}

	
	public void updateHighScore() {
		int TEMP = -1;
		if(this.getHighScore() != null) TEMP = Integer.parseInt(this.getHighScore());
		if(SCORE > TEMP) HIGH_SCORE = "" + SCORE;
		
		
		File scoreFile = new File("src/high_score.txt");
		if(!scoreFile.exists())
		{
			try {
				scoreFile.createNewFile();
			} catch(IOException e) {
			}
		}
		
		FileWriter fw = null;
		BufferedWriter bw = null;	
		try {
			fw = new FileWriter(scoreFile);
			bw = new BufferedWriter(fw);	
			bw.write(this.HIGH_SCORE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getHighScore() {
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader("src/high_score.txt");
			br = new BufferedReader(fr);
			return br.readLine();
		} catch (IOException e) {
			return "0";
		} finally {
			try {
				if(br != null) br.close();
			} catch (IOException e) {}
		}
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
				if(bird.getPosX() > chimneyGroup.getChimney(i).getPosX() && !chimneyGroup.getChimney(i).getIsBehindBird() && i%2==0) {
					SCORE++;
					gameController.onBirdPass();
					chimneyGroup.getChimney(i).setIsBehindBird(true);
				}
			}
			
			if(bird.getPosY() + bird.getH() > ground.getYGround() || bird.getPosY() + bird.getH() <= 0) {
				CURRENT_SCREEN = GAMEOVER_SCREEN; 
				HIGH_SCORE = this.getHighScore();
				updateHighScore();
			}
			
		}else {
			
		}
	}

	@Override
	public void GAME_PAINT(Graphics2D g2) {
		g2.setFont(new Font("Arial", Font.BOLD, 24));
		g2.setColor(Color.decode("#b8daef"));

		//ve background
		g2.fillRect(0, 0, MASTER_WIDTH, MASTER_HEIGHT);

		chimneyGroup.paint(g2);
		ground.paint(g2);


		if(bird.getIsFlying()) bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, -1);
		else bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);

		if(CURRENT_SCREEN == BEGIN_SCREEN) {
			g2.setColor(Color.white);
			g2.drawString("Press SPACE button to play game!", getWidth()/4, getHeight()/2);
		}else if(CURRENT_SCREEN == GAMEOVER_SCREEN) {
			g2.setColor(Color.white);
			g2.drawString("Press SPACE to turn back begin screen!", getWidth()/4, getHeight()/2);
			g2.drawString("Score:" + SCORE, getWidth()/4, getHeight()/3);
			g2.setColor(Color.white);
			if(this.getHighScore() != null) g2.drawString("High score:" + HIGH_SCORE, getWidth()/4, getHeight()/4);
			else g2.drawString("High score:" + "0", getWidth()/4, getHeight()/4);
		}
		
		g2.setColor(Color.white);
		g2.drawString("Score:" + SCORE, 10, 20);
	}
}