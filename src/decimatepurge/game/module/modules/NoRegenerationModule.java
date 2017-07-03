package decimatepurge.game.module.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class NoRegenerationModule extends SimpleEventModule {

	public NoRegenerationModule(ModuleID id) {
		super(id);
	}

	@EventHandler
	public void onRegen(EntityRegainHealthEvent event) {
		if (event.getRegainReason().equals(RegainReason.SATIATED)) {
			event.setCancelled(true);
		}
	}

}
