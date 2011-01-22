
import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;

public class Emo implements Mod {

	
	public boolean isTogglable() {
		return false;
	}

	
	public String getName() {
		return "Kill yourself";
	}

	
	public String getDescription() {
		return "Sets your HP to 0.";
	}

	
	public void process() {
		Player p = Loader.getMinecraft().getPlayer();

		if (p != null) {
			p.setHealth(0);
		}
	}
}
