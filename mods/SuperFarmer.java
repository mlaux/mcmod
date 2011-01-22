
import java.util.List;

import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Animable;
import com.mcmod.inter.Humanoid;
import com.mcmod.inter.Player;
import com.mcmod.inter.World;

public class SuperFarmer implements Mod {

	
	public boolean isTogglable() {
		return true;
	}

	
	public String getName() {
		return "Super Farmer";
	}

	
	public String getDescription() {
		return "Teleports all the entities in the area in front of you and sets their health to 1";
	}

	
	public void process() {
		World world = Loader.getMinecraft().getWorld();
		Player player = Loader.getMinecraft().getPlayer();

		if (world != null) {
			@SuppressWarnings("rawtypes")
			List entities = world.getEntityList();

			for (Object o : entities) {
				if (!Humanoid.class.isAssignableFrom(o.getClass())) {
					if (Animable.class.isAssignableFrom(o.getClass())) {
						Animable a = (Animable) o;

						a.setPosition(player.getX() + 1, player.getY(), player.getZ() + 1);

						if (a.getHealth() > 1);
						a.setHealth(1);
					}
				}
			}
		}
	}
}
