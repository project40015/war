package decimatepurge.game.module.modules.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.User;

public class ShoutCommandModule extends SimplePlayerCommandModule {

	private List<String> shouted = new ArrayList<String>();

	public ShoutCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	@Override
	protected void onCommand(Player player, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Invalid syntax, try /" + super.getCommand() + " (message)");
		}
		if (!shouted.contains(player.getUniqueId().toString())) {
			User user = Purge.getInstance().getUserManager().getUser(player);
			if (user.isDead()) {
				player.sendMessage(ChatColor.RED + "You must be alive to use this command.");
				return;
			}
			String message = "";
			for (int i = 0; i < args.length; i++) {
				message += args[i] + " ";
			}
			Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[SHOUT] " + ChatColor.GRAY + "[" + user.getFaction() + "] "
					+ user.getFullName() + ChatColor.GRAY + ": " + message.trim());
			shouted.add(player.getUniqueId().toString());
		} else {
			player.sendMessage(ChatColor.RED + "You have already shouted this game.");
		}
	}

	@Override
	public void loadCommand() {

	}

	@Override
	public void unloadCommand() {

	}

}
