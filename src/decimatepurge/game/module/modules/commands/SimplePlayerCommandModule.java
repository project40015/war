package decimatepurge.game.module.modules.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.Rank;

public abstract class SimplePlayerCommandModule extends SimpleCommandModule {

	private Rank rank;
	
	public SimplePlayerCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	public void setRank(Rank rank){
		this.rank = rank;
	}
	
	protected abstract void onCommand(Player player, Command command, String[] args);

	@Override
	public void onSuccessfulCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			if(rank == null || super.hasRank(Purge.getInstance().getUserManager().getUser((Player) arg0), rank.getId(), true)){
				onCommand((Player) arg0, arg1, arg3);
			}
		} else {
			arg0.sendMessage(ChatColor.RED + "You must be a player to use this command.");
		}
	}

}
