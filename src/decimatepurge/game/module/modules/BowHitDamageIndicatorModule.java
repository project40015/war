package decimatepurge.game.module.modules;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class BowHitDamageIndicatorModule extends SimpleEventModule {

	public BowHitDamageIndicatorModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		if(event.isCancelled()){
			return;
		}
		if(event.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player){
				Player player = (Player) arrow.getShooter();
				if(event.getEntity() instanceof Player){
					Player target = (Player) event.getEntity();
					player.sendMessage(ChatColor.GRAY + "Health of " + target.getName() + ": " + ChatColor.RED + target.getHealth());
				}
			}
		}
	}

}
