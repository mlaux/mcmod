package com.mcmod.inter;

public interface Inventory {
	public int getCurrentIndex();
	
	public InventoryItem[] getEquippableItems();
	
	public InventoryItem[] getInventoryItems();
}
