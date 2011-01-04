package com.mcmod.api;

import java.util.ArrayList;
import java.util.List;


public class Minecraft {
	private Worm worm;
	
	public Minecraft(Object o) {
		this.worm = new Worm(o);
	}
	
	public Player getPlayer() {
		return new Player(worm.get("Minecraft.player"));
	}
	
	public List<Item> getItemCache() {
		Object[] objects = (Object[]) StaticWorm.get("Item.itemCache");
		List<Item> items = new ArrayList<Item>();
		
		for(int x = 0; x < objects.length; x++) {
			if(objects[x] != null) {
				items.add(new Item(objects[x]));
			}
		}
		
		return items;
	}
	
	public Font getFont() {
		return new Font(worm.get("Minecraft.font"));
	}
}
