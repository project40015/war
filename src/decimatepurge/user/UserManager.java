package decimatepurge.user;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import decimatepurge.core.Manager;

public class UserManager implements Manager {

	private List<User> users = new ArrayList<>();
	
	private void addUser(Player player){
		User u = getUser(player);
		if(u != null){
			users.remove(u);
		}
		this.users.add(new User(player, ""));
	}
	
	public void removeUser(Player player){
		User user = getUser(player);
		if(user != null){
			if(users.contains(user)){
				users.remove(user);
			}
		}
	}
	
	public void broadcastActionBar(String message){
		for(User user : this.users){
			user.sendActionbar(message);
		}
	}
	
	public List<User> getUsers(){
		return users;
	}
	
	public User getUser(Player player){
		for(User user : users){
			if(user.getPlayer().equals(player)){
				return user;
			}
		}
		return null;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		addUser(event.getPlayer());
	}

	@Override
	public void onDisable() {
		
	}
	
	
}
