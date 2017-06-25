package decimatepurge.user;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class User {

	private Player player;
	private boolean dead = false;
	private String faction = "";

	/**
	 * <h1>
	 * Load a user object
	 * </h1>
	 * 
	 * This class is a wrapper for the player object
	 * including other information that the faction
	 * war / purge plug-in wishes to store
	 * 
	 * @param player Player to create the object for
	 * @param faction String representing the name of
	 * the user's faction, should only be used to
	 * determine the team the user is placed into
	 */
	public User(Player player, String faction) {
		this.player = player;
		this.faction = faction;
	}
	
	/**
	 * <h1>
	 * Set faction tag
	 * </h1>
	 * 
	 * <p>
	 * This is typically not to be used,
	 * primarily for testing purposes.
	 * </p>
	 */
	public void setFaction(String faction){
		this.faction = faction;
	}
	
	/**
	 * <h1>
	 * Get a user's faction
	 * </h1>
	 * 
	 * @return User's faction name, empty
	 * string if faction does not exist
	 */
	public String getFaction(){
		return faction;
	}
	
	public boolean isOnline(){
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
