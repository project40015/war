package decimatepurge.game.module.modules.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.objects.Faction;
import decimatepurge.user.Rank;
import decimatepurge.user.User;

public class FactionCommandModule extends SimplePlayerCommandModule implements Listener {

	private List<Faction> factions = new ArrayList<Faction>();

	public FactionCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	private Faction getFaction(String name) {
		for (Faction faction : factions) {
			if (faction.getName().equalsIgnoreCase(name)) {
				return faction;
			}
		}
		return null;
	}

	public void leave(User user) {
		Faction faction = this.getFaction(user.getFaction());
		this.getFaction(user.getFaction()).removeUser(user);
		if (faction.isEmpty()) {
			this.factions.remove(faction);
		}
	}

	private void changeFaction(User user, String factionTag) {
		if (!user.getFaction().equals("")) {
			Faction old = getFaction(user.getFaction());
			if (old != null) {
				old.removeUser(user);
				if (old.isEmpty()) {
					this.factions.remove(old);
				}
			}
		}
		Faction faction = getFaction(factionTag);
		if (faction != null) {
			faction.addUser(user);
			user.setFaction(faction.getName());
		} else {
			if (!factionTag.equals("")) {
				this.factions.add(new Faction(factionTag, user));
			}
			user.setFaction(factionTag);
		}

	}

	private void listCommands(Player player) {
		player.sendMessage(ChatColor.RED + "Faction War" + ChatColor.GRAY + ":");
		send(player, "(show/who) [faction]", "List the players of a faction");
		send(player, "list", "List all online factions");
	}

	private void send(Player player, String command, String description) {
		player.sendMessage(
				ChatColor.RED + "/" + super.getCommand() + " " + command + ChatColor.GRAY + " - " + description);
	}

	private void displayFaction(Player player, String tag, boolean self) {
		if (!self) {
			try {
				Player p = Bukkit.getServer().getPlayer(tag);
				tag = Purge.getInstance().getUserManager().getUser(p).getFaction();
			} catch (Exception ex) {
			}
		}
		if (tag.equals("")) {
			player.sendMessage(ChatColor.RED + "User is not playing with a faction.");
			return;
		}
		List<User> users = new ArrayList<>();
		for (User user : Purge.getInstance().getUserManager().getUsers()) {
			if (user.isOnline() && !user.isDead() && user.getFaction().equalsIgnoreCase(tag)) {
				users.add(user);
			}
		}
		if (users.size() == 0) {
			player.sendMessage(ChatColor.RED + "Faction not found.");
			return;
		}
		player.sendMessage(
				ChatColor.GRAY + "Displaying [" + ChatColor.RED + tag + ChatColor.GRAY + "] (" + users.size() + "):");
		String online = "";
		for (int i = 0; i < users.size(); i++) {
			online += ChatColor.GRAY + users.get(i).getPlayer().getName() + ChatColor.YELLOW + " ("
					+ (int) player.getHealth() + ")";
			if (i != users.size() - 1) {
				online += ChatColor.GRAY + ", ";
			} else {
				online += ChatColor.GRAY + ".";
			}
		}
		player.sendMessage(online);
	}

	private void displayFactionList(Player player) {
		player.sendMessage(ChatColor.GRAY + "Online Factions:");
		int solo = 0;
		for (User user : Purge.getInstance().getUserManager().getUsers()) {
			if (user.getFaction().equals("") && user.isOnline()) {
				solo++;
			}
		}
		player.sendMessage(ChatColor.GRAY + "Solo players" + ChatColor.GRAY + ": " + ChatColor.YELLOW + solo);
		for (int i = 0; i < (factions.size() >= 10 ? 10 : factions.size()); i++) {
			if (factions.get(i).getAliveSize() >= 0) {
				player.sendMessage(ChatColor.RED + factions.get(i).getColoredName(player) + ChatColor.GRAY + ": "
						+ ChatColor.YELLOW + factions.get(i).getAliveSize());
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		User user = Purge.getInstance().getUserManager().getUserByUUID(event.getPlayer().getUniqueId().toString(), false);
		this.changeFaction(user, user.getFaction());
	}

	@Override
	protected void onCommand(Player player, Command command, String[] args) {
		User user = Purge.getInstance().getUserManager().getUser(player);
		if (args.length == 0) {
			listCommands(player);
			return;
		}
		if (args[0].equalsIgnoreCase("show") || args[0].equalsIgnoreCase("who")) {
			if (args.length == 1) {
				displayFaction(player, user.getFaction(), true);
			} else {
				displayFaction(player, args[1], false);
			}
			return;
		}
		if (args[0].equalsIgnoreCase("list")) {
			displayFactionList(player);
			return;
		}
		if (args[0].equalsIgnoreCase("change")) {
			if (super.hasRank(user, Rank.DEVELOPER.getId(), true)) {
				if (args.length >= 2) {
					this.changeFaction(user, args[1]);
					player.sendMessage(ChatColor.YELLOW + "Changed your faction.");
				} else {
					player.sendMessage(ChatColor.RED + "Invalid syntax, try /f change (tag)");
				}
			} else {
				player.sendMessage(super.noPermission());
			}
			return;
		}
	}

	@Override
	public void loadCommand() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Purge.getInstance());
		// Appears redundant, but this is necessary for loading faction wrappers
		for (User user : Purge.getInstance().getUserManager().getUsers()) {
			this.changeFaction(user, user.getFaction());
		}
	}

	@Override
	public void unloadCommand() {
		HandlerList.unregisterAll(this);
		this.factions.clear();
	}

}
