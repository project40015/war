package decimatepurge.game.module.modules.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import decimatepurge.core.Purge;
import decimatepurge.user.User;

public class Team {

	private List<User> members = new ArrayList<>();
	private boolean dead;
	private String name;
	private Scoreboard scoreboard;
	private org.bukkit.scoreboard.Team scoreboardTeam;
	private Objective objective;
	
	public Team(List<User> users, String name){
		members.addAll(users);
		this.name = name;
	}
	
	@SuppressWarnings("deprecation")
	public void load(){
		scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		
		scoreboardTeam = scoreboard.registerNewTeam(name);

		if(name.equals("")){
			scoreboardTeam.setPrefix(ChatColor.GRAY + "[" + name + "] " + ChatColor.GREEN.toString());
		}else{
			scoreboardTeam.setPrefix(ChatColor.GREEN.toString());
		}
		scoreboardTeam.setCanSeeFriendlyInvisibles(true);
		scoreboardTeam.setAllowFriendlyFire(false);
		scoreboardTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
		
		org.bukkit.scoreboard.Team enemyTeam = scoreboard.registerNewTeam("enemy");
		enemyTeam.setPrefix(ChatColor.RED.toString());
		enemyTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
		loadSideBoard();
		for(User user : Purge.getInstance().getUserManager().getUsers()){
			if(user.isOnline()){
				if(members.contains(user)){
					user.getPlayer().setScoreboard(scoreboard);
					scoreboardTeam.addPlayer(user.getPlayer());
				}else{
					enemyTeam.addPlayer(user.getPlayer());
				}
			}
		}
	}
	
	public void addUser(User user){
		this.members.add(user);
	}
	
	public List<User> getAliveMembers(){
		List<User> result = new ArrayList<User>();
		for(User user : members){
			if(user.isOnline() && !user.isDead()){
				result.add(user);
			}
		}
		return result;
	}
	
	private void updateObjective(){
		for(Score score : scoreboard.getScores("Scoreboard")){
			scoreboard.resetScores(score.getEntry());
		}
		int n = 15;
		objective.getScore(" ").setScore(--n);
		objective.getScore(ChatColor.GRAY + "Faction:").setScore(--n);
		objective.getScore(ChatColor.RED + name).setScore(--n);
		objective.getScore("  ").setScore(--n);
		objective.getScore(ChatColor.GRAY + "Members:").setScore(--n);
		if(getAliveMembers().size() > 3){
			objective.getScore(ChatColor.RED.toString() + members.size()).setScore(--n);
		}else{
			for(User user : getAliveMembers()){
				objective.getScore(ChatColor.RED + user.getPlayer().getName()).setScore(--n);
			}
		}
		objective.getScore("   ").setScore(--n);
		objective.getScore(ChatColor.GRAY.toString() + ChatColor.ITALIC + "decimatepvp").setScore(--n);
	}
	
	private void loadSideBoard(){
		objective = scoreboard.registerNewObjective("Scoreboard", "dummy");
		objective.setDisplayName(ChatColor.RED + "Faction War");

		updateObjective();
		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public org.bukkit.scoreboard.Team getScoreboardTeam(){
		return this.scoreboardTeam;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isOut(){
		return dead;
	}
	
	public void recalc(){
		this.updateObjective();
		for(User user : members){
			if(!user.isDead()){
				return;
			}
		}
		dead = true;
	}
	
	public List<User> getUsers(){
		return members;
	}
	
	public boolean containsPlayer(Player player){
		for(User user : members){
			if(user.getPlayer().equals(player)){
				return true;
			}
		}
		return false;
	}
	
}
