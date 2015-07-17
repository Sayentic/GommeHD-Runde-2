package com.voxelboxstudios.devathlon.listeners;

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
	}
	
}
