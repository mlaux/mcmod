
import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;

public class Invincible implements Mod {

	
	public boolean isTogglable() {
		return true;
	}

	
	public String getName() {
		return "Invincible";
	}

	
	public String getDescription() {
		return "You'll no longer be hurt by anything.";
	}

	
	public void process() {
		Player p = Loader.getMinecraft().getPlayer();

		if (p != null) {
			if (p.getAirTimer() < 300) {
				p.setAirTimer(300);
			}

			if (p.getFireTimer() != -20) {
				p.setFireTimer(-20);
			}

			if (p.getHealth() < 20) {
				p.setHealth(20);
			}

			if (p.getFallDistance() != 0.0f) {
				p.setFallDistance(0.0f);
			}
		}
	}
}
