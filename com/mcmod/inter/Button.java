package com.mcmod.inter;

public interface Button {
	public int getHeight();
	
	public int getX();
	
	public int getY();
	
	public int getWidth();
	
	public boolean containsPoint(Minecraft mc, int x, int y);
	
	public boolean isEnabled();
}
