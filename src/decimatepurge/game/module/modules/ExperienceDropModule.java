package decimatepurge.game.module.modules;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class ExperienceDropModule extends SimpleEventModule {

	public ExperienceDropModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		event.getDrops().add(new ItemStack(Material.EXP_BOTTLE, 24));
	}

}
