package decimatepurge.user;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import decimatepurge.core.Manager;
import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.NoConnectionModule;

public class UserManager implements Manager {

	private List<User> users = new ArrayList<>();

	private User addUser(Player player) {
		removeUser(player);
		NoConnectionModule module = (NoConnectionModule) Purge.getInstance().getGameStageManager().getCurrentStage()
				.getLoadedModule(ModuleID.NO_CONNECTION_MODULE);
		if (module != null) {
			if (module.getAllowSpectators()) {
				User user = new User(player);
				this.users.add(user);
				return user;
			}
		}
		User user = new User(player, "");
		this.users.add(user);
		return user;
	}
	
	public User getUserByUUID(String id){
		for(User user : users){
			if(user.isOnline() && !user.isOff()){
				if(user.getPlayer().getUniqueId().toString().equalsIgnoreCase(id)){
					return user;
				}
			}
		}
		return null;
	}

	public void removeUser(Player player) {
		User user = getUser(player);
		if (user != null) {
			if (users.contains(user)) {
				users.remove(user);
			}
		}
	}

	public void broadcastActionBar(String message) {
		for (User user : this.users) {
			user.sendActionbar(message);
		}
	}

	public List<User> getUsers() {
		return users;
	}

	public User getUser(Player player) {
		for (User user : users) {
			if (user.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) {
				return user;
			}
		}
		return null;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		User user = addUser(event.getPlayer());
		Purge.getInstance().getBungeeManager().requestUserInformation(user);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		User user = getUser(event.getPlayer());
		if (user != null) {
			user.turnOff();
		}
	}

	@Override
	public void onDisable() {

	}

}
