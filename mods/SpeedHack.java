
import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Minecraft;
import com.mcmod.inter.Player;

import static org.lwjgl.input.Keyboard.*;

public class SpeedHack implements Mod {

	
	public boolean isTogglable() {
		return true;
	}

	
	public String getName() {
		return "Speed Hack";
	}

	
	public String getDescription() {
		return "Makes you move 2x faster.";
	}

	public void process() {
		Minecraft mc = Loader.getMinecraft();
		Player player = mc.getPlayer();

		if (player != null && activate()) {
			double sX = player.getSpeedX();
			double sZ = player.getSpeedZ();
			float r = player.getRotationY();

			double d = isKeyDown(KEY_W) ? 0.40000000000000002D : -0.40000000000000002D;

			if (isKeyDown(KEY_D)) {
				r -= 90.0d;
			} else if (isKeyDown(KEY_A)) {
				r += 90.0d;
			}

			sX += d * -mc.sin((r / 180.0f) * 3.14159265f);
			sZ += d * mc.cos((r / 180.0f) * 3.14159265f);

			player.setSpeedX(sX);
			player.setSpeedZ(sZ);
		}
	}

	private boolean activate() {
		return isKeyDown(KEY_W) || isKeyDown(KEY_A) || isKeyDown(KEY_S)
				|| isKeyDown(KEY_D);
	}
}
