package decimatepurge.user;

import org.bukkit.ChatColor;

public enum Rank {

	OWNER(ChatColor.RED + "[OWNER] "),
	DEVELOPER(ChatColor.RED + "[DEV] "),
	MODERATOR(ChatColor.GOLD + "[MOD] "),
	HELPER(ChatColor.YELLOW + "[HELPER] "),
	YOUTUBER(ChatColor.GOLD + "[YT] "),
	DEFAULT(ChatColor.WHITE.toString());
	
	private String display;
	
	Rank(String display){
		this.display = display;
	}
	
	public String getDisplay(){
		return display;
	}
	
}
