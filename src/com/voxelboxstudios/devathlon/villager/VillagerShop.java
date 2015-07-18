package com.voxelboxstudios.devathlon.villager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

import com.voxelboxstudios.devathlon.Main;

public class VillagerShop {
	
	/** Villagers **/
	
	public static List<VillagerShop> villagers = new ArrayList<VillagerShop>();
	

	/** Location **/
	
	private Location location;
	
	
	/** Message **/
	
	private String name;
	
	
	/** Entity **/
	
	private Villager shop;
	
	
	/** Constructor **/
	
	public VillagerShop(Location location, String name) {
		/** Location **/
		
		this.location = location;
		
		
		/** Message **/
		
		this.name = name;
		
		
		/** Spawn **/
		
		shop = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
	      
		
		/** Set custom name **/
		
	    shop.setCustomName(name);
	    
	    
	    /** White villager **/
	    
	    shop.setProfession(Profession.LIBRARIAN);
	    
	    
	    /** Head block **/
	    
	    shop.getEquipment().setHelmet(new ItemStack(Material.GLASS, 1));
	    
	    
	    /** Set visible **/
	    
	    shop.setCustomNameVisible(true);
	    
	    
	    /** Set NMS **/
	    
	    VillagerUtil.setInvulnerable(shop);
	    
	    
	    /** Add self to list **/
	    
	    villagers.add(this);
	}
	
	
	/** Get location **/
	
	public Location getLocation() {
		return location;
	}
	
	
	/** Get message **/
	
	public String getName() {
		return name;
	}
	
	
	/** Spawn villagers **/
	
	public static void spawnVillagers() {
		/** Config **/
		
		FileConfiguration cfg = Main.getPlugin().getConfig();
		
		
		/** Loop **/
		
		for(int i = 1; i < 6; i++) {
			/** Name **/
			
			String name = cfg.getString("villager." + i + ".name");
			
			
			/** Position **/
			
			double x = cfg.getDouble("villager." + i + ".x");
			double y = cfg.getDouble("villager." + i + ".y");
			double z = cfg.getDouble("villager." + i + ".z");
			
			
			/** Spawn villager **/
			
			new VillagerShop(new Location(Bukkit.getWorld(cfg.getString("hangar.world")), x + 0.5, y, z + 0.5, 180, 0), ChatColor.translateAlternateColorCodes('&', name));
		}
	}
		
}