package decimatepurge.game.module.modules.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;

public class HubCommandModule extends SimplePlayerCommandModule {

	public HubCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	@Override
	protected void onCommand(Player player, Command command, String[] args) {
		player.sendMessage(ChatColor.RED + "You left the war...");
		Purge.getInstance().getBungeeManager().send(player, "lobby");
	}

	@Override
	public void loadCommand() {

	}

	@Override
	public void unloadCommand() {

	}

}
