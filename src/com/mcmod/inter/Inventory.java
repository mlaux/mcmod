package com.mcmod.inter;

public interface Inventory {
	/**
	 * @return the index that is currently selected. (the thing you select 
	 *          with your mouse wheel)
	 */
	public int getCurrentIndex();
	
	public InventoryItem[] getEquippableItems();
	
	public InventoryItem[] getInventoryItems();
}
