package com.voxelboxstudios.devathlon.hub;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.hologram.Hologram;

public class HangarScheduler {

	/** Hangar Scheduler **/
	
	public HangarScheduler() {
		/** Config **/
		
		final FileConfiguration cfg = Main.getPlugin().getConfig();
		final Location hangar_fuel_location = new Location(Bukkit.getWorld(cfg.getString("hangar.world")), cfg.getDouble("hangarfuel.x"), cfg.getDouble("hangarfuel.y"), cfg.getDouble("hangarfuel.z"), cfg.getInt("hangarfuel.yaw"), cfg.getInt("hangarfuel.pitch"));
		
		/** Bukkit Runnable **/
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					if(p.getWorld().getName().equalsIgnoreCase(cfg.getString("hangar.world"))) {
						
						Bukkit.broadcastMessage("Test");
						
						/** Check **/
						
						if(!Main.fuel_reload.containsKey(p.getName())) {
							Main.fuel_reload.put(p.getName(), 100);
							Hologram h = new Hologram(hangar_fuel_location, "§cTreibstoff: §e100%");
							Main.fuel_holograms.put(p.getName(), h);
						} else {
							int fuel = Main.fuel_reload.get(p.getName());
							
							if(fuel < 100){
								fuel += 1;
							}
							
							Main.fuel_reload.put(p.getName(), fuel);
							Hologram h = Main.fuel_holograms.get(p.getName());
							h.hide(p);
							
							Hologram h2 = new Hologram(hangar_fuel_location, "§cTreibstoff: §e"+fuel+"%");
							h2.show(p);
							Main.fuel_holograms.put(p.getName(), h2);
							
						}
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 3L);
	}
	
}
