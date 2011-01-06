package com.mcmod.api;

import com.mcmod.inter.Minecraft;

public interface Mod {
	public String getName();
	
	public String getDescription();
	
	public void enable(Minecraft mc);
	
	public void disable();
}
