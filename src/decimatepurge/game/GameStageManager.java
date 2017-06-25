package decimatepurge.game;

import decimatepurge.core.Manager;
import decimatepurge.game.gamestage.GameStageInGame;
import decimatepurge.game.gamestage.GameStagePostGame;
import decimatepurge.game.gamestage.GameStageWaiting;
import decimatepurge.game.module.Module;

public class GameStageManager implements Manager {

	private GameStage[] stages = new GameStage[3];
	private int current = 0;
	
	public GameStageManager(){
		this.loadStages();
		start();
	}
	
	private void loadStages(){
		stages[0] = new GameStageWaiting();
		stages[1] = new GameStageInGame();
		stages[2] = new GameStagePostGame();
	}
	
	public GameStage getCurrentStage(){
		return stages[current];
	}
	
	private void start(){
		stages[0].loadGameStage();
	}
	
	public void startNext(){
//		if(!(stages.length >= current + 2)){
//			Purge.getInstance().shutdown();
//			return;
//		}
		
		if(current + 1 >= stages.length){
			return;
		}
		
		GameStage next = stages[current + 1];
		
		for(Module module : stages[current].getLoadedModules()){
			module.unloadModule();
		}
		
		next.loadGameStage();
		current++;
	}

	@Override
	public void onDisable() {
		
	}
	
}
