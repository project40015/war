package decimatepurge.game.gamestage;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import decimatepurge.core.Purge;
import decimatepurge.game.GameStage;
import decimatepurge.game.GameStageID;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.RegularScoreboardInformationModule;
import decimatepurge.game.module.modules.commands.FactionCommandModule;

public class GameStageWaiting extends GameStage {

	private final int TIME_TO_START = 60 * 10; // TODO make it 30 minutes

	private int run;
	private int timeToStart;
	private RegularScoreboardInformationModule scoreboardModule;
	private FactionCommandModule factionModule;

	public GameStageWaiting() {
		super("Waiting", GameStageID.WAITING_STAGE);
		super.loadModulesNoArguments(ModuleID.GRIEF_PROTECTION_MODULE, ModuleID.DEFAULT_CHAT_MODULE,
				ModuleID.JOIN_SERVER_MODULE, ModuleID.QUIT_SERVER_MODULE, ModuleID.START_GAME_COMMAND_MODULE,
				ModuleID.FACTION_DEFAULT_COMMAND_MODULE, ModuleID.BLOCK_COMMAND_MODULE, ModuleID.HUB_COMMAND_MODULE,
				ModuleID.SPECTATE_COMMAND_MODULE);
		super.loadModule(ModuleID.REGULAR_SCOREBOARD_INFORMATION_MODULE,
				(System.currentTimeMillis() + TIME_TO_START * 1000));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		factionModule.leave(Purge.getInstance().getUserManager().getUser(event.getPlayer()));
	}

	private void startTimer() {
		timeToStart = TIME_TO_START;
		run = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Purge.getInstance(), new Runnable() {

			@Override
			public void run() {
				timeToStart--;
				scoreboardModule.update(false);
				if (timeToStart == 0) {
					finish();
				}
			}

		}, 20, 20);
	}

	public int getTimeToStart() {
		return this.timeToStart;
	}

	@Override
	public void finish(Object... objects) {
		Bukkit.getServer().getScheduler().cancelTask(run);
		super.startNext();
	}

	@Override
	public void start() {
		scoreboardModule = (RegularScoreboardInformationModule) super.getModule(
				ModuleID.REGULAR_SCOREBOARD_INFORMATION_MODULE);
		factionModule = (FactionCommandModule) super.getModule(ModuleID.FACTION_DEFAULT_COMMAND_MODULE);
		startTimer();
	}

}
