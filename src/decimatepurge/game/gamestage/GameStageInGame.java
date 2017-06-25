package decimatepurge.game.gamestage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import decimatepurge.core.Purge;
import decimatepurge.game.GameStage;
import decimatepurge.game.GameStageID;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.TeamModule;
import decimatepurge.game.module.modules.objects.Team;
import decimatepurge.user.User;

public class GameStageInGame extends GameStage {

	private TeamModule teamModule;
	
	public GameStageInGame() {
		super("In Progress", GameStageID.IN_GAME_STAGE);
		
		super.loadModule(ModuleID.NO_CONNECTION_MODULE);
		super.loadModule(ModuleID.TEAM_MODULE);
		super.loadModule(ModuleID.QUIT_SERVER_MODULE);
		super.loadModule(ModuleID.MAP_MODULE, 100, true); // Map size as argument 0 should be higher
		super.loadModule(ModuleID.NO_REGENERATION_MODULE);
		super.loadModule(ModuleID.FACTION_DEFAULT_COMMAND_MODULE);
		super.loadModule(ModuleID.SHOUT_COMMAND_MODULE);
		super.loadModule(ModuleID.BLOCK_COMMAND_MODULE);
		super.loadModule(ModuleID.DEATH_MESSAGE_MODULE);
		super.loadModule(ModuleID.COMPASS_POINT_MODULE);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		die(event.getEntity());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		die(event.getPlayer());
	}
	
	private void die(Player player){
		User user = Purge.getInstance().getUserManager().getUser(player);
		user.setDead();
		teamModule.getTeam(user).recalc();
		
		if(player.isOnline()){
			player.kickPlayer(ChatColor.RED + "You lost the war");
		}
		
		Team winner = endCheck();
		if(winner != null){
			finish(winner);
		}
	}
	
	private Team endCheck(){
		Team winner = null;
		for(Team team : teamModule.getTeams()){
			if(!team.isOut()){
				if(winner == null){
					winner = team;
				}else{
					return null;
				}
			}
		}
		return winner;
	}
	
	@Override
	public void finish(Object...objects) {
		HandlerList.unregisterAll(this);
		if(objects[0] instanceof Team){
			Team winner = (Team) objects[0];
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(ChatColor.AQUA + winner.getName() + " has won the war!");
			Bukkit.broadcastMessage("");
			for(Player player : Bukkit.getServer().getOnlinePlayers()){
				player.playSound(player.getLocation(), Sound.SWIM, 1, 1);
			}
		}
		super.startNext();
	}

	@Override
	public void start() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Purge.getInstance());
		teamModule = (TeamModule) super.getModule(ModuleID.TEAM_MODULE);
		Purge.getInstance().getUserManager().broadcastActionBar(ChatColor.RED + "War started!");

		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.RED + "The faction war has stared with " + ChatColor.YELLOW + ChatColor.BOLD.toString() + Purge.getInstance().getUserManager().getUsers().size() + ChatColor.RED + " players across " + ChatColor.YELLOW + ChatColor.BOLD.toString() + teamModule.getTeams().size() + ChatColor.RED + " factions.");
		Bukkit.broadcastMessage("");
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			player.playSound(player.getLocation(), Sound.WOLF_HOWL, 1, 1);
			player.setSaturation(24);
		}
	}
	
}
