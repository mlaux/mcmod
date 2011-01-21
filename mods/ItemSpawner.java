
import javax.swing.JOptionPane;

import com.mcmod.Loader;
import com.mcmod.api.InventoryAPI;
import com.mcmod.api.Mod;
import com.mcmod.inter.Player;

public class ItemSpawner implements Mod {

    public boolean isTogglable() {
        return false;
    }

    public String getName() {
        return "Item Spawner...";
    }

    public String getDescription() {
        return "Adds items by name or ID to your inventory.";
    }

    public void process() {
        Player p = Loader.getMinecraft().getPlayer();

        if (p != null) {
            String info = JOptionPane.showInputDialog("Item: \"itemid [amount]\"");

            if (info != null) {
                try {
                    // TODO: figure out why item.getName() doesn't work anymore.

                    String[] data = info.split(" ");

                    int amount = 1;
                    int id = Integer.parseInt(data[0]);

                    if (data.length > 1) {
                        amount = Integer.parseInt(data[1]);
                    }

                    InventoryAPI.addItem(id, amount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
