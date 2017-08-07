package decimatepurge.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import decimatepurge.core.Purge;
import decimatepurge.game.module.modules.objects.StatisticsPage;

public class UserLoadDataTask extends BukkitRunnable {

	private Player toDisplay;
	private String name;
	private User user;
	
	public UserLoadDataTask(User user){
		this.user = user;
	}
	
	public UserLoadDataTask(Player toDisplay, String name){
		this.toDisplay = toDisplay;
		this.name = name;
	}
	
	public void run(){
		try{
			PreparedStatement statement;
			if(user != null){
				statement = Purge.getInstance().getConnection().prepareStatement(
						"SELECT * FROM `WarUserData` WHERE `uuid` = ?");
				statement.setString(1, user.getUniqueId());
			}else{
				statement = Purge.getInstance().getConnection().prepareStatement(
						"SELECT * FROM `WarUserData` WHERE `name` = ?");
				statement.setString(1, name);
			}
			ResultSet resultSet = statement.executeQuery();
			if(user == null){
				if(resultSet.next()){
					new StatisticsPage(resultSet.getString("name"), resultSet.getInt("kills"), resultSet.getInt("deaths"), resultSet.getInt("wins"), resultSet.getDouble("elo"), resultSet.getInt("gamesPlayed"), resultSet.getLong("playTime")).display(this.toDisplay);;
				}else{
					toDisplay.sendMessage(ChatColor.RED + "Could not find user " + ChatColor.YELLOW + "\"" + name + "\"" + ChatColor.RED + "!");
				}
				return;
			}
			if(resultSet.next()){
				if(user != null){
					user.setTotalKills(resultSet.getInt("kills"));
					user.setTotalDeaths(resultSet.getInt("deaths"));
					user.setWins(resultSet.getInt("wins"));
					user.setPlayTime(resultSet.getLong("playTime"));
					user.setGamesPlayed(resultSet.getInt("gamesPlayed"));
					user.setElo(resultSet.getDouble("elo"));
				}
			}else{
				PreparedStatement ps = Purge.getInstance().getConnection().prepareStatement(
						"INSERT INTO `WarUserData` (`uuid`, `name`, `kills`, `deaths`, `wins`, `playTime`, `gamesPlayed`, `elo`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
				);

				ps.setString(1, user.getUniqueId());
				ps.setString(2, "-null");
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.setInt(5, 0);
				ps.setLong(6, 0);
				ps.setInt(7, 0);
				ps.setDouble(8, 1000);
				ps.executeUpdate();
				ps.close();
			}
			resultSet.close();
			statement.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
