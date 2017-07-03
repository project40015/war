package decimatepurge.game.module.modules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import decimatepurge.core.Purge;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.utils.TimeFormatUtils;

public class RegularScoreboardInformationModule extends Module {

	private Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
	private long start;
	private final String boardName = ChatColor.RED + "Faction War";
	private Objective objective;

	private String o = "", t = "";

	Score startScore, onlineScore;

	public RegularScoreboardInformationModule(ModuleID id) {
		super(id);
	}

	public void update(boolean first) {
		if (!first) {
			board.resetScores(t);
			board.resetScores(o);
			t = ChatColor.RED + (start == 0 ? "N/A" : TimeFormatUtils.getTimeFormattedMinutesSeconds(start, "now"));
			objective.getScore(t).setScore(5);
			o = ChatColor.RED.toString() + Bukkit.getServer().getOnlinePlayers().size();
			objective.getScore(o).setScore(2);
			return;
		}
		objective = board.registerNewObjective("Scoreboard", "dummy");

		objective.setDisplayName(boardName);
		int i = 8;
		objective.getScore(" ").setScore(--i); // 7
		objective.getScore(ChatColor.GRAY + "Game begins:").setScore(--i); // 6
		t = ChatColor.RED + (start == 0 ? "N/A" : TimeFormatUtils.getTimeFormattedMinutesSeconds(start, "now"));
		startScore = objective.getScore(t);
		startScore.setScore(--i); // 5
		objective.getScore("   ").setScore(--i); // 4
		objective.getScore(ChatColor.GRAY + "Online:").setScore(--i); // 3
		o = ChatColor.RED.toString() + Bukkit.getServer().getOnlinePlayers().size();
		onlineScore = objective.getScore(o);
		onlineScore.setScore(--i); // 2
		objective.getScore("  ").setScore(--i); // 1
		objective.getScore(ChatColor.GRAY.toString() + ChatColor.ITALIC + "decimatepvp").setScore(--i); // 0

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().setScoreboard(board);
	}

	@Override
	protected void load() {

		if (super.getArguments().length > 0 && super.getArguments()[0] instanceof Long) {
			start = (long) super.getArguments()[0];
		}

		update(true);
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.setScoreboard(board);
		}

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.setScoreboard(board);
		}
		Bukkit.getPluginManager().registerEvents(this, Purge.getInstance());
	}

	@Override
	protected void unload() {
		HandlerList.unregisterAll(this);
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
		}
	}

}
