package decimatepurge.game.module.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.User;

public class MapModule extends Module {

	World world = Purge.getInstance().getWorld();
	private int border = 1000;
	private final int borderPrime = 5;
	private int tBorder = (border - borderPrime);

	public MapModule(ModuleID id) {
		super(id);
	}

	public void teleport(Player player) {
		player.teleport(getRandomLocation());
		player.setFallDistance(-100);
	}

	private Location getRandomLocation() {
		int x = (int) (2 * Math.random() * tBorder) - tBorder;
		int z = (int) (2 * Math.random() * tBorder) - tBorder;
		int y = Bukkit.getServer().getWorld("war_world").getHighestBlockYAt(x, z);

		Location location = new Location(Bukkit.getServer().getWorld("war_world"), x + 0.5, y, z + 0.5);

		return isSafe(location) ? location : getRandomLocation();
	}

	private boolean isSafe(Location location) {
		return location == null
				|| location
				.clone()
				.add(0, -1, 0)
				.getBlock() == null ||
				!location.clone().add(0, -1, 0)
				.getBlock()
				.isLiquid();
	}

	@Override
	public void load() {
		if (super.getArguments().length > 0 && super.getArguments()[0] instanceof Integer) {
			border = (Integer) super.getArguments()[0];
		}
		tBorder = (border - borderPrime);
		if (super.getArguments().length > 1 && super.getArguments()[1] instanceof Boolean) {
			if ((Boolean) super.getArguments()[1]) {
				for (User user : Purge.getInstance().getUserManager().getUsers()) {
					teleport(user.getPlayer());
				}
			}
		}
		
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "worldborder set " + border*2);
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "worldborder set 32 3600");
	}

	@Override
	public void unload() {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "worldborder set " + border*2);
	}

}
