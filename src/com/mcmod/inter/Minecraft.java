package com.mcmod.inter;

import java.net.URL;

public interface Minecraft {
	public Menu getCurrentMenu();
	
	public Font getFont();
	
	public PlayerInfo getPlayerInfo();
	
	public URL getURL();
	
	public Player getPlayer();

	public Item[] getItemCache();
	
	public void setItemCache(Item[] it); // wat use lol 
	
	public World getWorld();
	
	public CraftingManager getCraftingManager();
}
