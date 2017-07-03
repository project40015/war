package decimatepurge.game.module.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Scoreboard;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.objects.Team;
import decimatepurge.user.User;

public class TeamModule extends Module {

	private Scoreboard scoreboard;

	private List<Team> teams = new ArrayList<>();
	private int teamChecker;

	public TeamModule(ModuleID id) {
		super(id);
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	}

	public boolean isTeam(String tag) {
		for (Team team : teams) {
			if (team.getName().equalsIgnoreCase(tag)) {
				return true;
			}
		}
		return false;
	}

	public Team getTeam(String tag) {
		for (Team team : teams) {
			if (team.getName().equalsIgnoreCase(tag)) {
				return team;
			}
		}
		return null;
	}

	private void split(List<User> users, int index) {
		if (index == users.size()) {
			for (Team team : this.teams) {
				team.load();
			}
			return;
		}
		if (users.get(index).getFaction().equals("")) {
			teams.add(new Team(this, Arrays.asList(users.get(index)), ""));
		} else if (isTeam(users.get(index).getFaction())) {
			getTeam(users.get(index).getFaction()).addUser(users.get(index));
		} else {
			teams.add(new Team(this, Arrays.asList(users.get(index)), users.get(index).getFaction()));
		}
		split(users, index + 1);
	}

	private void split(List<User> users) {
		split(users, 0);
	}

	public boolean areOnSameTeam(Player... players) {
		teamLoop: for (Team team : teams) {
			for (int p = 0; p < players.length; p++) {
				if (!team.containsPlayer(players[p])) {
					if (p == 0) {
						continue teamLoop;
					} else {
						return false;
					}
				}
				if (p == players.length - 1) {
					return true;
				}
			}
		}
		return false;
	}

	public Team getTeam(User user) {
		for (Team team : teams) {
			if (team.getUsers().contains(user)) {
				return team;
			}
		}
		return null;
	}

	public Team getTeam(Player player) {
		for (Team team : teams) {
			for (User user : team.getUsers()) {
				if (user.getPlayer().equals(player)) {
					return team;
				}
			}
		}
		return null;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public List<Team> getAliveTeams() {
		List<Team> alive = new ArrayList<>();
		for (Team team : teams) {
			if (!team.isOut()) {
				alive.add(team);
			}
		}
		return alive;
	}

	private void startTeamChecker() {
		teamChecker = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Purge.getInstance(), new Runnable() {

			@Override
			public void run() {
				for (Team team : getAliveTeams()) {
					team.updateObjective(false);
				}
			}

		}, 20, 20);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		for (User user : getTeam(event.getPlayer()).getAliveMembers()) {
			user.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "(TEAM) " + event.getPlayer().getName()
					+ ChatColor.GRAY + ": " + event.getMessage());
		}
		event.setCancelled(true);
	}

	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	private void createTeams() {
		teams.clear();
		split(Purge.getInstance().getUserManager().getUsers());
	}

	@Override
	public void load() {
		Bukkit.getPluginManager().registerEvents(this, Purge.getInstance());
		this.createTeams();
		startTeamChecker();
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
		Bukkit.getServer().getScheduler().cancelTask(this.teamChecker);

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
		}
	}

}
