package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class ListenerVehicle implements Listener {

	/** Vehicle exit **/
	
	@EventHandler
	public void onVehicle(VehicleExitEvent e) {
		/** Cancel event **/
		
		e.setCancelled(true);
	}
	
}
