
import java.awt.Color;

import com.mcmod.Loader;
import com.mcmod.api.DrawingHelper;
import com.mcmod.api.Mod;
import com.mcmod.inter.World;

public class TimeManager implements Mod {

    public boolean isTogglable() {
        return true;
    }

    public String getName() {
        return "Always Sunny";
    }

    public String getDescription() {
        return "Makes it so it's never night.";
    }

    public void process() {
        World w = Loader.getMinecraft().getWorld();

        if (w != null) {
            long time = w.getTime();

            DrawingHelper.drawShadowString("[" + (time % 24000) + "]", 30, 30, Color.WHITE.getRGB());

            if ((time % 24000) >= 10000) {
                w.setTime(time + 16000);
            }
        }
    }
}
