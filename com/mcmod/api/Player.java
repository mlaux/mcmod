package com.mcmod.api;


public class Player {
	private Worm worm;
	
	public Player(Object o) {
		this.worm = new Worm(o);
	}
	
	public Inventory getInventory() {
		return new Inventory(worm.get("Humanoid.inventory"));
	}
}
