package decimatepurge.game.module.modules.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.Rank;
import decimatepurge.user.User;

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

	@Override
	public void load() {
		loaded = true;
		loadCommand();
	}

	@Override
	public void unload() {
		loaded = false;
		unloadCommand();
	}
	
	public boolean hasPermission(Player player, String node, boolean inform){
		if(player.hasPermission(node)){
			return true;
		}
		if(inform){
			player.sendMessage(noPermission());
		}
		return false;
	}
	
	public boolean hasRank(User user, int rank, boolean inform){
		if(user.getRank().getId() >= rank){
			return true;
		}
		if(inform && user.getPlayer() != null){
			user.getPlayer().sendMessage(ChatColor.GRAY + "This command requires rank " + Rank.getRank(rank).getDisplay().trim() + ChatColor.GRAY + "!");
		}
		return false;
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
