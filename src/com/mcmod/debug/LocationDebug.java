package com.mcmod.debug;

import com.mcmod.Loader;
import com.mcmod.api.DrawingHelper;
import com.mcmod.inter.Player;

public class LocationDebug extends McDebug {
	
	public String getName() {
		return "Location";
	}
	
	
	public void render() {
		Player p = Loader.getMinecraft().getPlayer();
		
		if(p != null) {
			DrawingHelper.drawShadowString("X: " + p.getX(), 30, 30, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("Y: " + p.getY(), 30, 45, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("Z: " + p.getZ(), 30, 60, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("SpeedX: " + p.getSpeedX(), 30, 75, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("SpeedY: " + p.getSpeedY(), 30, 90, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("SpeedZ: " + p.getSpeedZ(), 30, 105, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("RotX: " + p.getRotationX(), 30, 120, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("RotY: " + p.getRotationY(), 30, 135, 0xFFFFFFFF);
		}
	}
}
