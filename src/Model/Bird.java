package Model;

import java.awt.Rectangle;

import Controller.BirdActionListener;
import Main.Game;


public class Bird extends Objects {
	
	private float v;
	
	private boolean isFlying;
	private boolean isLive;
	private Rectangle rect;

	//listener
	private BirdActionListener listener;

	public Bird(int x, int y, int w, int h) {
		super(x, y, w, h);
		initComponent(x,y,w,h);
	}

	private void initComponent(int x , int y, int w, int h){
		v = 0;
		isFlying = false;
		isLive = true;
		rect = new Rectangle(x, y, w, h);
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void update(long deltatime) {
		v += Game.g;
		
		this.setPosY(this.getPosY() + v);
		this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());
		
		if(v<0) isFlying = true;
		else isFlying = false;
	}
	
	public void fly() {
		v = -4;		//do cao chim bay

		if(listener != null)
			listener.onBirdFlap();
	}
	
	public boolean getIsFlying() {
		return isFlying;
	}
	
	public void setLive(boolean b) {	
		if(isLive && !b && listener != null)
			listener.onBirdCollide();

		isLive = b;
	}
	
	public boolean getLive() {
		return isLive;
	}
	
	public void setV(float v) {
		this.v = v;	
	}

	public void setBirdActionListener(BirdActionListener listener){
		this.listener = listener;
	}
}