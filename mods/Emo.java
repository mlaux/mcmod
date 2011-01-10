import com.mcmod.Loader;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;
 
 
public class Emo implements Mod {
 
    @Override
    public boolean isTogglable() {
        return false;
    }
 
    @Override
    public String getName() {
        return "Kill yourself";
    }
 
    @Override
    public String getDescription() {
        return "Sets your HP to 0.";
    }
 
    @Override
    public void process() {
        Player p = Loader.getMinecraft().getPlayer();
         
        if(p != null) {
            p.setHealth(0);
        }
    }
}