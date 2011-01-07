import com.mcmod.api.Mod;

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
	
	public void enable() {}
	public void disable() {}

	public void render() {
	}
}
