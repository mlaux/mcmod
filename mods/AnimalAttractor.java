
import java.util.List;
import java.util.Random;

import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Animable;
import com.mcmod.inter.Humanoid;
import com.mcmod.inter.Player;
import com.mcmod.inter.World;

public class AnimalAttractor implements Mod {

	private Random r = new Random();

	
	public boolean isTogglable() {
		return false;
	}

	
	public String getName() {
		return "Animal Attractor";
	}

	
	public String getDescription() {
		return "Moves all of the monsters to your location.";
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

						a.setPosition(player.getX() + r.nextInt(6) + 3, player.getY(), player.getZ() + r.nextInt(6) + 3);
					}
				}
			}
		}
	}
}
