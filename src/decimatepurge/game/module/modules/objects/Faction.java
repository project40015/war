package decimatepurge.game.module.modules.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import decimatepurge.user.User;

public class Faction {

	private String name;
	private List<User> users = new ArrayList<>();

	public Faction(String name, User... users) {
		this.name = name;
		for (User user : users) {
			this.users.add(user);
		}
	}

	public Faction(String name, User user) {
		this.name = name;
		this.users.add(user);
	}

	public void removeUser(User user) {
		if (this.users.contains(user)) {
			this.users.remove(user);
		}
	}

	public String getColoredName(Player player) {
		for (User user : this.users) {
			if (user.getPlayer().equals(player)) {
				return ChatColor.GREEN + name;
			}
		}
		return ChatColor.RED + name;
	}

	public void addUser(User user) {
		if (!this.users.contains(user)) {
			this.users.add(user);
		}
	}

	public int getAliveSize() {
		int aliveSize = 0;
		for (User user : this.users) {
			if (!user.isDead() && user.isOnline()) {
				aliveSize++;
			}
		}
		return aliveSize;
	}

	public String getName() {
		return name;
	}

	public List<User> getUsers() {
		return users;
	}

	public boolean isEmpty() {
		for (User user : this.users) {
			if (!user.isDead() && user.isOnline() && !user.isOff()) {
				return false;
			}
		}
		return true;
	}

}
