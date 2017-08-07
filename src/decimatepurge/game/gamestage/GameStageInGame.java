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

		super.loadModulesNoArguments(ModuleID.TEAM_MODULE, ModuleID.QUIT_SERVER_MODULE,
				ModuleID.NO_REGENERATION_MODULE, ModuleID.FACTION_DEFAULT_COMMAND_MODULE, ModuleID.SHOUT_COMMAND_MODULE,
				ModuleID.BLOCK_COMMAND_MODULE, ModuleID.DEATH_MESSAGE_MODULE, ModuleID.COMPASS_POINT_MODULE,
				ModuleID.HUB_COMMAND_MODULE, ModuleID.SPECTATE_COMMAND_MODULE, ModuleID.HEALTH_COMMAND_MODULE, ModuleID.ENDERPEARL_DAMAGE_REMOVAL_MODULE,
				ModuleID.GOLDEN_HEAD_MODULE, ModuleID.LIMITED_BREAK_MODULE);

		super.loadModule(ModuleID.SPAWN_GEAR_MODULE, super.getModule(ModuleID.TEAM_MODULE), 90);
		super.loadModule(ModuleID.NO_CONNECTION_MODULE, true);
		super.loadModule(ModuleID.MAP_MODULE, 250, true); // Map size as
															// argument 0. Should
															// be higher.
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		//TODO change to true
		die(event.getEntity(), false, false);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		die(event.getPlayer(), true, true);
	}

//	@EventHandler
//	public void onGamemodeChange(PlayerGameModeChangeEvent event) {
//		if (event.getNewGameMode().equals(GameMode.SPECTATOR)) {
//			die(event.getPlayer(), false);
//		}
//	}

	private void die(Player player, boolean kick, boolean quit) {
		User user = Purge.getInstance().getUserManager().getUser(player);
		if(!user.isDead() && quit){
			Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " died! (disconnect)");
		}
		user.setDead();
		teamModule.getTeam(user).recalc();

		if (player.isOnline() && kick) {
			Purge.getInstance().kickPlayer(player, ChatColor.RED + "You lost the war");
		}
		
		if(!kick){
			user.setSpectating();
		}

		Team winner = endCheck();
		if (winner != null) {
			finish(winner);
		}
	}

	private Team endCheck() {
		Team winner = null;
		for (Team team : teamModule.getTeams()) {
			if (!team.isOut()) {
				if (winner == null) {
					winner = team;
				} else {
					return null;
				}
			}
		}
		return winner;
	}

	@Override
	public void finish(Object... objects) {
		HandlerList.unregisterAll(this);
		if (objects[0] instanceof Team) {
			Team winner = (Team) objects[0];
			String winName = winner.getName();
			if (winName.equalsIgnoreCase("")) {
				winName = winner.getUsers().get(0).getPlayer().getName();
			}
			for(User user : winner.getAliveMembers()){
				user.setWins(user.getWins() + 1);
				user.setElo(user.getElo() + 64);
			}
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(ChatColor.GOLD + winName + " has won the war!");
			Bukkit.broadcastMessage("");
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				player.playSound(player.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
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
		Bukkit.broadcastMessage(ChatColor.RED + "The faction war has stared with " + ChatColor.YELLOW
				+ ChatColor.BOLD.toString() + Purge.getInstance().getUserManager().getUsers().size() + ChatColor.RED
				+ " players across " + ChatColor.YELLOW + ChatColor.BOLD.toString() + teamModule.getTeams().size()
				+ ChatColor.RED + " factions.");
		Bukkit.broadcastMessage("");
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.WOLF_HOWL, 1, 1);
			player.setSaturation(24);
		}
		for(User user : Purge.getInstance().getUserManager().getUsers()){
			user.setGamesPlayed(user.getGamesPlayed() + 1);
		}
	}

}
