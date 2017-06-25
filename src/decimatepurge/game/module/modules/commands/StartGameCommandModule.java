package decimatepurge.game.module.modules.commands;

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
		if(arg0.isOp()){
			arg0.sendMessage(ChatColor.BLUE + "Starting the game...");
			Purge.getInstance().getGameStageManager().getCurrentStage().finish();
		}else{
			arg0.sendMessage(super.noPermission());
		}
	}

}
