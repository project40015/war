package decimatepurge.user;

import org.bukkit.ChatColor;

public enum Division {

	MASTERS(ChatColor.RED + "Masters", 2000),
	EMERALD_I(ChatColor.GREEN + "Emerald I", 1900),
	EMERALD_II(ChatColor.GREEN + "Emerald II", 1800),
	EMERALD_III(ChatColor.GREEN + "Emerald III", 1700),
	DIAMOND_I(ChatColor.AQUA + "Diamond I", 1600),
	DIAMOND_II(ChatColor.AQUA + "Diamond II", 1500),
	DIAMOND_III(ChatColor.AQUA + "Diamond III", 1400),
	GOLD_I(ChatColor.GOLD + "Gold I", 1300),
	GOLD_II(ChatColor.GOLD + "Gold II", 1200),
	GOLD_III(ChatColor.GOLD + "Gold III", 1100),
	SILVER_I(ChatColor.GRAY + "Silver I", 1000),
	SILVER_II(ChatColor.GRAY + "Silver II", 800),
	SILVER_III(ChatColor.GRAY + "Silver III", 500),
	RECRUIT(ChatColor.DARK_GRAY + "Recruit", 0);
	
	private String display;
	private int height;
	
	Division(String display, int height){
		this.display = display;
		this.height = height;
	}
	
	public String getDisplay(){
		return display;
	}
	
	public int getHeight(){
		return height;
	}
	
	public static Division getDivision(double elo){
		for(Division division : Division.values()){
			if(division.getHeight() <= elo){
				return division;
			}
		}
		return RECRUIT;
	}
	
}
