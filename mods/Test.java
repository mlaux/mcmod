import java.awt.Color;

import com.mcmod.Loader;
import com.mcmod.api.DrawingHelper;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;


public class Test implements Mod {
	@Override
	public boolean isTogglable() {
		return true;
	}

	@Override
	public String getName() {
		return "Test";
	}

	@Override
	public String getDescription() {
		return "A test mod, for testing new fields.";
	}

	@Override
	public void enable() {

	}

	@Override
	public void disable() {

	}

	@Override
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
