package decimatepurge.game.module.modules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;

public class NoConnectionModule extends Module {

	public NoConnectionModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent event){
		event.disallow(Result.KICK_OTHER, ChatColor.RED + "Game currently in progress");
	}
	
	@Override
	public void load() {
		Bukkit.getPluginManager().registerEvents(this, Purge.getInstance());
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}

}
