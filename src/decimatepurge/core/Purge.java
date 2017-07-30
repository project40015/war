package decimatepurge.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import decimatepurge.bungee.BungeeManager;
import decimatepurge.game.GameStageManager;
import decimatepurge.game.module.ModuleManager;
import decimatepurge.user.UserManager;

public class Purge extends JavaPlugin {

	private static Purge instance;
	private List<Manager> managers = new ArrayList<>();

	private ModuleManager moduleManager;
	private GameStageManager gameStageManager;
	private UserManager userManager;
	private BungeeManager bungeeManager;
	
	private String conString = "jdbc:mysql://198.100.26.121/mc_11737?user=mc_11737&password=f5fcae0e7d&autoReconnect=true";
	private Connection connection;

	private World world;
	
	@Override
	public void onEnable() {
		instance = this;

		this.establishConnection();
		fillManagers();

		for (Manager manager : this.managers) {
			Bukkit.getServer().getPluginManager().registerEvents(manager, this);
		}
		
		Bukkit.getServer().setSpawnRadius(0);
	}

	public String getServerID(){
		return "test";
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public void shutdown() {
		System.out.println("Preparing to shutdown faction war");
		Bukkit.getServer().shutdown();
	}

	public void kickPlayer(Player player, String message) {
		player.sendMessage(message);
		this.bungeeManager.send(player, "lobby");
	}

	private void fillManagers() {
		userManager = (UserManager) addManager(new UserManager());
		moduleManager = (ModuleManager) addManager(new ModuleManager());
		gameStageManager = (GameStageManager) addManager(new GameStageManager());
		bungeeManager = (BungeeManager) addManager(new BungeeManager());
	}
	
	private void establishConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(conString);
			this.connection.setAutoCommit(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Manager addManager(Manager manager) {
		this.managers.add(manager);
		return manager;
	}

	@Override
	public void onDisable() {
		for (Manager manager : managers) {
			manager.onDisable();
		}
	}
	
	public void setWorld(World world){
		this.world = world;
	}
	
	public World getWorld(){
		return this.world;
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public GameStageManager getGameStageManager() {
		return gameStageManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public BungeeManager getBungeeManager() {
		return this.bungeeManager;
	}

	public static Purge getInstance() {
		return instance;
	}

}
