package me.hassan.riftboosters.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;

import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.utils.BoosterUtil;

public class McmmoEvent implements Listener {
	
	@EventHandler
	public void mcmmoXpGain(McMMOPlayerXpGainEvent e) {
		Player player = e.getPlayer();
		float xp = e.getRawXpGained();
		if(BoosterUtil.hasBooster(player, BoosterType.MCMMO)) {
			
			e.setRawXpGained((float) (xp * BoosterUtil.getBoosterMultiplier(player, BoosterType.MCMMO)));
		}

	}

}
