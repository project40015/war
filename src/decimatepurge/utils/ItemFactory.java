package decimatepurge.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFactory {

	public static ItemStack createItem(Material type, String name){
		ItemStack itemStack = new ItemStack(type);
		ItemMeta im = itemStack.getItemMeta();
		im.setDisplayName(name);
		itemStack.setItemMeta(im);
		return itemStack;
	}
	
	public static ItemStack createItem(Material type, String name, List<String> lore){
		ItemStack itemStack = new ItemStack(type);
		ItemMeta im = itemStack.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		itemStack.setItemMeta(im);
		return itemStack;
	}
	
}
