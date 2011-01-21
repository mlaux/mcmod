import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;
import com.mcmod.inter.World;

public class SetLocationAsSpawn implements Mod {
	public boolean isTogglable() {
		return false;
	}

	public String getName() {
		return "Set Location As Spawn";
	}

	public String getDescription() {
		return "Sets your current location as your spawn point.";
	}

	public void process() {
		Player p = Loader.getMinecraft().getPlayer();
		World w = Loader.getMinecraft().getWorld();

		if (p != null && w != null) {
			w.setSpawnX((int) p.getX());
			w.setSpawnY((int) p.getY());
			w.setSpawnZ((int) p.getZ());
		}
	}
}