package decimatepurge.utils;

import org.bukkit.inventory.ItemStack;

public class Enchantment {

	private org.bukkit.enchantments.Enchantment enchant;
	private int level;
	
	public Enchantment(org.bukkit.enchantments.Enchantment enchant, int level){
		this.enchant = enchant;
		this.level = level;
	}
	
	public org.bukkit.enchantments.Enchantment getEnchantment(){
		return enchant;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void apply(ItemStack item){
		item.addEnchantment(enchant, level);
	}
	
}
