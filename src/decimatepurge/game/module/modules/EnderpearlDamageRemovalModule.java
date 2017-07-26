package decimatepurge.game.module.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class EnderpearlDamageRemovalModule extends SimpleEventModule {

	public EnderpearlDamageRemovalModule(ModuleID id) {
		super(id);
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event){
		if(event.getCause().equals(TeleportCause.ENDER_PEARL)){
			event.setCancelled(true);
			event.getPlayer().teleport(event.getTo());
			event.getPlayer().setFallDistance(-4);
		}
	}

}
