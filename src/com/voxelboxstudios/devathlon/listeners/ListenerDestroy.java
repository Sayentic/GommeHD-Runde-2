package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class ListenerDestroy implements Listener {

	/** Destroy **/
	
	@EventHandler
	public void onDestroy(VehicleDestroyEvent e) {
		/** Cancel event **/
		
		e.setCancelled(true);
	}
	
}
