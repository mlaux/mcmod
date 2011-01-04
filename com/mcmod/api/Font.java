package com.mcmod.api;

public class Font {
	private Worm worm;
	
	public Font(Object o) {
		worm = new Worm(o);
	}
	
	public void drawStringPlain(String s, int x, int y, int col) {
		worm.invoke("Font.drawStringPlain()", s, x, y, col);
	}
	
	public void drawStringShadow(String s, int x, int y, int col) {
		worm.invoke("Font.drawStringShadow()", s, x, y, col);
	}
}
