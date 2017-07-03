package decimatepurge.user;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class User {

	private Player player;
	private boolean dead = false;
	private String faction = "";
	private boolean spectating = false;
	private boolean off = false;

	/**
	 * <h1>Load a user object</h1>
	 * 
	 * <p>
	 * This class is a wrapper for the player object including other information
	 * that the faction war / purge plug-in wishes to store
	 * </p>
	 * 
	 * @param player
	 *            Player to create the object for
	 * @param faction
	 *            String representing the name of the user's faction, should
	 *            only be used to determine the team the user is placed into
	 */
	public User(Player player, String faction) {
		this.player = player;
		this.faction = faction;
	}

	/**
	 * <h1>Load a spectator</h1>
	 * 
	 * <p>
	 * Loads a user with less data and with spectator mode automatically turned
	 * on. Used for players joining while game is in progress.
	 * </p>
	 * 
	 * @param player
	 *            Player to place as spectator
	 */
	public User(Player player) {
		this.player = player;
		setSpectating();
	}

	/**
	 * <h1>Unload a user object</h1>
	 * 
	 * Disable user object, used when a user leaves the game. A new object is
	 * created when the user joins back.
	 */
	public void turnOff() {
		off = true;
	}

	/**
	 * @return Whether the player is enabled or not.
	 */
	public boolean isOff() {
		return off;
	}

	/**
	 * <h1>Make a player a spectator</h1>
	 * 
	 * <p>
	 * This command will kill the user. Understand that this is also a very
	 * strong setting and should only be applied to trustworthy users.
	 * </p>
	 */
	public void setSpectating() {
		this.spectating = true;
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.hidePlayer(player);
		}
		player.setGameMode(GameMode.SPECTATOR);
		player.sendMessage("");
		player.sendMessage(ChatColor.GRAY + "SPECTATOR ENABLED. IF CAUGHT " + ChatColor.RED.toString() + ChatColor.BOLD
				+ "GHOSTING" + ChatColor.GRAY + " YOU WILL BE SEVERELY PUNISHED.");
		player.sendMessage("");
	}

	/**
	 * <h1>Set faction tag</h1>
	 * 
	 * <p>
	 * This is typically not to be used, primarily for testing purposes.
	 * </p>
	 */
	public void setFaction(String faction) {
		this.faction = faction;
	}

	/**
	 * <h1>Get a user's faction</h1>
	 * 
	 * @return User's faction name, empty string if faction does not exist
	 */
	public String getFaction() {
		return faction;
	}

	public boolean isSpectating() {
		return spectating;
	}

	public boolean isOnline() {
		return player.isOnline();
	}

	public void setDead() {
		dead = true;
	}

	public boolean isDead() {
		return dead;
	}

	public Player getPlayer() {
		return player;
	}

	public void sendActionbar(String message) {
		((CraftPlayer) player).getHandle().playerConnection
				.sendPacket(new PacketPlayOutChat(
						IChatBaseComponent.ChatSerializer
								.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}"),
						(byte) 2));
	}

}
