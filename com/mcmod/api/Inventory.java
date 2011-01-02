package com.mcmod.api;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private Worm worm;
	
	public Inventory(Object o) {
		this.worm = new Worm(o);
	}
	
	public List<InventoryItem> getInventoryItems() {
		List<InventoryItem> items = new ArrayList<InventoryItem>();
		
		Object[] objects = (Object[]) worm.get("Inventory.inventoryItems");
		
		for(Object o : objects) {
			if(o != null) {
				items.add(new  InventoryItem(o));
			}
		}
		
		return items;
	}
	
	public List<InventoryItem> getEquippableItems() {
		List<InventoryItem> items = new ArrayList<InventoryItem>();
		
		Object[] objects = (Object[]) worm.get("Inventory.equippableItems");
		
		for(Object o : objects) {
			if(o != null) {
				items.add(new  InventoryItem(o));
			}
		}
		
		return items;
	}
	
	public int getEmptySlot() {
		Object[] objects = (Object[]) worm.get("Inventory.inventoryItems");
		
		for(int x = 0; x < objects.length; x++) {
			if(objects[x] == null) {
				return x;
			}
		}
		
		return -1;
	}
	
	public void addItem(int id, int count) {
		try {
			Class<?> itemClass = StaticWorm.getClass("InventoryItem");
			Constructor itemConstructor = itemClass.getConstructor(Integer.TYPE, Integer.TYPE);
			Object item = itemConstructor.newInstance(id, count);
			
			Object[] objects = (Object[]) worm.get("Inventory.inventoryItems");
			
			int x = getEmptySlot();
			objects[x] = item;
			
			worm.set("Inventory.inventoryItems", objects);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
