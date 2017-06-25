package decimatepurge.game.module.modules;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class DeathMessageModule extends SimpleEventModule {

	public DeathMessageModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		Player player = event.getEntity();
		if(player.getKiller() != null){
			event.setDeathMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + player.getKiller().getPlayerListName() + ChatColor.GRAY + "!");
		}else{
			event.setDeathMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " died!");
		}
	}

}
