package decimatepurge.game.module.modules.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import decimatepurge.game.module.ModuleManager.ModuleID;

public abstract class SimplePlayerCommandModule extends SimpleCommandModule {

	public SimplePlayerCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	protected abstract void onCommand(Player player, Command command, String[] args);

	@Override
	public void onSuccessfulCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			onCommand((Player) arg0, arg1, arg3);
		} else {
			arg0.sendMessage(ChatColor.RED + "You must be a player to use this command.");
		}
	}

}
