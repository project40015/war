package decimatepurge.game.module.modules.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.User;

public class SpectateCommandModule extends SimplePlayerCommandModule {

	public SpectateCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	@Override
	protected void onCommand(Player player, Command command, String[] args) {
		if (player.isOp()) {
			User user = Purge.getInstance().getUserManager().getUser(player);
			if (!user.isSpectating()) {
				user.setSpectating();
			}
		} else {
			player.sendMessage(super.noPermission());
		}
	}

	@Override
	public void loadCommand() {

	}

	@Override
	public void unloadCommand() {

	}

}
