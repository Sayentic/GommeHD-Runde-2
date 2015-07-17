package com.voxelboxstudios.devathlon.spaceship;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
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
					
					if(!s.getPlayer().isSneaking()) 
						s.getArmorStand().setVelocity(s.getPlayer().getLocation().getDirection().multiply(2.0).normalize());
					else
						s.getArmorStand().setVelocity(new Vector(0, 0, 0));
					
					
					/** Y **/
					
					if(s.getArmorStand().getLocation().getY() < 0) {
						s.getArmorStand().setVelocity(s.getArmorStand().getVelocity().add(new Vector(0, 1, 0)));
					}
					
					
					/** Direction **/
					
					Location loc = s.getArmorStand().getLocation().clone();
					
					loc.setPitch(s.getPlayer().getLocation().getPitch());
					loc.setYaw(s.getPlayer().getLocation().getYaw());
					
					s.getArmorStand().teleport(loc);
					
					
					/** Vector **/
					
					Vector vec = s.getPlayer().getLocation().getDirection();
					
					
					/** Head position **/
					
					float y = s.getPlayer().getLocation().getYaw();
					
					if(y < 0) y += 360;
					
					double r = (2 * Math.PI) * (y / 360);
					
					s.getArmorStand().setHeadPose(new EulerAngle(-vec.getY(), r, 0));
					
					
					/** Play smoke effect **/
					
					s.getArmorStand().getWorld().playEffect(s.getPlayer().getLocation(), Effect.SMOKE, 0);
					
					
					/** Check passenger **/
					
					s.getArmorStand().setPassenger(s.getPlayer());
				}

			}
		}.runTaskTimer(Main.getPlugin(), 2L, 2L);
	}
	
}
