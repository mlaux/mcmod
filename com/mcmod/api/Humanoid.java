package com.mcmod.api;

public class Humanoid extends PlayerEntity {
	private Worm worm;
	
	public Humanoid(Object o) {
		super(o);
		this.worm = new Worm(o);
	}
	
	public Inventory getInventory() {
		return new Inventory(worm.get("Humanoid.inventory"));
	}
}
