package decimatepurge.game.module.modules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;

public class JoinServerModule extends Module {

	ItemStack air = new ItemStack(Material.AIR);
	
	public JoinServerModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		event.getPlayer().getInventory().clear();
		event.getPlayer().getInventory().setHelmet(air);
		event.getPlayer().getInventory().setChestplate(air);
		event.getPlayer().getInventory().setLeggings(air);
		event.getPlayer().getInventory().setBoots(air);
		event.getPlayer().setHealth(20);
		event.getPlayer().setFoodLevel(20);
		event.getPlayer().sendMessage("");
		event.getPlayer().sendMessage(ChatColor.RED + "You have entered a faction war.");
		event.getPlayer().sendMessage("");
		event.getPlayer().setGameMode(GameMode.SURVIVAL);
		event.setJoinMessage("");
	}

	@Override
	public void load() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Purge.getInstance());
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}

}
