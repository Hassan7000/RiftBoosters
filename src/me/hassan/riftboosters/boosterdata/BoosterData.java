package me.hassan.riftboosters.boosterdata;

import java.io.Serializable;
import java.util.List;

public class BoosterData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String player;
	private List<String> boosters;
	public BoosterData(String player, List<String> boosters) {
		this.player = player;
		this.boosters = boosters;
	}
	
	public String getPlayer() {return player;}
	
	public List<String> getBoosters(){ return boosters; }
	
	public void addBooster(String booster) {
		this.boosters.add(booster);
	}
	public void setBoosters(List<String> boosters){
		this.boosters = boosters;
	}
	
	public void removeBooster(String booster) {
		this.boosters.remove(booster);
	}

}
