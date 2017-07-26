package decimatepurge.game.module.modules.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class HealthCommandModule extends SimplePlayerCommandModule {

	public HealthCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	@Override
	protected void onCommand(Player player, Command command, String[] args) {
		if(args.length >= 1){
			try {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				player.sendMessage(ChatColor.GRAY + target.getName() + "'s health: " + ChatColor.YELLOW + ((int)Math.ceil(target.getHealth())));
			}catch(Exception ex){
				player.sendMessage(ChatColor.RED + "Player not found.");
			}
		}else{
			player.sendMessage(ChatColor.RED + "Invalid syntax. Try: " + ChatColor.YELLOW + "/health (player)");
		}
	}

	@Override
	public void loadCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadCommand() {
		// TODO Auto-generated method stub
		
	}

}
