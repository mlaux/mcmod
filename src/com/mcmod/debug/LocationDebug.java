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
			f.drawStringShadow("SpeedX: " + p.getSpeedX(), 30, 75, 0xFFFFFFFF);
			f.drawStringShadow("SpeedY: " + p.getSpeedY(), 30, 90, 0xFFFFFFFF);
			f.drawStringShadow("SpeedZ: " + p.getSpeedZ(), 30, 105, 0xFFFFFFFF);
			f.drawStringShadow("RotX: " + p.getRotationX(), 30, 120, 0xFFFFFFFF);
			f.drawStringShadow("RotY: " + p.getRotationY(), 30, 135, 0xFFFFFFFF);
		}
	}
}
