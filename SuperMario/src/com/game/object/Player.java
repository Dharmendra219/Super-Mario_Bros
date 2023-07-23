package com.game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.game.gfx.Animation;
import com.game.gfx.Texture;
import com.game.main.Game;
import com.game.object.util.Handler;
import com.game.object.util.ObjectId;

public class Player extends GameObject{
	private static final float WIDTH = 16;
	private static final float HEIGHT = 16;
	
	//for accessing objects
	private Handler handler;
	private Texture tex;
	
	//instance variables...
	private PlayerState state;
	private BufferedImage[] spriteL, spriteS;
	private Animation playerWalkL, playerWalkS;
	private BufferedImage[] currSprite;
	private Animation currAnimation;
	
	//this list will keep a track of hitted blocks by the mario..and pass this
	//list to the handler to remove the block..
	private LinkedList<Block> removeBlocks;
	
	private boolean jumped = false;
	//private int health = 2;
	private boolean forward = false;
	
	public Player (float x, float y, int scale, Handler handler) {
		super(x, y, ObjectId.Player, WIDTH, HEIGHT, scale);
		this.handler = handler;
		
		tex = Game.getTexture();
		removeBlocks = new LinkedList<>();
		spriteL = tex.getMarioL();
		spriteS = tex.getMarioS();	
		
		playerWalkL = new Animation(5, spriteL[1], spriteL[2], spriteL[3]);
		playerWalkS = new Animation(5, spriteS[1], spriteS[2], spriteS[3]);
		
		state = PlayerState.Small;
		currSprite = spriteS;
		currAnimation = playerWalkS;
	}

	@Override
	public void tick() {
		setX(getVelX() + getX());
		setY(getVelY() + getY());
		applyGravity();
		
		collision();
		
		//calling run method that be created as runAnimation...
		currAnimation.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		//representing the rectangle for player... as the health downs player becomes smaller...
		//animation of the player as jumping running etc...
		
		if(jumped) {
			if(forward) {
				g.drawImage(currSprite[5], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
			} 
			else {
				g.drawImage(currSprite[5], (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(), (int) getHeight(), null);
			}
		}
		else if(getVelX() > 0) {
			currAnimation.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
			forward = true;
		}
		else if(getVelX() < 0) {
			currAnimation.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(), (int) getHeight());
			forward = false;
		}
		else {
			g.drawImage(currSprite[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
		}
		
		//showBounds(g);
	}
	
	private void collision() {
		//get iterate through all game Objects...
		for(int i = 0; i < handler.getGameObjs().size(); i++) {
			GameObject temp = handler.getGameObjs().get(i);
			
			//checks that the collision object is our player..
			if(temp == this) continue;
			//this is way of not doing the collision ...
			if(removeBlocks.contains(temp))continue;
			
			//logic for collision detection...
			if(temp.getId() == ObjectId.Block && getBoundsTop().intersects(temp.getBounds())) {
				setY(temp.getY() + temp.getHeight());
				setVelY(0);
				((Block) temp).hit();
				removeBlocks.add((Block) temp);
			}
			
			else {
				if(getBounds().intersects(temp.getBounds())) {
					//when player is on the top of the object(block)...
					setY(temp.getY() - getHeight());
					setVelY(0);
					jumped = false;
				}
				
				//when the object(block) is on the top of the player....
				if(getBoundsTop().intersects(temp.getBounds())) {
					setY(temp.getY() + getHeight());
					setVelY(0);
				}
				// when the player object is on the left and intersecting with the object...
				if(getBoundsRight().intersects(temp.getBounds())) {
					setX(temp.getX() - getWidth());
				}
				
				//vice versa of the left...
				if(getBoundsLeft().intersects(temp.getBounds())) {
					setX(temp.getX() + getWidth());
				}
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)(getX() + getWidth()/2 - getWidth()/4),
				(int) (getY() + getHeight()/2),
				(int) getWidth()/2,
				(int) getHeight()/2);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle ((int)(getX() + getWidth()/2 - getWidth()/4),
				(int) getY() ,
				(int) getWidth()/2,
				(int) getHeight()/2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle ((int)(getX() + getWidth() - 5),
				(int) (getY() + 5),
				5,
				(int) (getHeight() - 10));
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle ((int)getX(),
				(int) (getY() + 5),
				5,
				(int) (getHeight() - 10));
	}
	
	public void showBounds(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.red);
		g2d.draw(getBounds());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsTop());
	}
	
	public boolean hasJumped() {
		return jumped;
	}
	
	public void setJumped(boolean hasJumped) {
		jumped = hasJumped;
	}
	
	public LinkedList<Block> getAndRemoveBlock(){
		LinkedList<Block> output = new LinkedList<Block>();
		
		for(Block removeBlock : removeBlocks) {
			if(!removeBlock.shouldRemove())continue;
			output.add(removeBlock);
		}
		
		for(Block removeBlock : output) {
			removeBlocks.remove(removeBlock);
		}
		
		return output;
	}
}
