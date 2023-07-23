package com.game.object.util;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import com.game.object.Block;
import com.game.object.GameObject;
import com.game.object.Player;

public class Handler {
	private List<GameObject> gameObjs;
	private Player player;
	
	public Handler() {
		gameObjs = new LinkedList<GameObject>();
	}
		
	public void tick() {
		for(GameObject obj : gameObjs) {
			obj.tick();
		}
		
		//removing the blocks
		LinkedList<Block> removeBlocks = player.getAndRemoveBlock();
		for(Block removeBlock : removeBlocks) {
			removeObj(removeBlock);
		}
	}
	
	public void render(Graphics g) {
		for(GameObject obj : gameObjs) {
			obj.render(g);
		}
	}
	
	//to add objects....
	public void addObj(GameObject obj) {
		gameObjs.add(obj);
	}
	
	//to remove objects...
	public void removeObj(GameObject obj) {
		gameObjs.remove(obj);
	}
	
	//getter method to return the list of objects...
	public List<GameObject> getGameObjs(){
		return gameObjs;
	}
	
	//it will check our player is set yet or no...
	public int setPlayer(Player player) {
		if(this.player != null) {
			return -1;
		}
		
		addObj(player);
		this.player = player;
		return 0;
	}
	
	public int removePlayer() {
		if(player == null)return -1;
		
		removeObj(player);
		this.player = null;
		return 0;
	}
	
	public Player getPlayer() {
		return player;
	}
}
