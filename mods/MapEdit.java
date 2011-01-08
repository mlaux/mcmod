import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;


public class MapEdit implements Mod {

	@Override
	public boolean isTogglable() {
		return false;
	}

	@Override
	public String getName() {
		return "MapEdit";
	}

	@Override
	public String getDescription() {
		return "Add a block to the map.";
	}

	@Override
	public void process() {
		Player p = Loader.getMinecraft().getPlayer();
		
		if(p != null) {
			int myX = (int) p.getX();
			int myY = (int) p.getY();
			int myZ = (int) p.getZ();
			
			int upperLeftX = myX - 15;
			int upperLeftZ = myZ - 15;
			
			int lowerRightX = myX + 15;
			int lowerRightZ = myZ + 15;
			
			for(int x = -15; x < 15; x++) {
				for(int z = -15; z < 15; z++) {
					Loader.getMinecraft().getWorld().setBlock(myX + x, myY + 5, myZ + z, 46);
				}
			}
		}
	}
}
