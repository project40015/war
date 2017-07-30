package decimatepurge.game.module.modules;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.Division;
import decimatepurge.user.User;

public class DeathMessageModule extends SimpleEventModule {

	public DeathMessageModule(ModuleID id) {
		super(id);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		User dUser = Purge.getInstance().getUserManager().getUser(player);
		if (player.getKiller() != null) {
			User kUser = Purge.getInstance().getUserManager().getUser(event.getEntity().getKiller());
			event.setDeathMessage(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " (" + Division.getDivision(dUser.getElo()).getDisplay() + ChatColor.GRAY + ") was killed by "
					+ ChatColor.YELLOW + player.getKiller().getName() + ChatColor.GRAY + " (" + Division.getDivision(kUser.getElo()).getDisplay() + ChatColor.GRAY + ")!");
		} else {
			event.setDeathMessage(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " (" + Division.getDivision(dUser.getElo()).getDisplay() + ChatColor.GRAY + ") died!");
		}
	}

}
