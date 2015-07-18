package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ListenerDrop implements Listener {

	/** Player drop event **/
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		/** Cancel event **/
		
		e.setCancelled(true);
	}
}
