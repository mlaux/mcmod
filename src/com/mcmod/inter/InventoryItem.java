package com.mcmod.inter;

public interface InventoryItem {
	/**
	 * @return how damaged the item is.
	 */
	public int getDamage();
	
	/**
	 * @return how many of the item you have in a stack.
	 */
	public int getCount();
	
	/**
	 * @return the ID of the item.
	 */
	public int getId();
}
