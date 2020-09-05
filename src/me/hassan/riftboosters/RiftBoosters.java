package me.hassan.riftboosters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import me.hassan.riftboosters.boostertype.BoosterType;
import me.hassan.riftboosters.events.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.hassan.riftboosters.boosterdata.BoosterData;
import me.hassan.riftboosters.boostertask.BoosterTask;
import me.hassan.riftboosters.command.BoosterCommand;

import net.milkbowl.vault.economy.Economy;

public class RiftBoosters extends JavaPlugin {
	
	private static RiftBoosters instance;
	public HashMap<String, BoosterData> data = new HashMap<>();
	public HashMap<String, List<String>> boosters = new HashMap<>();
	public HashMap<UUID, Inventory> mainInventory = new HashMap<>();
	public HashMap<UUID, Inventory> boosterInventory = new HashMap<>();
	public HashMap<UUID, List<String>> disabledBoosters = new HashMap<>();
	public Economy econ = null;
	private BoosterTask task;
	
	private File boosterFile = new File(getDataFolder(), "BoosterData.dat");
	private File dataFile = new File(getDataFolder(), "Data.dat");
	
	public void onEnable() {
		instance = this;
		this.saveDefaultConfig();
		this.setupEconomy();
		Bukkit.getPluginManager().registerEvents(new McmmoEvent(), this);
		Bukkit.getPluginManager().registerEvents(new SellEvent(), this);
		Bukkit.getPluginManager().registerEvents(new XpEvent(), this);
		Bukkit.getPluginManager().registerEvents(new BmEvent(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryEvent(), this);
		getCommand("boosters").setExecutor(new BoosterCommand());
		
		if(!dataFile.exists()) {
			 try {
				 dataFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
		data = (HashMap<String, BoosterData>) load(dataFile);
		 
		 if(data == null) {
			 data = new HashMap<String, BoosterData>();
		 }
		 
		 if(!boosterFile.exists()) {
			 try {
				 boosterFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
		 boosters = (HashMap<String, List<String>>) load(boosterFile);
		 
		 if(boosters == null) {
			 boosters = new HashMap<String, List<String>>();
		 }
		
		task = new BoosterTask();
		task.startGeneratorTask();
	}
	
	public void onDisable() {
		if(task != null){
			task.cancelTask();
		}
		removeUselessBoosters();
		
		if(dataFile.exists()) {
			 this.save(dataFile, data);
		 }
		
		if(boosterFile.exists()) {
			this.save(boosterFile, boosters);
		}
		
		instance = null;
	}
	
	public static RiftBoosters getInstance() { return instance; }


	private void removeUselessBoosters(){
		for(Map.Entry<UUID, List<String>> entry : disabledBoosters.entrySet()){
			if(data.containsKey(entry.getKey().toString())){
				for(String boosters : entry.getValue()){
					data.get(entry.getKey().toString()).removeBooster(boosters);
				}
			}
		}


	}

	private void save(File f, Object object) {
		 if(f.exists()) {
			 try {			 
				 ObjectOutputStream dataa = new ObjectOutputStream(new FileOutputStream(f));
				 dataa.writeObject(object);
				 dataa.flush();
				 dataa.close();
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }
	 }
	 
	 private Object load(File f) {
		 try {
			 ObjectInputStream data = new ObjectInputStream(new FileInputStream(f));
			 Object result = data.readObject();
			 data.close();
			 return result;
		 } catch (Exception e) {
			return null;
		 }
	 }
	 
	 private boolean setupEconomy() {
			if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
				return false;
			} else {
				RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
				if (rsp == null) {
					return false;
				} else {
					econ = (Economy) rsp.getProvider();
					return econ != null;
				}
			}
		}

}
