package decimatepurge.game.module.modules.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.Rank;

public class InventoryReloadCommandModule extends SimplePlayerCommandModule {

	private List<String> used = new ArrayList<>();
	
	public InventoryReloadCommandModule(ModuleID id, String command) {
		super(id, command);
		super.setRank(Rank.YOUTUBER);
	}

	@Override
	protected void onCommand(Player player, Command command, String[] args) {
		if(!this.used.contains(player.getUniqueId().toString())){
			this.used.add(player.getUniqueId().toString());
		}
	}

	@Override
	public void loadCommand() {
		
	}

	@Override
	public void unloadCommand() {
		
	}

}
