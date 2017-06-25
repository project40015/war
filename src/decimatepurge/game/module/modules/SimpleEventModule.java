package decimatepurge.game.module.modules;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;

public abstract class SimpleEventModule extends Module {

	public SimpleEventModule(ModuleID id, ModuleID...modules) {
		super(id, modules);
	}
	
	public SimpleEventModule(ModuleID id) {
		super(id);
	}

	@Override
	protected void load() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Purge.getInstance());
	}

	@Override
	protected void unload() {
		HandlerList.unregisterAll(this);
	}

}
