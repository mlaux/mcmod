import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;


public class SpeedHack implements Mod {

	@Override
	public boolean isTogglable() {
		return true;
	}

	@Override
	public String getName() {
		return "Speed Hack";
	}

	@Override
	public String getDescription() {
		return "Makes you move 2x faster.";
	}

	@Override
	public void enable() {
		
	}

	@Override
	public void disable() {
	
	}

	@Override
	public void render() {
		Player player = Loader.getMinecraft().getPlayer();
		
		if(player != null) {
			double speedX = player.getSpeedX();
			double speedZ = player.getSpeedZ();
			player.setSpeedX(speedX + (speedX < 0 ? -0.1 : 0.1));
			player.setSpeedZ(speedZ + (speedZ < 0 ? -0.1 : 0.1));
		}
	}
	
}
