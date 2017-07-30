package decimatepurge.game.module.modules;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import decimatepurge.game.module.ModuleManager.ModuleID;

public class GoldenHeadModule extends SimpleEventModule {

	private ItemStack headItem;
	
	public GoldenHeadModule(ModuleID id) {
		super(id);
		setupHeadItem();
	}
	
	private void setupHeadItem(){
		this.headItem = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta im = this.headItem.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Golden Head");
		headItem.setItemMeta(im);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		event.getDrops().add(headItem);
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event){
		if(event.getItem().getType().equals(Material.GOLDEN_APPLE)){
			if(event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null){
				if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Golden Head")){
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 15*20, 0));
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10*20, 1));
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120*20, 0));
					if(event.getItem().getAmount() > 1){
						ItemStack n = event.getItem().clone();
						n.setAmount(n.getAmount() - 1);
						event.getPlayer().getInventory().setItemInHand(n);
					}else{
						event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
					}
					event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 4 >= 20 ? 20 : event.getPlayer().getFoodLevel() + 4);
					event.getPlayer().setSaturation(30);
					event.setCancelled(true);
				}
			}
		}
	}

}
