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

	private boolean allowSpectators = false;
	private String[] allowedSpectators = { "62687bed-fb7b-45b1-ae00-6305f5291172" };

	public NoConnectionModule(ModuleID id) {
		super(id);
	}

	/**
	 * @return True if users with permission are allowed to join while this
	 *         module is loaded as spectators.
	 */
	public boolean getAllowSpectators() {
		return allowSpectators;
	}

	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent event) {
		if (allowSpectators) {
			for (String str : allowedSpectators) {
				if (str.equals(event.getUniqueId().toString())) {
					return;
				}
			}
		}
		event.disallow(Result.KICK_OTHER, ChatColor.RED + "Game currently in progress");
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}

	@Override
	protected void load() {
		if (super.getArguments().length > 0) {
			if (super.getArguments()[0] instanceof Boolean) {
				allowSpectators = (Boolean) super.getArguments()[0];
			}
		}
		Bukkit.getPluginManager().registerEvents(this, Purge.getInstance());
	}

}
