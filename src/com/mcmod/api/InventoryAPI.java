package com.mcmod.api;

import com.mcmod.inter.Inventory;
import com.mcmod.inter.InventoryItem;
import com.mcmod.inter.Item;

public class InventoryAPI {
	private InventoryAPI() { 
		
	}
	
	public static boolean addItem(Inventory in, int id, int count) {
		int spot = -1;
		
		InventoryItem[] it = in.getInventoryItems();
		for(int k = 0; k < it.length; k++)
			if(it[k] == null)
				spot = k;
		
		if(spot == -1)
			return false;
		
		Object ii = StaticWorm.instantiate("InventoryItem", new Class<?>[] { int.class, int.class }, id, count);
		it[spot] = (InventoryItem) ii;
		return true;
	}

	public static Item itemFromName(Item[] cache, String item) {
		for(int x = 0; x < cache.length; x++) {
			if(cache[x] != null) {
				String name = cache[x].getName();
				
				if(name != null && name.toLowerCase().contains(item.toLowerCase()))
					return cache[x];
			}
		}
		return null;
	}
}
