
import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;

public class MapEdit implements Mod {

	
	public boolean isTogglable() {
		return false;
	}

	
	public String getName() {
		return "MapEdit";
	}

	
	public String getDescription() {
		return "Add a block to the map.";
	}

	
	public void process() {
		Player p = Loader.getMinecraft().getPlayer();

		if (p != null) {
			int myX = (int) p.getX();
			int myY = (int) p.getY();
			int myZ = (int) p.getZ();

			for (int y = -2; y > -30; y--) {
				for (int x = -2; x < 2; x++) {
					for (int z = -2; z < 2; z++) {
						try {
							Loader.getMinecraft().getWorld().setBlock(myX + x, myY + y, myZ + z, 1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
