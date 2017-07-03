package decimatepurge.game.module.modules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.User;

public class DefaultChatModule extends Module {

	public DefaultChatModule(ModuleID id) {
		super(id);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		String format = ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.GRAY + ": " + event.getMessage();
		User user = Purge.getInstance().getUserManager().getUser(event.getPlayer());
		if (!user.getFaction().equals("")) {
			format = ChatColor.GRAY + "[" + user.getFaction() + "] " + format;
		}
		event.setFormat(format);
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
