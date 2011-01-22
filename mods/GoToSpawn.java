
import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;
import com.mcmod.inter.World;

public class GoToSpawn implements Mod {

	
	public boolean isTogglable() {
		return false;
	}

	
	public String getName() {
		return "Go To Spawn";
	}

	
	public String getDescription() {
		return "Teleports you to your spawn.";
	}

	
	public void process() {
		Player p = Loader.getMinecraft().getPlayer();
		World w = Loader.getMinecraft().getWorld();

		if (p != null && w != null) {
			p.setPosition(w.getSpawnX(), w.getSpawnY(), w.getSpawnZ());
		}
	}
}
