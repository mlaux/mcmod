
import com.mcmod.api.InventoryAPI;
import com.mcmod.api.Mod;

public class ClearInventory implements Mod {

	public boolean isTogglable() {
		return false;
	}

	public String getName() {
		return "Clear Inventory";
	}

	public String getDescription() {
		return "Removes all items from your inventory.";
	}

	public void process() {
		InventoryAPI.clearInventory();
	}
}
