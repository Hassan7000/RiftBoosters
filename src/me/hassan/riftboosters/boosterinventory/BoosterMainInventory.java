package me.hassan.riftboosters.boosterinventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.hassan.riftboosters.RiftBoosters;
import me.hassan.riftboosters.boosterdata.BoosterData;
import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.utils.Common;
import me.hassan.riftboosters.utils.ItemBuilder;

public class BoosterMainInventory {
	
	public static void createMainMenu(Player player) {
		Inventory inv = Bukkit.createInventory(player, 9*3, Common.colorMessage("&8&l➢ &5Booster Menu"));
		
		ArrayList<String> lore = new ArrayList<>();
		
		lore.add(Common.colorMessage("&7Click to view your available Sell Boosters"));
		ItemStack sellBooster = new ItemBuilder(Material.PAPER)
				.setDisplayName(Common.colorMessage("&6&lSell Boosters"))
				.setLore(lore)
				.setKey("booster", "SELL")
				.build();
		lore.clear();
		
		lore.add(Common.colorMessage("&7Click to view your available XP Boosters"));
		ItemStack xpBooster = new ItemBuilder(Material.EXP_BOTTLE)
				.setDisplayName(Common.colorMessage("&e&lXP Boosters"))
				.setLore(lore)
				.setKey("booster", "XP")
				.build();
		lore.clear();
		
		lore.add(Common.colorMessage("&7Click to view your available BM Boosters"));
		ItemStack bmBooster = new ItemBuilder(Material.EMERALD)
				.setDisplayName(Common.colorMessage("&d&lBM Boosters"))
				.setLore(lore)
				.setKey("booster", "BLACKMARKET")
				.build();
		lore.clear();
		
		lore.add(Common.colorMessage("&7Click to view your available MCMMO Boosters"));
		ItemStack mcmmoBooster = new ItemBuilder(Material.DOUBLE_PLANT)
				.setDisplayName(Common.colorMessage("&b&lMCMMO Boosters"))
				.setLore(lore)
				.setKey("booster", "MCMMO")
				.build();
		lore.clear();
		
		inv.setItem(10, sellBooster);
		inv.setItem(12, xpBooster);
		inv.setItem(14, bmBooster);
		inv.setItem(16, mcmmoBooster);
		fillEmptySlots(inv, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7)
				.setDisplayName(Common.colorMessage("&f "))
				.build());
		RiftBoosters.getInstance().mainInventory.put(player.getUniqueId(), inv);
		player.openInventory(inv);
		
	}
	
	public static void createBoosterMenu(Player player, BoosterType type) {
		Inventory inv = Bukkit.createInventory(player, 54, Common.colorMessage("&8&l➢ &5Booster " + type.toString()));
		String uuid = player.getUniqueId().toString();
		int slot = 0;
		if(RiftBoosters.getInstance().boosters.containsKey(uuid)) {
			
			
			ItemStack boosterItem = new ItemStack(Material.STONE);
			
			if(type == BoosterType.SELL) {
				boosterItem = new ItemStack(Material.PAPER);
			}else if(type == BoosterType.BLACKMARKET) {
				boosterItem = new ItemStack(Material.EMERALD);
			}else if(type == BoosterType.XP) {
				boosterItem = new ItemStack(Material.EXP_BOTTLE);
			}else if(type == BoosterType.MCMMO) {
				boosterItem = new ItemStack(Material.DOUBLE_PLANT);
			}
			
			
			for(String boosters : RiftBoosters.getInstance().boosters.get(uuid)) {
				
				String[] s = boosters.split(":");
				ArrayList<String> lore = new ArrayList<>();
				if(type == BoosterType.valueOf(s[0])) {
					
					int seconds = Integer.valueOf(s[1]);
					double multiplier = Double.valueOf(s[2]);
					
					lore.add(Common.colorMessage("&7Seconds: &d" + Common.checkTime(seconds)));
					lore.add(Common.colorMessage("&7Multiplier: &d" + multiplier));
					
					ItemStack booster = new ItemBuilder(boosterItem.getType())
							.setDisplayName(Common.colorMessage("&d" + type.toString() + " Booster"))
							.setLore(lore)
							.setKey("type", type.toString())
							.setKey("id", boosters)
							.build();
					inv.setItem(slot, booster);
					slot++;
				}
			}
			RiftBoosters.getInstance().boosterInventory.put(player.getUniqueId(), inv);
			player.openInventory(inv);
		}
	}
	
	private static void fillEmptySlots(Inventory inv, ItemStack item) {
        for (int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                inv.setItem(i, item);
            }
        }
    }

}
