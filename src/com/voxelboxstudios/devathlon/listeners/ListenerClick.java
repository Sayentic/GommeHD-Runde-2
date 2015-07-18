package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ListenerClick implements Listener {

	/** Click **/
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		e.setCancelled(true);
	}
	
}
