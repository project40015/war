package decimatepurge.user;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import decimate.WarSocket.ServerInformationPacketResultEvent;
import decimate.WarSocket.WarSocket;
import decimatepurge.core.Manager;
import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.NoConnectionModule;

public class UserManager implements Manager {

	private List<User> users = new ArrayList<>();

	private User addUser(String uuid) {
		removeUser(uuid);
		NoConnectionModule module = (NoConnectionModule) Purge.getInstance().getGameStageManager().getCurrentStage()
				.getLoadedModule(ModuleID.NO_CONNECTION_MODULE);
		if (module != null) {
			if (module.getAllowSpectators()) {
				User user = new User(uuid);
				this.users.add(user);
				user.setSpectating();
				return user;
			}
		}
		User user = new User(uuid);
		this.users.add(user);
		return user;
	}
	
	public User createUserByUUID(String id){
		for(User user : users){
			if(user.getPlayer().getUniqueId().toString().equalsIgnoreCase(id)){
				return user;
			}
		}
		return addUser(id);
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

	public void removeUser(String uuid) {
		User user = getUserByUUID(uuid);
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
		getUserByUUID(event.getPlayer().getUniqueId().toString()).loadPlayer(event.getPlayer());
	}
	
	private void requestUser(User user){
		WarSocket.getInstance().emitServerInformationPacketRequest(user.getPlayer().getUniqueId().toString());
	}
	
	@EventHandler
	public void onResult(ServerInformationPacketResultEvent event){
		User user = Purge.getInstance().getUserManager().getUserByUUID(event.getUUID()); 
		user.setFaction(event.getFaction());
		Rank rank = Rank.valueOf(event.getRank());
		user.loadRank(rank == null ? Rank.DEFAULT : rank);
		this.requestUser(user);
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
