package decimatepurge.game.module.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class NoDamageModule extends SimpleEventModule {

	public NoDamageModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event){
		event.setCancelled(true);
	}

	@EventHandler
	public void onHungerLoss(FoodLevelChangeEvent event){
		event.setCancelled(true);
	}
	
}
