package me.hassan.riftboosters.events;


import me.hassan.riftboosters.RiftBoosters;
import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.utils.BoosterUtil;
import me.hassan.riftboosters.utils.Common;
import me.mraxetv.beasttokens.events.tokendrops.blocks.BTBlockTokenDropEvent;
import me.mraxetv.beasttokens.events.tokendrops.farming.BTFarmingTokenDropEvent;
import me.mraxetv.beasttokens.events.tokendrops.mobs.BTMobTokenDropEvent;
import me.mraxetv.beasttokens.events.tokendrops.mobs.BTMythicMobTokenDropEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BmEvent implements Listener {

	@EventHandler
	public void blockTokenDrop(BTBlockTokenDropEvent e){
		Player player = e.getPlayer();

		if(BoosterUtil.hasBooster(player, BoosterType.BLACKMARKET)) {
			double extra = e.getTotalTokens() * BoosterUtil.getBoosterMultiplier(player, BoosterType.BLACKMARKET) - e.getTotalTokens();
			e.setTokens(e.getTotalTokens() * BoosterUtil.getBoosterMultiplier(player, BoosterType.BLACKMARKET));

		}
	}

	@EventHandler
	public void farmTokenDrop(BTFarmingTokenDropEvent e){
		Player player = e.getPlayer();
		if(BoosterUtil.hasBooster(player, BoosterType.BLACKMARKET)) {
			double extra = e.getTotalTokens() * BoosterUtil.getBoosterMultiplier(player, BoosterType.BLACKMARKET) - e.getTotalTokens();
			e.setTokens(e.getTotalTokens() * BoosterUtil.getBoosterMultiplier(player, BoosterType.BLACKMARKET));

		}
	}

	@EventHandler
	public void mobTokenDrop(BTMobTokenDropEvent e){
		Player player = e.getPlayer();
		if(BoosterUtil.hasBooster(player, BoosterType.BLACKMARKET)) {
			double extra = e.getTotalTokens() * BoosterUtil.getBoosterMultiplier(player, BoosterType.BLACKMARKET) - e.getTotalTokens();
			e.setTokens(e.getTotalTokens() * BoosterUtil.getBoosterMultiplier(player, BoosterType.BLACKMARKET));

		}
	}

}
