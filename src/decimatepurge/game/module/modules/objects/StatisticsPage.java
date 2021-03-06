package decimatepurge.game.module.modules.objects;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import decimatepurge.core.Purge;
import decimatepurge.user.Division;
import decimatepurge.user.User;
import decimatepurge.user.UserLoadDataTask;
import decimatepurge.utils.ItemFactory;
import decimatepurge.utils.TimeFormatUtils;

public class StatisticsPage {

	private String name;
	private int kills;
	private int deaths;
	private int wins;
	private double kd;
	private double elo;
	private int gamesPlayed;
	private long playtime;
	
	private static final DecimalFormat df = new DecimalFormat("#.##");
	
	public StatisticsPage(Player player){
		this(Purge.getInstance().getUserManager().getUser(player));
	}
	
	public StatisticsPage(String name, int kills, int deaths, int wins, double elo, int gamesPlayed, long playtime){
		this.name = name;
		this.kills = kills;
		this.deaths = deaths;
		this.wins = wins;
		if(deaths != 0){
			kd = kills/(double)deaths;
		}else{
			kd = kills;
		}
		this.elo = elo;
		this.gamesPlayed = gamesPlayed;
		this.playtime = playtime;
	}
	
	@SuppressWarnings("deprecation")
	public StatisticsPage(Player toDisplay, String name){
		Bukkit.getServer().getScheduler().runTaskAsynchronously(Purge.getInstance(), new UserLoadDataTask(toDisplay, name));
	}
	
	public StatisticsPage(User user){
		this(user.getName(), user.getTotalKills(), user.getTotalDeaths(), user.getWins(), user.getElo(), user.getGamesPlayed(), user.getPlaytime());
	}
	
	public void display(Player player){
		Inventory inventory = Bukkit.getServer().createInventory(player, 18, ChatColor.GRAY + "Statistics: " + ChatColor.RED + name);
		inventory.setItem(1, ItemFactory.createItem(Material.IRON_SWORD, ChatColor.GRAY + "Kills: " + ChatColor.YELLOW + kills));
		inventory.setItem(2, ItemFactory.createItem(Material.DEAD_BUSH, ChatColor.GRAY + "Deaths: " + ChatColor.RED + deaths));
		inventory.setItem(3, ItemFactory.createItem(Material.ANVIL, ChatColor.GRAY + "K/D: " + ChatColor.WHITE + df.format(kd)));
		inventory.setItem(4, ItemFactory.createItem(Material.GOLDEN_APPLE, ChatColor.GRAY + "Wins: " + ChatColor.GOLD + wins));
		inventory.setItem(5, ItemFactory.createItem(Material.LADDER, ChatColor.GRAY + "Rank: " + Division.getDivision(elo).getDisplay() + ChatColor.GRAY + " (" + df.format(elo) + ")"));
		inventory.setItem(6, ItemFactory.createItem(Material.SIGN, ChatColor.GRAY + "Playtime: " + TimeFormatUtils.getTimeFormatted(playtime)));
		inventory.setItem(7, ItemFactory.createItem(Material.IRON_PICKAXE, ChatColor.GRAY + "Games: " + ChatColor.AQUA + gamesPlayed));
		
		player.openInventory(inventory);
	}
	
}
