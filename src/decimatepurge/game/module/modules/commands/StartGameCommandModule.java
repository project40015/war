package decimatepurge.game.module.modules.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;

public class StartGameCommandModule extends SimpleCommandModule {

	public StartGameCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	@Override
	public void onSuccessfulCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0.isOp()) {
			arg0.sendMessage(ChatColor.BLUE + "Starting the game...");
			Bukkit.broadcastMessage(ChatColor.YELLOW + arg0.getName() + " has forced the game to start.");
			Purge.getInstance().getGameStageManager().getCurrentStage().finish();
		} else {
			arg0.sendMessage(super.noPermission());
		}
	}

	@Override
	public void loadCommand() {

	}

	@Override
	public void unloadCommand() {

	}

}
