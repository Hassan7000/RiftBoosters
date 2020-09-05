package me.hassan.riftboosters.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import me.hassan.riftboosters.RiftBoosters;
import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.utils.BoosterUtil;
import me.hassan.riftboosters.utils.Common;

public class XpEvent implements Listener {

	int amount = 0;

	@EventHandler
	public void onExperienceGain(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();
		if (BoosterUtil.hasBooster(player, BoosterType.XP)) {
			int extra = (int) (event.getAmount() * BoosterUtil.getBoosterMultiplier(player, BoosterType.SELL) - event.getAmount());
			event.setAmount((int) (event.getAmount() * BoosterUtil.getBoosterMultiplier(player, BoosterType.XP)));

			Bukkit.getScheduler().runTaskLater(RiftBoosters.getInstance(),
					() -> Common.sendMessage(player, "&a&l+" + extra + "XP " + "&7("
							+ BoosterUtil.getBoosterMultiplier(player, BoosterType.XP) + "x" + " XP Booster Active)"),
					40);

		}
	}

}
