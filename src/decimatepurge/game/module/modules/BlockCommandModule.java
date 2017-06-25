package decimatepurge.game.module.modules;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class BlockCommandModule extends SimpleEventModule {

	public BlockCommandModule(ModuleID id) {
		super(id);
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event){
		String command = "";
		if(event.getMessage().contains(" ")){
			command = event.getMessage().split(" ")[0];
		}
		if(command.startsWith("/")){
			command = command.substring(1);
		}
		if(command.contains(":")){
			event.getPlayer().sendMessage(ChatColor.RED + "You cannot use this.");
			event.setCancelled(true);
			return;
		}
		if(command.startsWith("me")){
			event.getPlayer().sendMessage(ChatColor.RED + "You cannot use this.");
			event.setCancelled(true);
			return;
		}
	}
	
}
