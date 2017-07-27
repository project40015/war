package decimatepurge.user;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class User {

	private Player player;
	private Rank rank;
	private boolean dead = false;
	private String faction = "";
	private boolean spectating = false;
	private boolean off = false;
	private String uuid;
	private String name;
	
	private long joined;
	
	//Global variables
	private int wins;
	private int totalKills;
	private int totalDeaths;
	private double elo;
	private long playtime;
	private int gamesPlayed;
	
	private int kills;

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
	@Deprecated
	public User(Player player, String faction) {
		joined = System.currentTimeMillis();
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
		joined = System.currentTimeMillis();
		this.player = player;
		setSpectating();
	}
	
	/**
	 * <h1>Load a loading user</h1>
	 * 
	 * <p>
	 * Loads a user object when a user requests to join. The player
	 * will be filled in later. This is the constructor to use.
	 * </p>
	 * 
	 * @param player
	 *            Player to place as spectator
	 */
	public User(String uuid){
		joined = System.currentTimeMillis();
		this.uuid = uuid;
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
	
	public void loadRank(Rank rank){
		this.rank = rank;
	}
	
	public String getUniqueId(){
		return player != null ? player.getUniqueId().toString() : uuid;
	}
	
	public void loadPlayer(Player player){
		this.player = player;
		this.name = player.getName();
	}

	public boolean isSpectating() {
		return spectating;
	}

	public boolean isOnline() {
		return player == null ? false : player.isOnline();
	}

	public void setDead() {
		dead = true;
	}
	
	public Rank getRank(){
		return rank;
	}

	public boolean isDead() {
		return dead;
	}

	public Player getPlayer() {
		return player;
	}
	
	public String getFullName(){
		return this.rank.getDisplay() + this.player.getName();
	}
	
	public void setTotalKills(int totalKills){
		this.totalKills = totalKills;
	}
	
	public void setTotalDeaths(int totalDeaths){
		this.totalDeaths = totalDeaths;
	}
	
	public void setElo(double elo){
		this.elo = elo;
	}
	
	public void setWins(int wins){
		this.wins = wins;
	}
	
	public void setPlayTime(long playtime){
		this.playtime = playtime;
	}
	
	public void killUser(User killed){
		this.kills++;
		this.totalKills++;
		setElo((elo + 32*(1 - Math.pow(10, elo/400.0)/(Math.pow(10, elo/400.0) + Math.pow(10, killed.elo/400.0)))));
	}
	
	public void death(User killer){
		this.totalDeaths++;
		setElo((elo + 32*(0 - Math.pow(10, elo/400.0)/(Math.pow(10, killer.elo/400.0) + Math.pow(10, elo/400.0)))));
	}
	
	public void setGamesPlayed(int gamesPlayed){
		this.gamesPlayed = gamesPlayed;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	/*
	 * 	private int wins;
	private int totalKills;
	private int totalDeaths;
	private double elo;
	private long playtime;
	private int gamesPlayed;
	
	private int kills;
	 */
	
	public int getWins(){
		return wins;
	}
	
	public int getTotalKills(){
		return totalKills;
	}
	
	public int getTotalDeaths(){
		return totalDeaths;
	}
	
	public double getElo(){
		return elo;
	}
	
	public long getPlaytime(){
		return playtime + System.currentTimeMillis() - joined;
	}
	
	public int getGamesPlayed(){
		return gamesPlayed;
	}
	
	public int getGameKills(){
		return kills;
	}

	public void sendActionbar(String message) {
		((CraftPlayer) player).getHandle().playerConnection
				.sendPacket(new PacketPlayOutChat(
						IChatBaseComponent.ChatSerializer
								.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}"),
						(byte) 2));
	}

}
