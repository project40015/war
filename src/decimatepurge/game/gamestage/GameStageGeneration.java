package decimatepurge.game.gamestage;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import decimatepurge.core.Purge;
import decimatepurge.game.GameStage;
import decimatepurge.game.GameStageID;
import decimatepurge.game.module.ModuleManager.ModuleID;

public class GameStageGeneration extends GameStage {

	public GameStageGeneration() {
		super("generation", GameStageID.GENERATION_STAGE);
		super.loadModulesNoArguments(ModuleID.NO_CONNECTION_MODULE);
	}

	@Override
	public void finish(Object... objects) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Purge.getInstance(), new Runnable(){
			
			@Override
			public void run(){
				startNext();
			}
			
		}, 40);
	}

	@Override
	public void start() {
//		try {
//			FileUtils.cleanDirectory(new File(Bukkit.getServer().getWorldContainer(), "war_world"));
//			FileUtils.copyDirectory(new File(Purge.getInstance().getDataFolder(), "war_world_1"), new File(Bukkit.getServer().getWorldContainer(), "war_world"));
//			finish();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		Bukkit.createWorld(new WorldCreator("war_world"));
		Purge.getInstance().setWorld(Bukkit.getServer().getWorld("war_world"));
		finish();
	}

}
