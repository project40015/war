package decimatepurge.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import decimatepurge.game.GameStageManager;
import decimatepurge.game.module.ModuleManager;
import decimatepurge.user.UserManager;

public class Purge extends JavaPlugin {

	private static Purge instance;
	private List<Manager> managers = new ArrayList<>();
	
	private ModuleManager moduleManager;
	private GameStageManager gameStageManager;
	private UserManager userManager;
	
	public void onEnable(){
		instance = this;
		
		fillManagers();
		
		for(Manager manager : this.managers){
			Bukkit.getServer().getPluginManager().registerEvents(manager, this);
		}
	}
	
	public void shutdown(){
		System.out.println("Preparing to shutdown faction war");
		Bukkit.getServer().shutdown();
	}
	
	private void fillManagers(){
		userManager = (UserManager) addManager(new UserManager());
		moduleManager = (ModuleManager) addManager(new ModuleManager());
		gameStageManager = (GameStageManager) addManager(new GameStageManager());
	}
	
	private Manager addManager(Manager manager){
		this.managers.add(manager);
		return manager;
	}
	
	public void onDisable(){
		for(Manager manager : managers){
			manager.onDisable();
		}
	}
	
	public ModuleManager getModuleManager(){
		return moduleManager;
	}
	
	public GameStageManager getGameStageManager(){
		return gameStageManager;
	}
	
	public UserManager getUserManager(){
		return userManager;
	}
	
	public static Purge getInstance(){
		return instance;
	}
	
}
