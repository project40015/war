package decimatepurge.game.gamestage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import decimatepurge.core.Purge;
import decimatepurge.game.GameStage;
import decimatepurge.game.GameStageID;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.User;

public class GameStagePostGame extends GameStage {

	public GameStagePostGame() {
		super("Post Game", GameStageID.POST_GAME_STAGE);
		super.loadModule(ModuleID.GRIEF_PROTECTION_MODULE);
		super.loadModule(ModuleID.NO_CONNECTION_MODULE);
		super.loadModule(ModuleID.DEFAULT_CHAT_MODULE);
		super.loadModule(ModuleID.QUIT_SERVER_MODULE);
		super.loadModule(ModuleID.BLOCK_COMMAND_MODULE);
	}
	
	private void platform(){
		Location center = new Location(Bukkit.getWorlds().get(0), 0, 140, 0);
		for(int x = 0; x < 5; x++){
			for(int y = 0; y < 4; y++){
				for(int z = 0; z < 5; z++){
					if(y == 0){
						center.clone().add(x-2.5, y, z-2.5).getBlock().setType(Material.GLASS);
					}else{
						center.clone().add(x-2.5, y, z-2.5).getBlock().setType(Material.AIR);
					}
				}
			}
		}
		for(User user : Purge.getInstance().getUserManager().getUsers()){
			user.getPlayer().teleport(center.clone().add(0,1,0));
		}
	}
	
	private void postGame(){
		platform();
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Purge.getInstance(), new Runnable(){

			@Override
			public void run() {
				finish();
			}
			
		}, 20*15);
	}

	@Override
	public void finish(Object... objects) {
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			player.kickPlayer(ChatColor.GREEN + "Thank you for participating in the war.");
		}
		Purge.getInstance().shutdown();
	}

	@Override
	public void start() {
		postGame();
	}

}
