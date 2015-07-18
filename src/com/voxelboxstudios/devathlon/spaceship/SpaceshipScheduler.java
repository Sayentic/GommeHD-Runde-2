package com.voxelboxstudios.devathlon.spaceship;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.actionbar.ActionBar;

public class SpaceshipScheduler {

	/** Constructor **/
	
	public SpaceshipScheduler() {
		/** Ticks **/
		
		int tickrateGame = Main.getPlugin().getConfig().getInt("gametickrate");
		int tickrateFuel = Main.getPlugin().getConfig().getInt("fueltickrate");
		
		
		/** Bukkit runnable **/
		
		new BukkitRunnable() {
			public void run() {
				/** Loop through spaceships **/
				
				for(Spaceship s : Spaceship.spaceships) {
					/** Set velocity **/
					
					Vector v = s.getPlayer().getLocation().getDirection().multiply(2.0).normalize();
					
					if(!s.getPlayer().isSneaking()) 
						if(s.getFuel() > 0) s.getArmorStand().setVelocity(v);
					else {
						s.getArmorStand().setVelocity(new Vector(0, 0, 0));
						v = new Vector(0, 0, 0);
					}
					
					
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
					
					if(s.getFuel() > 0) s.getArmorStand().getWorld().playEffect(s.getPlayer().getLocation(), Effect.SMOKE, 0);
					
					
					/** Play sound **/
					
					float b = (float) v.getY();
					
					b += 0.8;
					
					if(b < 0.4f) b = 0.4f;
					
					if(s.getFuel() > 0) for(Player t : Bukkit.getOnlinePlayers()) t.playSound(s.getArmorStand().getLocation(), "jet", 1, b);
					
					
					/** Check passenger **/
					
					s.getArmorStand().setPassenger(s.getPlayer());
					
					
					/** Set exp **/
					
					s.getPlayer().setExp((float) s.getHealth() / (float) Main.getMaxHealth());
				}
			}
		}.runTaskTimer(Main.getPlugin(), tickrateGame, tickrateGame);
		
		
		/** Bukkit runnable **/
		
		new BukkitRunnable() {
			int tick = 0;
			
			public void run() {
				/** Loop through spaceships **/
				
				for(Spaceship s : Spaceship.spaceships) {
					/** Fuel **/
					
					if(s.getFuel() > 0) s.setFuel(s.getFuel() - 1);
					
					
					/** Fuel **/
					
					String ss = "";
					
					int num = 32;
					
					int after = (int) (((float) s.getFuel() / 100f) * num);
					
					if(after < 0) after = 0;
					if(after > num) after = num;
					
					for(int i = 0; i < after; i++) {
						ss = ss + "|";
					}
					
					ss = ss + "§c";
					
					for(int i = after; i < num; i++) {
						ss = ss + "|";
					}
					
					ss = "§a" + ss;
					
					
					/** Action bar **/
					
					if(after > 0) {
						ActionBar.sendActionBar(s.getPlayer(), ss);
					} else {
						if(tick % 2 == 0) {
							/** Send action bar **/
							
							ActionBar.sendActionBar(s.getPlayer(), "§cKein Treibstoff!");
							
							
							/** Play sound **/
							
							s.getPlayer().playSound(s.getPlayer().getLocation(), Sound.NOTE_PLING, 1, 0.5f);
						} else {
							ActionBar.sendActionBar(s.getPlayer(), "");
						}
					}
				}
				
				
				/** Tick **/
				
				tick++;
			}
		}.runTaskTimer(Main.getPlugin(), tickrateFuel, tickrateFuel);
	}
	
}
