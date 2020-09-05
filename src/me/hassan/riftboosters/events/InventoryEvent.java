package me.hassan.riftboosters.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.hassan.riftboosters.RiftBoosters;
import me.hassan.riftboosters.boosterinventory.BoosterMainInventory;
import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.safenbt.SafeNBT;
import me.hassan.riftboosters.utils.BoosterUtil;
import me.hassan.riftboosters.utils.Common;


public class InventoryEvent implements Listener {
	
	
	@EventHandler
	public void onMainMenuClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		Inventory inv = e.getClickedInventory();
		
		if(inv == null) return;
		
		if(RiftBoosters.getInstance().mainInventory.containsKey(player.getUniqueId())) {
			Inventory mInventory = RiftBoosters.getInstance().mainInventory.get(player.getUniqueId());
			
			if(inv == mInventory || inv.equals(mInventory)) {
				e.setCancelled(true);
				
				ItemStack item = e.getCurrentItem();
				
				SafeNBT nbt = SafeNBT.get(item);
				
				if(nbt.hasKey("booster")) {
					BoosterType type = BoosterType.valueOf(nbt.getString("booster").toUpperCase());
					player.closeInventory();
					BoosterMainInventory.createBoosterMenu(player, type);
					RiftBoosters.getInstance().mainInventory.remove(player.getUniqueId());
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void onBoosterInventory(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		Inventory inv = e.getClickedInventory();
		
		if(inv == null) return;
		
		if(RiftBoosters.getInstance().boosterInventory.containsKey(player.getUniqueId())) {
			Inventory mInventory = RiftBoosters.getInstance().boosterInventory.get(player.getUniqueId());
			
			if(inv == mInventory || inv.equals(mInventory)) {
				e.setCancelled(true);
				ItemStack item = e.getCurrentItem();
				
				SafeNBT nbt = SafeNBT.get(item);
				
				if(nbt.hasKey("type")) {
					BoosterType type = BoosterType.valueOf(nbt.getString("type").toUpperCase());
					player.closeInventory();
					
					if(BoosterUtil.acitveBooster(player, nbt.getString("id"), type)) {
						Common.sendMessage(player, "&d&lRift&5&lMC &8>> &dYou have actived a " + type.toString() + " booster");
						Common.sendMessage(player, "&d&lRift&5&lMC &8>> &dDo /boosters active to view your current booster status");
					}else {
						Common.sendMessage(player, "&d&lRift&5&lMC &8>> &dYou already have a " + type.toString() + " enabled");
						Common.sendMessage(player, "&d&lRift&5&lMC &8>> &dType /boosters active - to view your boosters that are active");
					}
				}
				
			}
		}
	}

}
