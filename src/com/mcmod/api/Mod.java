package com.mcmod.api;


public interface Mod {
	public boolean isTogglable();
	
	public String getName();
	
	public String getDescription();

	public void enable();
	
	public void disable();
	
	public void render();
}
