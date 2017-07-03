package decimatepurge.game.module.modules.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;

public abstract class SimpleCommandModule extends Module implements CommandExecutor {

	private String command;
	private boolean loaded = false;

	public SimpleCommandModule(ModuleID id, String command) {
		super(id);
		Purge.getInstance().getCommand(command).setExecutor(this);
		this.command = command;
	}

	protected String getCommand() {
		return command;
	}

	protected String noPermission() {
		return ChatColor.RED + "You do not have permission to use this command!";
	}

	public void load() {
		loaded = true;
		loadCommand();
	}

	public void unload() {
		loaded = false;
		unloadCommand();
	}

	public abstract void loadCommand();

	public abstract void unloadCommand();

	public abstract void onSuccessfulCommand(CommandSender sender, Command command, String commandLabel, String[] args);

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (loaded) {
			onSuccessfulCommand(arg0, arg1, arg2, arg3);
		} else {
			arg0.sendMessage(ChatColor.RED + "This command is currently not available.");
		}
		return false;
	}

}
