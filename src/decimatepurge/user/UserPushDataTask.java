package decimatepurge.user;

import java.sql.PreparedStatement;

import org.bukkit.scheduler.BukkitRunnable;

import decimatepurge.core.Purge;

public class UserPushDataTask extends BukkitRunnable {

	private User user;

	public UserPushDataTask(User user) {
		this.user = user;
	}

	public void run() {
		try {
			PreparedStatement s = Purge.getInstance().getConnection().prepareStatement(
					"UPDATE `users` SET `name` = ?, `kills` = ?, `deaths` = ?, `wins` = ?, `playTime` = ?, `gamesPlayed` = ?, `elo` = ? WHERE `uuid` = ?");
			
			s.setString(8, user.getUniqueId());
			s.setString(1, user.getName());
			s.setInt(2, user.getTotalKills());
			s.setInt(3, user.getTotalDeaths());
			s.setInt(4, user.getWins());
			s.setLong(5, user.getPlaytime());
			s.setInt(6, user.getGamesPlayed());
			s.setDouble(7, user.getElo());
			s.executeUpdate();
			s.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
