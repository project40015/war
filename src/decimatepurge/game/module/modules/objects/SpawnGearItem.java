package decimatepurge.game.module.modules.objects;

import org.bukkit.inventory.ItemStack;

import decimatepurge.utils.Enchantment;

public class SpawnGearItem {

	private double cost;
	private ItemStack item;
	private GearItemType type;
	
	public SpawnGearItem(GearItemType type, double cost, ItemStack item, Enchantment... enchants){
		this.type = type;
		this.cost = cost;
		for(Enchantment enchantment : enchants){
			enchantment.apply(item);
		}
		this.item = item;
	}
	
	public ItemStack getItem(){
		return item;
	}
	
	public double getCost(){
		return cost;
	}
	
	public GearItemType getItemType(){
		return type;
	}
	
}
