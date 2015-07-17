package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.spaceship.Spaceship;

public class ListenerJoin implements Listener {

	/** Join **/
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		/** Player **/
		
		Player p = e.getPlayer();
		
		
		/** Teleport **/
		
		e.getPlayer().teleport(new Location(Bukkit.getWorld("map"), 100, 100, 100));
		
		
		/** Remove vehicle **/
		
		if(e.getPlayer().isInsideVehicle()) {
			e.getPlayer().getVehicle().eject();
			e.getPlayer().getVehicle().remove();
		}
		
		
		/** Spaceship **/
		
		new BukkitRunnable() {
			public void run() {
				new Spaceship(e.getPlayer());
			}
		}.runTaskLater(Main.getPlugin(), 10L);
		
		
		/** Clear Player **/
		
		p.resetMaxHealth();
		p.setHealth(20);
		
		
		/** Clear inventory **/
		
		p.getInventory().clear();
		
		
		/** Game Mode **/
		
		p.setGameMode(GameMode.ADVENTURE);
		
		
		/** Join message **/
		
		e.setJoinMessage("§8» §6" + e.getPlayer().getName() + " §7hat das Spiel betreten.");
	}
	
}
