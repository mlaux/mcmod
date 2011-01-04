package com.mcmod.api;

public class Button {
	private Worm worm;
	
	public Button(Object o) {
		this.worm = new Worm(o);
	}
	
	public int getX() {
		return worm.getInt("Button.x");
	}
	
	public int getY() {
		return worm.getInt("Button.y");
	}
	
	public int getWidth() {
		return worm.getInt("Button.width");
	}
	
	public int getHeight() {
		return worm.getInt("Button.height");
	}
	
	public boolean isEnabled() {
		return worm.getBoolean("Button.enabled");
	}
	
	public boolean containsPoint(int x, int y) {
		return (Boolean) worm.invoke("Button.containsPoint()", null, x, y); // It's fine for the first paramter to be null, because it's unusued.
	}
}
