package me.hassan.riftboosters.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import me.hassan.riftboosters.RiftBoosters;
import me.hassan.riftboosters.boosterdata.BoosterData;
import me.hassan.riftboosters.boosterinventory.BoosterMainInventory;
import me.hassan.riftboosters.boostertype.BoosterState;
import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.utils.BoosterUtil;
import me.hassan.riftboosters.utils.Common;

public class BoosterCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 5) {
			if(args[0].equalsIgnoreCase("give")) {
				Player target = Bukkit.getPlayer(args[1]);
				if(target != null) {
					
					if(sender.hasPermission("riftboosters.admin")) {
						BoosterType type = BoosterType.valueOf(args[2].toUpperCase());
						
						
						int seconds = Integer.valueOf(args[3]);
						double multiplier = Double.valueOf(args[4]);
						if(RiftBoosters.getInstance().boosters.containsKey(target.getUniqueId().toString())) {
							String booster = type.toString() + ":" + String.valueOf(seconds) + ":" + multiplier;
							RiftBoosters.getInstance().boosters.get(target.getUniqueId().toString()).add(booster);
							Common.sendMessage(target, "&d&lRift&5&lMC &8>> " + "&aYou have redeemed a " + type.toString() + " booster Voucher. Do /boosters to view your boosters");
						}else {
							List<String> boosters = new ArrayList<>();
							String booster = type.toString() + ":" + String.valueOf(seconds) + ":" + multiplier;
							boosters.add(booster);
							RiftBoosters.getInstance().boosters.put(target.getUniqueId().toString(), boosters);
							
							Common.sendMessage(target, "&d&lRift&5&lMC &8>> " + "&aYou have redeemed a " + type.toString() + " booster Voucher. Do /boosters to view your boosters");
						}
					}else {
						sender.sendMessage(ChatColor.RED + " You don't have permission to use this command");
					}
					
					
					
				}
			}
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("active")) {
				Player player = (Player) sender;
				if(RiftBoosters.getInstance().data.containsKey(player.getUniqueId().toString())) {
					BoosterData data = RiftBoosters.getInstance().data.get(player.getUniqueId().toString());

					if(data.getBoosters().size() > 0){
						Common.sendMessage(player, " &8&l---&d&lBoosters&8&l--- ");

						for(String boosters : RiftBoosters.getInstance().data.get(player.getUniqueId().toString()).getBoosters()) {
							String[] s = boosters.split(":");
							BoosterType type = BoosterType.valueOf(s[0]);
							int seconds = Integer.valueOf(s[1]);

							if(seconds == 0) continue;

							double multiplier = Double.valueOf(s[2]);
							Common.sendMessage(player, "");

							Common.sendMessage(player, "&7Booster: &d" + type.toString());
							Common.sendMessage(player, "&7Seconds: &d" + Common.checkTime(seconds));
							Common.sendMessage(player, "&7Multiplier: &d" + multiplier);

							Common.sendMessage(player, "");
						}

						Common.sendMessage(player, " &8&l---&d&lBoosters&8&l--- ");
					}else{
						Common.sendMessage(player, "&cYou don't have any boosters active");
					}

					
				}else {
					Common.sendMessage(player, "&cYou don't have any boosters active");
				}
			}
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("list")) {
				Player player = (Player) sender;
				BoosterMainInventory.createMainMenu(player);
				
			}
		}
		if(args.length == 0) {
			Player player = (Player) sender;
			BoosterMainInventory.createMainMenu(player);
		}
		
		return false;
	}

	

}
