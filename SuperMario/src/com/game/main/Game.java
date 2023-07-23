package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.game.gfx.Camera;
import com.game.gfx.Texture;
import com.game.gfx.Windows;
import com.game.main.util.LevelHandler;
import com.game.object.Block;
import com.game.object.Player;
import com.game.object.util.Handler;
import com.game.object.util.KeyInput;


public class Game extends Canvas implements Runnable {

	//Game Constants
	private static final int MILLIS_PER_SEC = 1000;
	private static final int NANOS_PER_SEC = 1000000000;
	private static final double NUM_TICKS = 60.0;
	private static final String NAME = "Super Mario";
			
	private static final int WINDOW_WIDTH = 960;
	private static final int WINDOW_HEIGHT = 720;
	private static final int SCREEN_WIDTH = WINDOW_WIDTH - 67;
	private static final int SCREEN_HEIGHT = WINDOW_HEIGHT;
	private static final int SCREEN_OFFSET = 16*3;
	
	//Game Variables
	private boolean running;
	
	//Game components
	private Thread thread;
	private Handler handler;
	private Camera cam;
	private static Texture tex;
	private LevelHandler levelHandler;
	
	
	public Game() {
		initialize();
	}
	 
	public static void main(String args[]) {
		new Game();
	}
	
	private void initialize() {
		tex = new Texture();
		
		handler  = new Handler();
		this.addKeyListener(new KeyInput(handler));
		
		levelHandler = new LevelHandler(handler);
		levelHandler.start();
		
		cam = new Camera(0, SCREEN_OFFSET);
		new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this);
		
		start();
	}
	
	private synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	private synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = NUM_TICKS;
		double ns = NANOS_PER_SEC / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;//the value will be 0-1...
			lastTime = now;
		
		
		while(delta >= 1) {
			tick();//it reminds that 1s is passed and time will be updated
			updates++;
			delta--;
		}
		
		if(running) {
			render();
			frames++;
		}
		
		
		if(System.currentTimeMillis() - timer > MILLIS_PER_SEC) {
			timer += MILLIS_PER_SEC;
			System.out.println("FPS: " + frames + " TPS: " + updates);
			updates = 0;
			frames = 0;
		}
	}
		
		stop();
	}
	
	private void tick() {
		handler.tick();
		cam.tick(handler.getPlayer());
	}
	
	private void render() {
		BufferStrategy buf = this.getBufferStrategy();
		if(buf == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		//draw graphics...
		Graphics g = buf.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);//Set the window to black
		
		g2d.translate(cam.getX(), cam.getY());
		handler.render(g);
		g2d.translate(-cam.getX(), -cam.getY());
		
		g.dispose();//cleans for next frame...
		buf.show();//set the next frame...
	}
	
	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}
	
	//get functions to excess private variables...
	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}
	
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	//getter function for texture...
	public static Texture getTexture() {
		return tex;
	}
}
