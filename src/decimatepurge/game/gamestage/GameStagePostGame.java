package decimatepurge.game.gamestage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import decimatepurge.core.Purge;
import decimatepurge.game.GameStage;
import decimatepurge.game.GameStageID;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.User;

public class GameStagePostGame extends GameStage {

	public GameStagePostGame() {
		super("Post Game", GameStageID.POST_GAME_STAGE);
		super.loadModulesNoArguments(ModuleID.GRIEF_PROTECTION_MODULE, ModuleID.NO_CONNECTION_MODULE,
				ModuleID.DEFAULT_CHAT_MODULE, ModuleID.QUIT_SERVER_MODULE, ModuleID.BLOCK_COMMAND_MODULE,
				ModuleID.HUB_COMMAND_MODULE, ModuleID.ENDERPEARL_DAMAGE_REMOVAL_MODULE);
	}

	private void platform() {
		Location center = new Location(Bukkit.getWorld("war_world"), 0, 140, 0);
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				for (int z = 0; z < 5; z++) {
					if (y == 0) {
						center.clone().add(x - 2.5, y, z - 2.5).getBlock().setType(Material.GLASS);
					} else {
						center.clone().add(x - 2.5, y, z - 2.5).getBlock().setType(Material.AIR);
					}
				}
			}
		}
		for (User user : Purge.getInstance().getUserManager().getUsers()) {
			if(user.getPlayer() != null && user.getPlayer().isOnline()){
				user.getPlayer().teleport(center.clone().add(0, 1, 0));
			}
			if (!user.isDead() && !user.isSpectating() && user.isOnline()) {
				FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.YELLOW)
						.with(FireworkEffect.Type.BALL).build();
				for (int i = 0; i < 5; i++) {
					Firework fw = user.getPlayer().getLocation().getWorld().spawn(user.getPlayer().getLocation(),
							Firework.class);
					FireworkMeta meta = fw.getFireworkMeta();
					meta.clearEffects();
					meta.addEffect(effect);
					meta.setPower(2);
					fw.setFireworkMeta(meta);
				}
			}
		}
	}

	private void postGame() {
		platform();
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Purge.getInstance(), new Runnable() {

			@Override
			public void run() {
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					Purge.getInstance().kickPlayer(player, ChatColor.GREEN + "Thank you for participating in the war.");
				}
			}

		}, 20 * 10);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Purge.getInstance(), new Runnable() {

			@Override
			public void run() {
				finish();
			}

		}, 20 * 15);
	}

	@Override
	public void finish(Object... objects) {
		Purge.getInstance().shutdown();
	}

	@Override
	public void start() {
		postGame();
	}

}
