package me.hassan.riftboosters.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.hassan.riftboosters.RiftBoosters;
import me.hassan.riftboosters.boosterdata.BoosterData;
import me.hassan.riftboosters.boostertype.BoosterState;
import me.hassan.riftboosters.boostertype.BoosterType;

public class BoosterUtil {

	public static boolean hasBooster(Player player, BoosterType type) {

		if (RiftBoosters.getInstance().data.containsKey(player.getUniqueId().toString())) {

			BoosterData data = RiftBoosters.getInstance().data.get(player.getUniqueId().toString());

			for (String booster : data.getBoosters()) {
				String[] s = booster.split(":");
				if(getBoosterSeconds(player,type) > 0) {
					if (type == BoosterType.valueOf(s[0])) {
						return true;
					} else {
						return false;
					}
				}

			}
		} else {
			return false;
		}
		return false;
	}

	public static int getBoosterSeconds(Player player, BoosterType type) {
		if (RiftBoosters.getInstance().data.containsKey(player.getUniqueId().toString())) {
			BoosterData data = RiftBoosters.getInstance().data.get(player.getUniqueId().toString());

			for (String booster : data.getBoosters()) {
				String[] s = booster.split(":");

				if (type == BoosterType.valueOf(s[0])) {
					return Integer.valueOf(s[1]);
				} else {
					return 0;
				}
			}
		}
		return 0;
	}

	public static double getBoosterMultiplier(Player player, BoosterType type) {
		if (RiftBoosters.getInstance().data.containsKey(player.getUniqueId().toString())) {
			BoosterData data = RiftBoosters.getInstance().data.get(player.getUniqueId().toString());

			for (String booster : data.getBoosters()) {
				String[] s = booster.split(":");
				if (hasBooster(player, type)) {
					if(getBoosterSeconds(player,type) > 0) {
						return Double.valueOf(s[2]);
					}
				}

			}
		}
		return 0.0;
	}

	public static String getBooster(Player player, BoosterType type) {
		if (RiftBoosters.getInstance().data.containsKey(player.getUniqueId().toString())) {
			BoosterData data = RiftBoosters.getInstance().data.get(player.getUniqueId().toString());

			for (String booster : data.getBoosters()) {
				String[] s = booster.split(":");

				if (type == BoosterType.valueOf(s[0])) {
					return booster;
				} else {
					return "";
				}
			}
		}
		return "";
	}

	public static void setSeconds(Player player, BoosterType type, int seconds) {
		List<String> boosters = new ArrayList<>();
		if (hasBooster(player, type)) {

			String booster = getBooster(player, type);

			if (booster.equalsIgnoreCase(""))
				return;

			String[] s = booster.split(":");
			BoosterData data = RiftBoosters.getInstance().data.get(player.getUniqueId().toString());


			if (seconds > 0) {

				data.removeBooster(booster);

				String newBooster = type.toString() + ":" + String.valueOf(seconds - 1) + ":" + String.valueOf(s[2]);

				data.addBooster(newBooster);

			} else {
				if (seconds == 0) {
					boosters.add(booster);
					Common.sendMessage(player, "&d&lRift&5&lMC &8>> &d Your " + type.toString() + " booster has ran out.");
					Common.sendMessage(player, "&d&lRift&5&lMC &8>> &d You can now active a new booster");
					data.removeBooster(booster);






				}
			}

		}
	}

	public static boolean acitveBooster(Player player, String id, BoosterType type) {
		if(RiftBoosters.getInstance().boosters.containsKey(player.getUniqueId().toString())) {
			
			if(RiftBoosters.getInstance().data.containsKey(player.getUniqueId().toString())) {
				
				if(!hasBooster(player, type)) {
					
					RiftBoosters.getInstance().data.get(player.getUniqueId().toString()).addBooster(id);
					
					if(RiftBoosters.getInstance().boosters.get(player.getUniqueId().toString()).contains(id)) {
						RiftBoosters.getInstance().boosters.get(player.getUniqueId().toString()).remove(id);
					}
					
					return true;
				}else {
					return false;
				}
			}else {
				List<String> boosters = new ArrayList<>();
				boosters.add(id);
				RiftBoosters.getInstance().data.put(player.getUniqueId().toString(), new BoosterData(player.getUniqueId().toString(), boosters));
				if(RiftBoosters.getInstance().boosters.get(player.getUniqueId().toString()).contains(id)) {
					RiftBoosters.getInstance().boosters.get(player.getUniqueId().toString()).remove(id);
				}
				return true;
			}
			
		}
		return false;
	}
		

}
