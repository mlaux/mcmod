package com.mcmod.api;

import com.mcmod.Loader;
import com.mcmod.inter.Inventory;
import com.mcmod.inter.InventoryItem;

public class InventoryAPI {
	private InventoryAPI() { 
		
	}
	
	/**
	 * Adds 'count' amount of the specified item ID to the inventory
	 * of the local player.
	 * @param id Item ID to add
	 * @param count How many of the item to add
	 * @return 'true' if the items were added, 'false' if the inventory
	 * was full.
	 */
	public static boolean addItem(int id, int count) {
		Inventory in = Loader.getMinecraft().getPlayer().getInventory();
		int spot = -1;
		
		InventoryItem[] it = in.getInventoryItems();
		for(int k = 0; k < it.length; k++)
			if(it[k] == null)
				spot = k;
		
		if(spot == -1)
			return false;
		
		Object ii = StaticWorm.instantiate("InventoryItem", new Class<?>[] { int.class, int.class, int.class }, id, count, 0);
		it[spot] = (InventoryItem) ii;
		return true;
	}
	
	/**
	 * Removes all items from the local player's inventory.
	 */
	public static void clearInventory() {
		Inventory in = Loader.getMinecraft().getPlayer().getInventory();
		InventoryItem[] it = in.getInventoryItems();
		
		for(int k = 0; k < it.length; k++)
			it[k] = null;
	}
}
