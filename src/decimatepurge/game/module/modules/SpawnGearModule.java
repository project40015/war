package decimatepurge.game.module.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import decimatepurge.game.module.ArgumentNotFoundException;
import decimatepurge.game.module.Module;
import decimatepurge.game.module.ModuleManager.ModuleID;
import decimatepurge.game.module.modules.objects.GearItemType;
import decimatepurge.game.module.modules.objects.SpawnGear;
import decimatepurge.game.module.modules.objects.SpawnGearItem;
import decimatepurge.game.module.modules.objects.Team;
import decimatepurge.user.User;
import decimatepurge.utils.Enchantment;

public class SpawnGearModule extends Module {
	
	private TeamModule teamModule;
	private double maxPoints = 90;
	private List<SpawnGearItem> items = new ArrayList<>();
	
	public SpawnGearModule(ModuleID id) {
		super(id);
	}
	
	private void loadGear(){
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 20, new ItemStack(Material.DIAMOND_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 25, new ItemStack(Material.IRON_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 1), new Enchantment(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 1)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 25, new ItemStack(Material.IRON_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 30, new ItemStack(Material.DIAMOND_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 35, new ItemStack(Material.IRON_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 2), new Enchantment(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 1)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 35, new ItemStack(Material.IRON_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 3)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 40, new ItemStack(Material.DIAMOND_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 2), new Enchantment(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 1)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 45, new ItemStack(Material.DIAMOND_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 3)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_MAIN, 45, new ItemStack(Material.IRON_SWORD), new Enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 4)));
		
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_BOW, 10, new ItemStack(Material.BOW), new Enchantment(org.bukkit.enchantments.Enchantment.ARROW_DAMAGE, 1)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_BOW, 15, new ItemStack(Material.BOW), new Enchantment(org.bukkit.enchantments.Enchantment.ARROW_DAMAGE, 2)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_BOW, 20, new ItemStack(Material.BOW), new Enchantment(org.bukkit.enchantments.Enchantment.ARROW_DAMAGE, 3)));
		this.items.add(new SpawnGearItem(GearItemType.WEAPON_BOW, 20, new ItemStack(Material.BOW), new Enchantment(org.bukkit.enchantments.Enchantment.ARROW_DAMAGE, 1), new Enchantment(org.bukkit.enchantments.Enchantment.ARROW_FIRE, 1)));

		this.items.add(new SpawnGearItem(GearItemType.HELMET, 5, new ItemStack(Material.IRON_HELMET), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.HELMET, 10, new ItemStack(Material.IRON_HELMET), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.HELMET, 15, new ItemStack(Material.DIAMOND_HELMET), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.HELMET, 20, new ItemStack(Material.DIAMOND_HELMET), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.HELMET, 25, new ItemStack(Material.DIAMOND_HELMET), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 3)));
		
		this.items.add(new SpawnGearItem(GearItemType.CHESTPLATE, 10, new ItemStack(Material.IRON_CHESTPLATE), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.CHESTPLATE, 15, new ItemStack(Material.IRON_CHESTPLATE), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.CHESTPLATE, 20, new ItemStack(Material.DIAMOND_CHESTPLATE), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.CHESTPLATE, 25, new ItemStack(Material.DIAMOND_CHESTPLATE), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.CHESTPLATE, 30, new ItemStack(Material.DIAMOND_CHESTPLATE), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 3)));
		
		this.items.add(new SpawnGearItem(GearItemType.LEGGINGS, 10, new ItemStack(Material.IRON_LEGGINGS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.LEGGINGS, 15, new ItemStack(Material.IRON_LEGGINGS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.LEGGINGS, 20, new ItemStack(Material.DIAMOND_LEGGINGS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.LEGGINGS, 25, new ItemStack(Material.DIAMOND_LEGGINGS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.LEGGINGS, 30, new ItemStack(Material.DIAMOND_LEGGINGS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 3)));
		
		this.items.add(new SpawnGearItem(GearItemType.BOOTS, 5, new ItemStack(Material.IRON_BOOTS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.BOOTS, 10, new ItemStack(Material.IRON_BOOTS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.BOOTS, 15, new ItemStack(Material.DIAMOND_BOOTS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1)));
		this.items.add(new SpawnGearItem(GearItemType.BOOTS, 20, new ItemStack(Material.DIAMOND_BOOTS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2)));
		this.items.add(new SpawnGearItem(GearItemType.BOOTS, 25, new ItemStack(Material.DIAMOND_BOOTS), new Enchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 3)));
		
		//Speed I for 3:00
		this.items.add(new SpawnGearItem(GearItemType.MISC, 20, new ItemStack(Material.POTION, 1, (short) 8194)));
		//Fire res for 3:00
		this.items.add(new SpawnGearItem(GearItemType.MISC, 15, new ItemStack(Material.POTION, 1, (short) 8195)));
		this.items.add(new SpawnGearItem(GearItemType.MISC, 15, new ItemStack(Material.ENDER_PEARL)));
		this.items.add(new SpawnGearItem(GearItemType.MISC, 15, new ItemStack(Material.FLINT_AND_STEEL)));
		this.items.add(new SpawnGearItem(GearItemType.MISC, 10, new ItemStack(Material.WEB, 4)));
		this.items.add(new SpawnGearItem(GearItemType.MISC, 5, new ItemStack(Material.WATER_BUCKET)));
		this.items.add(new SpawnGearItem(GearItemType.MISC, 8, new ItemStack(Material.FISHING_ROD)));
		this.items.add(new SpawnGearItem(GearItemType.MISC, 5, new ItemStack(Material.GOLDEN_APPLE, 2)));
		this.items.add(new SpawnGearItem(GearItemType.MISC, 5, new ItemStack(Material.GOLDEN_APPLE, 2)));
		this.items.add(new SpawnGearItem(GearItemType.MISC, 8, new ItemStack(Material.GOLDEN_APPLE, 3)));

	}
	
	private void loadSpawnGear() throws ArgumentNotFoundException {
		// arguments (2): 0 - TeamModule, 1 - max_points
		if(super.getArguments().length >= 1 && super.getArguments()[0] instanceof TeamModule){
			TeamModule teamModule = (TeamModule) super.getArguments()[0];
			this.teamModule = teamModule;
		}else{
			throw new ArgumentNotFoundException("Necessary TeamModule missing: index 0");
		}
		if(super.getArguments().length >= 2 && super.getArguments()[0] instanceof Integer){
			int maxPoints = (int) super.getArguments()[1];
			this.maxPoints = maxPoints;
		}
		loadGear();
		for(Team team : this.teamModule.getAliveTeams()){
			int size = team.getAliveMembers().size();
			double pointsPerPlayer = maxPoints/(size*0.55);
			for(User player : team.getAliveMembers()){
				if(player.getPlayer() != null && player.getPlayer().isOnline()){
					new SpawnGear(items, player.getPlayer(), pointsPerPlayer);
				}
			}
		}
		
	}
	
	@Override
	public void load(){
		try{
			loadSpawnGear();
		}catch(ArgumentNotFoundException ex){
			ex.printStackTrace();
		}
	}

	@Override
	protected void unload() {
		
	}

}
