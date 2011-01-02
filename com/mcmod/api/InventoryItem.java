package com.mcmod.api;

public class InventoryItem {
	private Worm worm;
	
	public InventoryItem(Object o) {
		this.worm = new Worm(o);
	}
	
	public int getCount() {
		return worm.getInt("InventoryItem.count");
	}
	
	public int getId() {
		return worm.getInt("InventoryItem.id");
	}
	
	public int getDamage() {
		return worm.getInt("InventoryItem.damage");
	}
	
	public void setCount(int count) {
		worm.setInt("InventoryItem.count", count);
	}
	
	public void setId(int id) {
		worm.setInt("InventoryItem.id", id);
	}
	
	public void setDamage(int damage) {
		worm.setInt("InventoryItem.damage", damage);
	}
}
