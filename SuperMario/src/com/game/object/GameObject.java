package com.game.object;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.object.util.ObjectId;

public abstract class GameObject {
	
	//x and y are the positions for the game object...
	private float x;
	private float y;
	
	//id of the object type...
	private ObjectId id;
	
	//velocity in x and y direction...
	private float velX, velY;
	
	//width and height of the object...how tall is the object...
	private float width, height;
	
	//scale in height and width...
	private int scale;
	
	public GameObject(float x, float y, ObjectId id, float width, float height, int scale) {
		this.x = x * scale;
		this.y = y * scale;
		this.id = id;
		this.width = width * scale;
		this.height = height * scale;
		this.scale = scale;
	}
	
	// now we creating some abstract methods for abstract class...
	public abstract void tick();//for any position update , any state update 
	public abstract void render(Graphics g);// for graphics update...display,animations..
	public abstract Rectangle getBounds();// define the bounding box for our game object...
	
	public void applyGravity() {
		velY += 0.5f;
	}
	
	//setter methods....
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public void setVelX(float velX) {
		this.velX = velX;
	}
	
	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public void setWidth(float width) {
		this.width = width * scale;
	}
	
	public void setHeight(float height) {
		this.height = height * scale;
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	// getter methods for all abstract methods..
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public ObjectId getId() {
		return id;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public int getScale() {
		return scale;
	}
}
