package com.mcmod.debug;

import java.awt.Color;

import com.mcmod.Loader;
import com.mcmod.api.DrawingHelper;
import com.mcmod.inter.Player;

public class PlayerDebug extends McDebug {
	public String getName() {
		return "Player";
	}

	public void render() {
		Player p = Loader.getMinecraft().getPlayer();
		
		if(p != null) {
			DrawingHelper.drawShadowString("Fall Distance: " + p.getFallDistance(), 30, 30, Color.WHITE.getRGB());
			DrawingHelper.drawShadowString("AirTimer: " + p.getAirTimer(), 30, 45, Color.WHITE.getRGB());
			DrawingHelper.drawShadowString("FireTimer: " + p.getFireTimer(), 30, 60, Color.WHITE.getRGB());
			DrawingHelper.drawShadowString("OnGround: " + p.getOnGround(), 30, 75, Color.WHITE.getRGB());
		}
	}

}
