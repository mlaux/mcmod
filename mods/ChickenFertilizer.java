import java.util.List;

import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Chicken;
import com.mcmod.inter.Player;
import com.mcmod.inter.World;


public class ChickenFertilizer implements Mod {
	@Override
	public boolean isTogglable() {
		return true;
	}

	@Override
	public String getName() {
		return "Chicken Fertilizer";
	}

	@Override
	public String getDescription() {
		return "Teleports all the chickens to you and makes them lay eggs -- rapidly.";
	}

	@Override
	public void enable() {
		
	}

	@Override
	public void disable() {
		
	}

	@Override
	public void render() {
		World world = Loader.getMinecraft().getWorld();
		Player me = Loader.getMinecraft().getPlayer();
		
		if(world != null) {
			List entities = world.getEntityList();
			
			for(Object o : entities) {
				if(Chicken.class.isAssignableFrom(o.getClass())) {
					Chicken c = (Chicken) o;
					
					c.setPosition(me.getX() + 1, me.getY(), me.getZ() + 1);
					
					if(c.getEggTimer() > 3)
						c.setEggTimer(2);
				}
			}
		}
	}
}
