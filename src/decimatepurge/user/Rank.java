package decimatepurge.user;

import org.bukkit.ChatColor;

public enum Rank {

	OWNER(ChatColor.RED + "[OWNER] "),
	MODERATOR(ChatColor.GOLD + "[MOD] "),
	HELPER(ChatColor.YELLOW + "[HELPER] "),
	YOUTUBER(ChatColor.GOLD + "[YT] "),
	DEFAULT(ChatColor.GRAY.toString());
	
	private String display;
	
	Rank(String display){
		this.display = display;
	}
	
	public String getDisplay(){
		return display;
	}
	
}
