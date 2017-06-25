package decimatepurge.game.gamestage;

import org.bukkit.Bukkit;

import decimatepurge.core.Purge;
import decimatepurge.game.GameStage;
import decimatepurge.game.GameStageID;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.RegularScoreboardInformationModule;

public class GameStageWaiting extends GameStage {

	private final int TIME_TO_START = 60*10; // TODO make it 30 minutes
	
	private int run;
	private int timeToStart;
	private RegularScoreboardInformationModule scoreboardModule;
	
	public GameStageWaiting() {
		super("Waiting", GameStageID.WAITING_STAGE);
		super.loadModule(ModuleID.GRIEF_PROTECTION_MODULE);
		super.loadModule(ModuleID.DEFAULT_CHAT_MODULE);
		super.loadModule(ModuleID.JOIN_SERVER_MODULE);
		super.loadModule(ModuleID.QUIT_SERVER_MODULE);
		super.loadModule(ModuleID.REGULAR_SCOREBOARD_INFORMATION_MODULE, (System.currentTimeMillis() + TIME_TO_START*1000));
		super.loadModule(ModuleID.START_GAME_COMMAND_MODULE);
		super.loadModule(ModuleID.FACTION_DEFAULT_COMMAND_MODULE);
		super.loadModule(ModuleID.BLOCK_COMMAND_MODULE);
	}
	
	private void startTimer(){
		timeToStart = TIME_TO_START;
		run = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Purge.getInstance(), new Runnable(){

			@Override
			public void run() {
				timeToStart--;
				scoreboardModule.update(false);
				if(timeToStart == 0){
					finish();
				}
			}
			
		}, 20, 20);
	}
	
	public int getTimeToStart(){
		return this.timeToStart;
	}
	
	@Override
	public void finish(Object...objects) {
		Bukkit.getServer().getScheduler().cancelTask(run);
		super.startNext();
	}

	@Override
	public void start() {
		scoreboardModule = (RegularScoreboardInformationModule) super.getModule(ModuleID.REGULAR_SCOREBOARD_INFORMATION_MODULE);
		startTimer();
	}

}
