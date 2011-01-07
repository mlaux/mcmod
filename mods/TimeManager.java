import java.awt.Color;

import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Font;
import com.mcmod.inter.World;

public class TimeManager implements Mod {
	public boolean isTogglable() {
		return true;
	}
	
	public String getName() {
		return "Always Sunny";
	}

	public String getDescription() {
		return "Makes it so it's never night.";
	}
	
	public void enable() {
	}
	
	public void disable() {
	}
	
	public void render() {
		World w = Loader.getMinecraft().getWorld();
		Font f = Loader.getMinecraft().getFont();
		
		if(w != null) {
			long time = w.getTime();
			
			f.drawStringShadow("[" + (time % 24000) + "]", 30, 30, Color.WHITE.getRGB());
		
			if((time % 24000) >= 10000) {
				w.setTime(time + 16000);
			}
		}
	}
}
