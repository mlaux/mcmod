package com.mcmod.debug;

import com.mcmod.Loader;
import com.mcmod.api.DrawingHelper;
import com.mcmod.inter.World;

public class WorldDebug extends McDebug {

	public String getName() {
		return "Spawn";
	}

	public void render() {
		World w = Loader.getMinecraft().getWorld();

		if (w != null) {
			DrawingHelper.drawShadowString("SpawnX: " + w.getSpawnX(), 30, 75, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("SpawnY: " + w.getSpawnY(), 30, 90, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("SpawnZ: " + w.getSpawnZ(), 30, 105, 0xFFFFFFFF);
			DrawingHelper.drawShadowString("Time: " + w.getTime(), 30, 120, 0xFFFFFFFF);
		}
	}
}
