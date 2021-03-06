
import org.lwjgl.input.Keyboard;

import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;

public class Flyer implements Mod {

	
	public boolean isTogglable() {
		return true;
	}

	
	public String getName() {
		return "Flyer";
	}

	
	public String getDescription() {
		return "Allows you to hold \"SPACE\" and fly.";
	}

	
	public void process() {
		Keyboard.enableRepeatEvents(true);
		Player p = Loader.getMinecraft().getPlayer();

		if (p != null) {
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				p.setSpeedY(0.5d);
				p.setFallDistance(0.0f);
			} else {
				if (p.getOnGround() == false) {
					if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
						p.setSpeedY(0d);
					}
					p.setFallDistance(0.0f);
				}
			}

		}
	}
}
