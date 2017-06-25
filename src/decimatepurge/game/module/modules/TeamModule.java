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
//	private int teamSize = 2;
	
	public TeamModule(ModuleID id){
		super(id);
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	}
	
	public boolean isTeam(String tag){
		for(Team team : teams){
			if(team.getName().equalsIgnoreCase(tag)){
				return true;
			}
		}
		return false;
	}
	
	public Team getTeam(String tag){
		for(Team team : teams){
			if(team.getName().equalsIgnoreCase(tag)){
				return team;
			}
		}
		return null;
	}
	
	private void split(List<User> users, int index){
		if(index == users.size()){
			for(Team team : this.teams){
				team.load();
			}
			return;
		}
		if(users.get(index).getFaction().equals("")){
			teams.add(new Team(Arrays.asList(users.get(index)), ""));
		}else if(isTeam(users.get(index).getFaction())){
			getTeam(users.get(index).getFaction()).addUser(users.get(index));
		}else{
			teams.add(new Team(Arrays.asList(users.get(index)), users.get(index).getFaction()));
		}
		split(users, index + 1);
	}
	
	private void split(List<User> users){
		split(users, 0);
	}
	
//	private void split(List<User> users, int index){
//		if(users.size() == 0){
//			return;
//		}
//		if(users.size() <= index + teamSize){
//			List<User> members = new ArrayList<>();
//			for(int i = index; i < users.size(); i++){
//				members.add(users.get(i));
//			}
//			teams.add(new Team(this, members, index + ""));
//			return;
//		}
//		List<User> members = new ArrayList<>();
//		for(int q = index; q < index + teamSize; q++){
//			members.add(users.get(q));
//		}
//		teams.add(new Team(this, members, index + ""));
//		
//		split(users, index + teamSize);
//	}
	
	public boolean areOnSameTeam(Player...players){
		teamLoop: for(Team team : teams){
			for(int p = 0; p < players.length; p++){
				if(!team.containsPlayer(players[p])){
					if(p == 0){
						continue teamLoop;
					}else{
						return false;
					}
				}
				if(p == players.length - 1){
					return true;
				}
			}
		}
		return false;
	}
	
	public Team getTeam(User user){
		for(Team team : teams){
			if(team.getUsers().contains(user)){
				return team;
			}
		}
		return null;
	}
	
	public Team getTeam(Player player){
		for(Team team : teams){
			for(User user : team.getUsers()){
				if(user.getPlayer().equals(player)){
					return team;
				}
			}
		}
		return null;
	}
	
	public List<Team> getTeams(){
		return teams;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		for(User user : getTeam(event.getPlayer()).getAliveMembers()){
			user.getPlayer().sendMessage(ChatColor.BLUE + "(FACTION) " + event.getPlayer().getName() + ChatColor.GRAY + ": " + event.getMessage());
		}
		event.setCancelled(true);
	}
	
//	@EventHandler
//	public void onDamage(EntityDamageByEntityEvent event){
//		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
//			if(areOnSameTeam((Player) event.getEntity(), (Player) event.getDamager())){
//				event.setCancelled(true);
//			}
//		}
//	}
	
	public Scoreboard getScoreboard(){
		return this.scoreboard;
	}
	
	private void createTeams(){
		teams.clear();
		split(Purge.getInstance().getUserManager().getUsers());
	}
	
	@Override
	public void load() {
//		if(super.getArguments().length > 0 && super.getArguments()[0] instanceof Integer){
//			teamSize = (Integer) super.getArguments()[0];
//		}
		Bukkit.getPluginManager().registerEvents(this, Purge.getInstance());
		this.createTeams();
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}
	
}
