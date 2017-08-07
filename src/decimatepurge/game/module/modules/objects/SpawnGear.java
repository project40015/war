package decimatepurge.game.module.modules.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnGear {

	private List<SpawnGearItem> items = new ArrayList<>();
	private List<GearItemType> types = new ArrayList<>();
	private Player player;
	private double points;
	private List<ItemStack> lateAddMisc = new ArrayList<>();
	
	public SpawnGear(List<SpawnGearItem> items, Player player, double points){
		clone(items);
		this.player = player;
		this.points = points;
		this.types = GearItemType.getTypeList();
		load();
	}
	
	private void load(){
		while(points > 0){
			GearItemType type = pickRandomCategory();
			List<SpawnGearItem> possibleItems = getItems(type);
			if(possibleItems.size() == 0){
				break;
			}
			if(type != GearItemType.MISC){
				this.types.remove(type);
			}
			SpawnGearItem randomItem = possibleItems.get((int) (Math.random() * possibleItems.size()));
			this.points -= randomItem.getCost();
			this.items.remove(randomItem);
			apply(randomItem);
		}
		if(this.types.contains(GearItemType.WEAPON_MAIN)){
			player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
		}
		if(this.types.contains(GearItemType.WEAPON_BOW)){
			player.getInventory().setItem(1, new ItemStack(Material.BOW));
		}
		if(this.types.contains(GearItemType.HELMET)){
			player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		}
		if(this.types.contains(GearItemType.CHESTPLATE)){
			player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		}
		if(this.types.contains(GearItemType.LEGGINGS)){
			player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		}
		if(this.types.contains(GearItemType.BOOTS)){
			player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		}
		player.getInventory().addItem(new ItemStack(Material.COMPASS));
		player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
		player.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 64));
		player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
		player.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));
		player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
		for(ItemStack item : this.lateAddMisc){
			if(item != null){
				player.getInventory().addItem(item);
			}
		}
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));
		player.getInventory().addItem(new ItemStack(Material.ARROW, 32));
	}
	
	private void apply(SpawnGearItem item){
		if(item.getItemType().equals(GearItemType.HELMET)){
			player.getInventory().setHelmet(item.getItem());
		}else if(item.getItemType().equals(GearItemType.CHESTPLATE)){
			player.getInventory().setChestplate(item.getItem());
		}else if(item.getItemType().equals(GearItemType.LEGGINGS)){
			player.getInventory().setLeggings(item.getItem());
		}else if(item.getItemType().equals(GearItemType.BOOTS)){
			player.getInventory().setBoots(item.getItem());
		}else if(item.getItemType().equals(GearItemType.WEAPON_MAIN)){
			player.getInventory().setItem(0, item.getItem());
		}else if(item.getItemType().equals(GearItemType.WEAPON_BOW)){
			player.getInventory().setItem(1, item.getItem());
		}else{
			this.lateAddMisc.add(item.getItem());
		}
	}
	
	private List<SpawnGearItem> getItems(GearItemType type){
		List<SpawnGearItem> result = new ArrayList<>();
		for(SpawnGearItem item : items){
			if(item.getItemType().equals(type) && item.getCost() < points){
				result.add(item);
			}
		}
		if(result.size() > 5 && !type.equals(GearItemType.MISC)){
			result = Arrays.asList(result.get(result.size() - 1), result.get(result.size() - 2), result.get(result.size() - 3), result.get(result.size() - 4), result.get(result.size() - 5));
		}
		return result;
	}
	
	private GearItemType pickRandomCategory(){
		return types.get((int)(Math.random()*types.size()));
	}
	
	private void clone(List<SpawnGearItem> items){
		for(SpawnGearItem item : items){
			this.items.add(item);
		}
	}
	
}
