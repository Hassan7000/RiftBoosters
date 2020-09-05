package me.hassan.riftboosters.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.bgsoftware.wildtools.api.events.SellWandUseEvent;


import me.hassan.riftboosters.RiftBoosters;
import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.utils.BoosterUtil;
import me.hassan.riftboosters.utils.Common;
import net.brcdev.shopgui.event.ShopPostTransactionEvent;
import net.brcdev.shopgui.event.ShopPreTransactionEvent;
import net.brcdev.shopgui.shop.ShopTransactionResult;
import net.brcdev.shopgui.shop.ShopManager.ShopAction;
import net.brcdev.shopgui.shop.ShopTransactionResult.ShopTransactionResultType;
import net.luckyfeed.events.InfiniteChestPreSellEvent;
import net.luckyfeed.events.InfiniteChestSellEvent;

public class SellEvent implements Listener {
	
	
	
	@EventHandler
	public void onTransaction(ShopPostTransactionEvent event) {
		ShopTransactionResult result = event.getResult();
		Player player = result.getPlayer();
		
		if(!(result.getResult() == ShopTransactionResultType.SUCCESS)) return;
		
		if(result.getShopAction() != ShopAction.BUY && BoosterUtil.hasBooster(player, BoosterType.SELL)) {
			double extra = result.getPrice() * BoosterUtil.getBoosterMultiplier(player, BoosterType.SELL) - result.getPrice();
			RiftBoosters.getInstance().econ.depositPlayer(player, extra);
			Bukkit.getScheduler ().runTaskLater (RiftBoosters.getInstance(), () -> Common.sendMessage(player, "&a&l+"+ "$"+ Common.formatNumbers(extra) + " " + "&7(" + BoosterUtil.getBoosterMultiplier(player, BoosterType.SELL) + "x"+ " Sell Booster Active)" ), 3);
		}
	}
	
	@EventHandler
	public void onSellWand(SellWandUseEvent e) {
		Player player = e.getPlayer();
		
		if(BoosterUtil.hasBooster(player, BoosterType.SELL)) {
			double extra = e.getPrice() * BoosterUtil.getBoosterMultiplier(player, BoosterType.SELL) - e.getPrice();
			e.setPrice(e.getPrice() * BoosterUtil.getBoosterMultiplier(player, BoosterType.SELL));
			Bukkit.getScheduler ().runTaskLater (RiftBoosters.getInstance(), () -> Common.sendMessage(player, "&a&l+"+ "$"+ Common.formatNumbers(extra) + " " + "&7(" + BoosterUtil.getBoosterMultiplier(player, BoosterType.SELL) + "x"+ " Sell Booster Active)" ), 3);
		}
	}
	
	@EventHandler
	public void onSellChest(InfiniteChestSellEvent e) {
		if(e.getRecipient().isOnline()) {
			Player player = e.getRecipient().getPlayer();
			if(BoosterUtil.hasBooster(player, BoosterType.SELL)) {
				double extra = e.getFinalPrice() * BoosterUtil.getBoosterMultiplier(player, BoosterType.SELL) - e.getFinalPrice();
				RiftBoosters.getInstance().econ.depositPlayer(player, extra);
				Bukkit.getScheduler ().runTaskLater (RiftBoosters.getInstance(), () -> Common.sendMessage(player, "&a&l+"+ "$"+ Common.formatNumbers(extra) + " " + "&7(" + BoosterUtil.getBoosterMultiplier(player, BoosterType.SELL) + "x"+ " Sell Booster Active)" ), 3);
			}
		}
	}

}
