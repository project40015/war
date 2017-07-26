package decimatepurge.game.module.modules;

import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class BlockAnimalSpawnModule extends SimpleEventModule {

	public BlockAnimalSpawnModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onSpawn(EntitySpawnEvent event){
		if(event.getEntity() instanceof Creature){
			event.setCancelled(true);
		}
	}

}
