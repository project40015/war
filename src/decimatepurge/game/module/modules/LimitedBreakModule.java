package decimatepurge.game.module.modules;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class LimitedBreakModule extends SimpleEventModule {

	public LimitedBreakModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event){
		if(!event.getBlock().getType().equals(Material.COBBLESTONE) &&
				!event.getBlock().getType().equals(Material.LEAVES) &&
				!event.getBlock().getType().equals(Material.LEAVES_2) &&
				!event.getBlock().getType().equals(Material.YELLOW_FLOWER) &&
				!event.getBlock().getType().equals(Material.RED_ROSE) &&
				!event.getBlock().getType().equals(Material.LONG_GRASS) &&
				!event.getBlock().getType().equals(Material.DEAD_BUSH) &&
				!event.getBlock().getType().equals(Material.WEB)){
			if(event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)){
				event.getPlayer().sendMessage(ChatColor.RED + "You cannot break this block.");
				event.setCancelled(true);
			}
		}
	}

}
