package decimatepurge.game.module.modules.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.objects.StatisticsPage;
import decimatepurge.user.User;

public class UserStatisticsCommandModule extends SimplePlayerCommandModule {

	public UserStatisticsCommandModule(ModuleID id, String command) {
		super(id, command);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event){
		if(event.getInventory().getTitle().startsWith(ChatColor.GRAY + "Statistics: ")){
			event.setCancelled(true);
		}
	}
	
	@Override
	protected void onCommand(Player player, Command command, String[] args) {
		if(args.length == 0){
			new StatisticsPage(player).display(player);
		}else{
			User user = Purge.getInstance().getUserManager().getUser(args[0]);
			if(user != null){
				new StatisticsPage(user).display(player);
			}else{
				player.sendMessage(ChatColor.YELLOW + "Loading statistics from database...");
				new StatisticsPage(player, args[0]);
			}
		}
	}

	@Override
	public void loadCommand() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Purge.getInstance());
	}

	@Override
	public void unloadCommand() {
		HandlerList.unregisterAll(this);
	}

}
