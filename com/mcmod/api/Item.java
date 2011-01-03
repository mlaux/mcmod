package com.mcmod.api;

import java.lang.reflect.Field;

public class Item {
	private Worm worm;
	private Object o;
	
	public Item(Object o) {
		this.worm = new Worm(o);
		this.o = o;
	}
	
	public int getId() {
		return worm.getInt("Item.id");
	}
	
	public String getName() {
		for(Field f : o.getClass().getDeclaredFields()) {
			try {
				f.setAccessible(true);
				System.out.println(f.getName() + ": " + f.get(o));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//String s = (String) worm.get("Item.name");
		return "Unknown"; // s.substring(s.indexOf(".") + 1);
	}
}
