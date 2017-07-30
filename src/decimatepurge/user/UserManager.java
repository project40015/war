package decimatepurge.user;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import decimate.WarSocket.ServerInformationPacketResultEvent;
import decimate.WarSocket.WarSocket;
import decimatepurge.core.Manager;
import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.NoConnectionModule;

public class UserManager implements Manager {

	private List<User> users = new ArrayList<>();

	@SuppressWarnings("deprecation")
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
		Bukkit.getScheduler().runTaskAsynchronously(Purge.getInstance(), new UserLoadDataTask(user));
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
	
	public User getUserByUUID(String id, boolean offlineCheck){
		for(User user : users){
			if(!offlineCheck || (user.isOnline() && !user.isOff())){
				if(user.getUniqueId().equalsIgnoreCase(id)){
					return user;
				}
			}
		}
		return null;
	}

	public void removeUser(String uuid) {
		User user = getUserByUUID(uuid, false);
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
			if (user.getUniqueId().equals(player.getUniqueId().toString())) {
				return user;
			}
		}
		return null;
	}
	
	public User getUser(String player) {
		for (User user : users) {
			if (user.getName().equalsIgnoreCase(player)) {
				return user;
			}
		}
		return null;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		User user = this.getUser(event.getEntity());
		if(event.getEntity().getKiller() != null){
			User kUser = this.getUser(event.getEntity().getKiller());
			user.death(kUser);
			kUser.killUser(user);
		}else{
			user.death();
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		for(PotionEffect pe : event.getPlayer().getActivePotionEffects()){
			event.getPlayer().removePotionEffect(pe.getType());
		}
		event.getPlayer().teleport(new Location(Bukkit.getWorld("war_world"), 0, Bukkit.getWorld("war_world").getHighestBlockYAt(0, 0), 0));
		getUserByUUID(event.getPlayer().getUniqueId().toString(), false).loadPlayer(event.getPlayer());
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event){
		if(getUserByUUID(event.getUniqueId().toString(), false) == null){
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "You may not join this server directly.");
		}
	}
	
	private void requestUser(User user){
		WarSocket.getInstance().emitServerInformationPacketRequest(user.getUniqueId());
	}
	
	@EventHandler
	public void onResult(ServerInformationPacketResultEvent event){
		if(!Purge.getInstance().getGameStageManager().getCurrentStage().getLoadedModules().contains(ModuleID.NO_CONNECTION_MODULE)){
			User user = this.addUser(event.getUUID());
			user.setFaction(event.getFaction());
			Rank rank = Rank.valueOf(event.getRank());
			user.loadRank(rank == null ? Rank.DEFAULT : rank);
			this.requestUser(user);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		User user = getUser(event.getPlayer());
		Bukkit.getScheduler().runTaskAsynchronously(Purge.getInstance(), new UserPushDataTask(user));
		if (user != null) {
			user.turnOff();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		for(User user : this.users){
			Bukkit.getScheduler().runTaskAsynchronously(Purge.getInstance(), new UserPushDataTask(user));
		}
	}

}
