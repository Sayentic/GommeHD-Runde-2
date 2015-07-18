package com.voxelboxstudios.devathlon.hub;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.devathlon.Main;

public class HangarScheduler {

	/** Hangar Scheduler **/
	
	public HangarScheduler() {
		/** Config **/
		
		FileConfiguration cfg = Main.getPlugin().getConfig();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					if(p.getWorld().getName().equalsIgnoreCase(cfg.getString())) {

						
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 3L);
		
	}
	
}
