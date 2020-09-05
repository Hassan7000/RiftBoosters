package me.hassan.riftboosters.boostertask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.hassan.riftboosters.utils.Common;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.hassan.riftboosters.RiftBoosters;
import me.hassan.riftboosters.boosterdata.BoosterData;
import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.utils.BoosterUtil;



public class BoosterTask {
	
	BukkitTask task;
	
	public void startGeneratorTask(){
		 task = new BukkitRunnable() {
           public void run() {
        	   for(Map.Entry<String, BoosterData> entry : RiftBoosters.getInstance().data.entrySet()){
        		   
        		   Player player = Bukkit.getPlayer(UUID.fromString(entry.getKey()));
        		   
        		   if(player != null) {
        			   if(entry.getValue().getBoosters().size() > 0) {
        				   for(String boosters : entry.getValue().getBoosters()) {
							   String[] s = boosters.split(":");
							   BoosterType type = BoosterType.valueOf(s[0].toUpperCase());

							   if(BoosterUtil.getBoosterSeconds(player,type) > 0){
								   BoosterUtil.setSeconds(player, type, BoosterUtil.getBoosterSeconds(player, type));
							   }else{
								   if(BoosterUtil.getBoosterSeconds(player,type) == 0){
									   Common.sendMessage(player, "&d&lRift&5&lMC &8>> &d Your " + type.toString() + " booster has ran out.");
									   Common.sendMessage(player, "&d&lRift&5&lMC &8>> &d You can now active a new booster");
									   removeUselessBoosters(player);


								   }


							   }
                		   }   
        			   }
            		    
        		   }
        		   
        	   }



           }
		}.runTaskTimer(RiftBoosters.getInstance(), 40, 20);
   }

   private void removeUselessBoosters(Player player){
		if(RiftBoosters.getInstance().data.containsKey(player.getUniqueId().toString())){
			List<String> nBoosters = new ArrayList<>();
			for(String boosters : RiftBoosters.getInstance().data.get(player.getUniqueId().toString()).getBoosters()){
				String[] s = boosters.split(":");
				BoosterType type = BoosterType.valueOf(s[0].toUpperCase());
				if(BoosterUtil.getBoosterSeconds(player,type) > 0){
					nBoosters.add(boosters);
				}
			}
			RiftBoosters.getInstance().data.get(player.getUniqueId().toString()).setBoosters(nBoosters);
		}
   }

	public void cancelTask() {
		task.cancel();
	}

}
