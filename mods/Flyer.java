import org.lwjgl.input.Keyboard;

import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;


public class Flyer implements Mod {
	@Override
	public boolean isTogglable() {
		return true;
	}

	@Override
	public String getName() {
		return "Flyer";
	}

	@Override
	public String getDescription() {
		return "Allows you to hold \"UP\" and fly.";
	}

	@Override
	public void enable() {

	}

	@Override
	public void disable() {

	}

	@Override
	public void render() {
		Keyboard.enableRepeatEvents(true);
		Player p = Loader.getMinecraft().getPlayer();
		
		if(p != null) {
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				p.setSpeedY(0.5f);
			}
			
			p.setFallDistance(0.0f);
		}
	}

}
