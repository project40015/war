package decimatepurge.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import decimatepurge.core.Manager;
import decimatepurge.core.Purge;
import decimatepurge.game.gamestage.GameStageChangeEvent;
import decimatepurge.game.gamestage.GameStageGeneration;
import decimatepurge.game.gamestage.GameStageInGame;
import decimatepurge.game.gamestage.GameStagePostGame;
import decimatepurge.game.gamestage.GameStageWaiting;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager;
import decimatepurge.game.module.ModuleManager.ModuleID;

public class GameStageManager implements Manager {

	private GameStage[] stages = new GameStage[4];
	private int current = 0;

	public GameStageManager() {
		loadPermanentModules();
		this.loadStages();
		start();
	}

	private void loadStages() {
		stages[0] = new GameStageGeneration();
		stages[1] = new GameStageWaiting();
		stages[2] = new GameStageInGame();
		stages[3] = new GameStagePostGame();
	}

	public GameStage getCurrentStage() {
		return stages[current];
	}
	
	private void loadPermanentModules(){
		ModuleManager moduleManager = Purge.getInstance().getModuleManager();
		moduleManager.getModule(ModuleID.NO_WEATHER_MODULE).loadModule();
		moduleManager.getModule(ModuleID.BLOCK_ANIMAL_SPAWN_MODULE).loadModule();
		moduleManager.getModule(ModuleID.USER_STATISTICS_COMMAND_MODULE).loadModule();
	}

	private void start() {
		stages[0].loadGameStage(new ArrayList<>());
	}

	public void startNext() {
		// if(!(stages.length >= current + 2)){
		// Purge.getInstance().shutdown();
		// return;
		// }

		if (current + 1 >= stages.length) {
			return;
		}

		GameStage next = stages[current + 1];
		Bukkit.getServer().getPluginManager().callEvent(new GameStageChangeEvent(next));
		
		List<Module> remainingModules = new ArrayList<>();

		for (Module module : stages[current].getLoadedModules()) {
			if(!next.getLoadedModules().contains(module)){
				module.unloadModule();
			}else{
				remainingModules.add(module);
			}
		}

		next.loadGameStage(remainingModules);
		current++;
	}

	@Override
	public void onDisable() {

	}

}
