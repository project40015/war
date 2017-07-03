package decimatepurge.game.module.modules;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.User;

public class CompassPointModule extends SimpleEventModule {

	public CompassPointModule(ModuleID id) {
		super(id);
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (event.getPlayer().getItemInHand().getType().equals(Material.COMPASS)) {
			User u = Purge.getInstance().getUserManager().getUser(event.getPlayer());
			double nearest = 500000;
			Player pl = null;
			for (User user : Purge.getInstance().getUserManager().getUsers()) {
				if (user.isOnline() && !user.isDead() && !user.isSpectating()) {
					if (!user.equals(u)) {
						if (user.getPlayer().getLocation().distance(event.getPlayer().getLocation()) < nearest) {
							pl = user.getPlayer();
							nearest = user.getPlayer().getLocation().distance(event.getPlayer().getLocation());
						}
					}
				}
			}
			if (pl != null) {
				u.sendActionbar(ChatColor.RED + "Compass pointing at " + pl.getName() + ChatColor.YELLOW + " ("
						+ (int) nearest + "m)");
				event.getPlayer().setCompassTarget(pl.getLocation());
			} else {
				u.sendActionbar(ChatColor.RED + "No players found");
			}
		}
	}

}
