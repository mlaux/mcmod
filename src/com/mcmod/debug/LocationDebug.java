package com.mcmod.debug;

import com.mcmod.Loader;
import com.mcmod.inter.Font;
import com.mcmod.inter.Player;

public class LocationDebug extends McDebug {
	@Override
	public String getName() {
		return "Location";
	}
	
	@Override
	public void render() {
		Font f = Loader.getMinecraft().getFont();
		Player p = Loader.getMinecraft().getPlayer();
		
		if(p != null) {
			f.drawStringShadow("X: " + p.getX(), 30, 30, 0xFFFFFFFF);
			f.drawStringShadow("Y: " + p.getY(), 30, 45, 0xFFFFFFFF);
			f.drawStringShadow("Z: " + p.getZ(), 30, 60, 0xFFFFFFFF);
		}
	}
}
