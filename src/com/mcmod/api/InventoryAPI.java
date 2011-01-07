package com.mcmod.api;

import com.mcmod.inter.Inventory;
import com.mcmod.inter.InventoryItem;

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
}
