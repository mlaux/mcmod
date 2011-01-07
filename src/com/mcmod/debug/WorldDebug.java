package com.mcmod.debug;

import com.mcmod.Loader;
import com.mcmod.inter.Font;
import com.mcmod.inter.World;

public class WorldDebug extends McDebug {
	@Override
	public String getName() {
		return "Spawn";
	}

	@Override
	public void render() {
		Font f = Loader.getMinecraft().getFont();
		World w = Loader.getMinecraft().getWorld();
		
		if(w != null) {
			f.drawStringShadow("SpawnX: " + w.getSpawnX(), 30, 75, 0xFFFFFFFF);
			f.drawStringShadow("SpawnY: " + w.getSpawnY(), 30, 90, 0xFFFFFFFF);
			f.drawStringShadow("SpawnZ: " + w.getSpawnZ(), 30, 105, 0xFFFFFFFF);
			f.drawStringShadow("Time: " + w.getTime(), 30, 120, 0xFFFFFFFF);
		}
	}
}
