package decimatepurge.game.module.modules.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import decimatepurge.core.Purge;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.user.User;

public class FactionCommandModule extends SimplePlayerCommandModule {

	public FactionCommandModule(ModuleID id, String command) {
		super(id, command);
	}

	private void listCommands(Player player){
		player.sendMessage(ChatColor.RED + "Faction War" + ChatColor.GRAY + ":");
		send(player, "(show/who) [faction]", "List the players of a faction");

	}
	
	private void send(Player player, String command, String description){
		player.sendMessage(ChatColor.RED + "/" + super.getCommand() + " " + command + ChatColor.GRAY + " - " + description);
	}
	
	private void displayFaction(Player player, String tag, boolean self){
		if(!self){
			try{
				Player p = Bukkit.getServer().getPlayer(tag);
				tag = Purge.getInstance().getUserManager().getUser(p).getFaction();
			}catch(Exception ex){ }
		}
		if(tag.equals("")){
			player.sendMessage(ChatColor.RED + "User is not playing with a faction.");
			return;
		}
		List<User> users = new ArrayList<>();
		for(User user : Purge.getInstance().getUserManager().getUsers()){
			if(user.isOnline() && !user.isDead() && user.getFaction().equalsIgnoreCase(tag)){
				users.add(user);
			}
		}
		if(users.size() == 0){
			player.sendMessage(ChatColor.RED + "Faction not found.");
			return;
		}
		player.sendMessage(ChatColor.GRAY + "Displaying [" + ChatColor.RED + tag + ChatColor.GRAY + "] (" + users.size() + "):");
		String online = "";
		for(int i = 0; i < users.size(); i++){
			online += ChatColor.GRAY + users.get(i).getPlayer().getName() + ChatColor.YELLOW + " (" + (int)player.getHealth() + ")";
			if(i != users.size() - 1){
				online += ChatColor.GRAY + ", ";
			}else{
				online += ChatColor.GRAY + ".";
			}
		}
		player.sendMessage(online);
	}

	@Override
	protected void onCommand(Player player, Command command, String[] args) {
		User user = Purge.getInstance().getUserManager().getUser(player);
		if(args.length == 0){
			listCommands(player);
			return;
		}
		if(args[0].equalsIgnoreCase("show") || args[0].equalsIgnoreCase("who")){
			if(args.length == 1){
				displayFaction(player, user.getFaction(), true);
			}else{
				displayFaction(player, args[1], false);
			}
			return;
		}
		if(args[0].equalsIgnoreCase("change")){
			if(player.isOp()){
				if(args.length >= 2){
					user.setFaction(args[1]);
				}else{
					player.sendMessage(ChatColor.RED + "Invalid syntax, try /f change (tag)");
				}
			}else{
				player.sendMessage(super.noPermission());
			}
			return;
		}		
	}
	

}
