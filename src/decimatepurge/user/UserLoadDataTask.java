package decimatepurge.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.scheduler.BukkitRunnable;

import decimatepurge.core.Purge;

public class UserLoadDataTask extends BukkitRunnable {

	private String name;
	private User user;
	
	public UserLoadDataTask(User user){
		this.user = user;
	}
	
	public UserLoadDataTask(String name){
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
			if(resultSet.next()){
				if(user != null){
					user.setTotalKills(resultSet.getInt("kills"));
					user.setTotalDeaths(resultSet.getInt("deaths"));
					user.setWins(resultSet.getInt("wins"));
					user.setPlayTime(resultSet.getLong("playTime"));
					user.setGamesPlayed(resultSet.getInt("gamesPlayed"));
					user.setElo(resultSet.getDouble("elo"));
				}else{
					//TODO send player the information ...
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
