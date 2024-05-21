package Model;

import java.awt.Rectangle;

public class Chimney extends Objects{

	private Rectangle rect;

	private boolean isBehindBird = false;

	public Chimney(int x, int y, int w, int h) {
		super(x, y, w, h);

		rect = new Rectangle(x, y, w, h);
	}

	public void update() {

		this.setPosX(this.getPosX() - 2);
		this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());

	}

	public Rectangle getRect() {
		return rect;
	}

	public void setIsBehindBird(boolean b) {
		isBehindBird = b;
	}

	public boolean getIsBehindBird() {
		return isBehindBird;
	}

}