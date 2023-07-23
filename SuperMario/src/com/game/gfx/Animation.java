package com.game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
	
	private int speed;
	private int frames;
	
	private int index = 0;
	private int count = 0;
	
	private BufferedImage[] images;
	private BufferedImage currentImg;
	
	public Animation(int speed, BufferedImage... args) {// taking in number of buffered images...
		this.speed = speed;
		images = new BufferedImage[args.length];
		
		for(int i = 0; i < args.length; i++) {
			images[i] = args[i];
		}
		
		frames = args.length;
	}
	
	//allows us to set speed and run through animations...
	public void runAnimation() {
		index++;
		if(index > speed) {
			index = 0;
			nextFrame();
		}
	}
	
	private void nextFrame() {
		currentImg = images[count];
		count++;
		
		if(count >= frames) {
			count = 0;
		}
	}
	
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY) {
		g.drawImage(currentImg, x, y, scaleX, scaleY, null);
	}
}
