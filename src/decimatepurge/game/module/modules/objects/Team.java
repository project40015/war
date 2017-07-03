package decimatepurge.game.module.modules.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import decimatepurge.core.Purge;
import decimatepurge.game.module.modules.TeamModule;
import decimatepurge.user.User;

public class Team {

	private List<String> scores = new ArrayList<>();
	private List<User> members = new ArrayList<>();
	private boolean dead;
	private String name;
	private Scoreboard scoreboard;
	private org.bukkit.scoreboard.Team scoreboardTeam;
	private Objective objective;
	private TeamModule module;

	public Team(TeamModule module, List<User> users, String name) {
		this.module = module;
		members.addAll(users);
		this.name = name;
	}

	@SuppressWarnings("deprecation")
	public void load() {
		scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

		scoreboardTeam = scoreboard.registerNewTeam(name);

		if (!name.equals("")) {
			scoreboardTeam.setPrefix(ChatColor.GRAY + "[" + name + "] " + ChatColor.GREEN.toString());
		} else {
			scoreboardTeam.setPrefix(ChatColor.GREEN.toString());
		}
		scoreboardTeam.setCanSeeFriendlyInvisibles(true);
		scoreboardTeam.setAllowFriendlyFire(false);
		scoreboardTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);

		org.bukkit.scoreboard.Team enemyTeam = scoreboard.registerNewTeam("enemy");
		enemyTeam.setPrefix(ChatColor.RED.toString());
		enemyTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
		loadSideBoard();
		for (User user : Purge.getInstance().getUserManager().getUsers()) {
			if (user.isOnline()) {
				if (members.contains(user)) {
					user.getPlayer().setScoreboard(scoreboard);
					scoreboardTeam.addPlayer(user.getPlayer());
				} else {
					enemyTeam.addPlayer(user.getPlayer());
				}
			}
		}
	}

	public void addUser(User user) {
		this.members.add(user);
	}

	public List<User> getAliveMembers() {
		List<User> result = new ArrayList<User>();
		for (User user : members) {
			if (user.isOnline() && !user.isDead()) {
				result.add(user);
			}
		}
		return result;
	}

	public void updateObjective(boolean firstLoad) {
		if (dead) {
			return;
		}
		for (String score : scores) {
			scoreboard.resetScores(score);
		}
		int n = 15;
		addScore(" ", --n, firstLoad);
		addScore(ChatColor.GRAY + "Faction:", --n, firstLoad);
		addScore(ChatColor.RED + (name.equals("") ? "-" : name), --n, firstLoad);
		addScore("  ", --n, firstLoad);
		addScore(ChatColor.GRAY + "Members:", --n, firstLoad);
		if (getAliveMembers().size() > 3) {
			addLoadingScore(ChatColor.RED.toString() + getAliveMembers().size(), --n);
		} else {
			for (User user : getAliveMembers()) {
				addLoadingScore(ChatColor.RED + user.getPlayer().getName(), --n);
			}
		}
		addScore("   ", --n, firstLoad);
		addScore(ChatColor.GRAY + "Factions:", --n, firstLoad);
		addLoadingScore(ChatColor.RED.toString() + module.getAliveTeams().size(), --n);
		addScore("    ", --n, firstLoad);
		addScore(ChatColor.GRAY.toString() + ChatColor.ITALIC + "decimatepvp", --n, firstLoad);
	}

	private void addLoadingScore(String score, int number) {
		this.scores.add(score);
		objective.getScore(score).setScore(number);
	}

	private void addScore(String score, int number, boolean firstLoad) {
		if (firstLoad) {
			objective.getScore(score).setScore(number);
		}
	}

	private void loadSideBoard() {
		objective = scoreboard.registerNewObjective("Scoreboard", "dummy");
		objective.setDisplayName(ChatColor.RED + "Faction War");

		updateObjective(true);

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public org.bukkit.scoreboard.Team getScoreboardTeam() {
		return this.scoreboardTeam;
	}

	public String getName() {
		return name;
	}

	public boolean isOut() {
		return dead;
	}

	public void recalc() {
		this.updateObjective(false);
		boolean dead = true;
		for (User user : members) {
			if (!user.isDead()) {
				dead = false;
			} else if (user.isOnline()) {
				user.getPlayer().setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
			}
		}
		this.dead = dead;
	}

	public List<User> getUsers() {
		return members;
	}

	public boolean containsPlayer(Player player) {
		for (User user : members) {
			if (user.getPlayer().equals(player)) {
				return true;
			}
		}
		return false;
	}

}
