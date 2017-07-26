package decimatepurge.user;

import org.bukkit.ChatColor;

public enum Rank {

	OWNER(ChatColor.RED + "[OWNER] ", 5),
	DEVELOPER(ChatColor.RED + "[DEV] ", 5),
	MODERATOR(ChatColor.GOLD + "[MOD] ", 4),
	HELPER(ChatColor.YELLOW + "[HELPER] ", 3),
	YOUTUBER(ChatColor.GOLD + "[YT] ", 2),
	DONOR(ChatColor.GREEN + "[DONOR] ", 1),
	DEFAULT(ChatColor.WHITE.toString(), 0);
	
	private String display;
	private int id;
	
	Rank(String display, int id){
		this.display = display;
		this.id = id;
	}
	
	public String getDisplay(){
		return display;
	}
	
	public int getId(){
		return id;
	}
	
	public static Rank getRank(int id){
		for(Rank rank : Rank.values()){
			if(rank.getId() == id){
				return rank;
			}
		}
		return null;
	}
	
}
