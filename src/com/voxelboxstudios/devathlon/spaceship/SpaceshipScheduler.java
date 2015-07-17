package com.voxelboxstudios.devathlon.spaceship;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.voxelboxstudios.devathlon.Main;

public class SpaceshipScheduler {

	/** Constructor **/
	
	public SpaceshipScheduler() {
		/** Bukkit runnable **/
		
		new BukkitRunnable() {
			public void run() {
				/** Loop through spaceships **/
				
				for(Spaceship s : Spaceship.spaceships) {
					/** Set velocity **/
					
					s.getMinecart().setDerailedVelocityMod(s.getPlayer().getLocation().getDirection());
					
					if(!s.getPlayer().isSneaking()) 
						s.getMinecart().setVelocity(s.getPlayer().getLocation().getDirection().multiply(2.0).normalize());
					else
						s.getMinecart().setVelocity(new Vector(0, 0, 0));
				}

			}
		}.runTaskTimer(Main.getPlugin(), 3L, 3L);
	}
	
}
