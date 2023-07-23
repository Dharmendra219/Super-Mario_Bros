package com.game.object;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.gfx.Texture;
import com.game.main.Game;

public class Debris {
	
	private Texture tex = Game.getTexture();
	private BufferedImage[] debris;
	private int scale;
	private float width, height, velX, velY;
	private float[] x, y;
	
	public Debris(float x, float y, float width, float height, int scale) {
		this.x = new float[4];
		this.y = new float[4];
		
		this.x[0] = (float) (x - (0.5 * width));
		this.x[1] = (float) (x - (0.5 * width));
		this.x[2] = (float) (x + (0.5 * width));
		this.x[3] = (float) (x + (0.5 * width));
		
		this.y[0] = (float) (y + (0.5 * height));
		this.y[1] = (float) (y - (0.5 * height));
		this.y[2] = (float) (y + (0.5 * height));
		this.y[3] = (float) (y - (0.5 * height));
		
		this.width = width/2;
		this.height = height/2;
		debris = tex.getDebris1();
		
		velX = 2f;
		velY = -7f;
	}
	
	//apply gravity to the debris...
	public void applyGravity() {
		velY += 0.5f;
	}
	
	public void tick() {
		//scattering the debris with some velocity...
		x[0] = -velX + x[0];
		x[1] = -velX + x[1];
		x[2] = velX + x[2];
		x[3] = velX + x[3];
		
		y[0] = velY + y[0];
		y[1] = (float) (velY + y[1] - 2);
		y[2] = velY + y[2];
		y[3] = (float) (velY + y[3] - 2);
		
		applyGravity();
		
	}
	
	//removing the brick when the mario hit it..
	public boolean shouldRemove() {
		if(y[1] > Game.getWindowHeight()) {
			return true;
		}
		
		return false;
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < 4; i++) {
			g.drawImage(debris[i], (int) x[i], (int) y[i], (int) width, (int) height, null);
		}
	}
}
