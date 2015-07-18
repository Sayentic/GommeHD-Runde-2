package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ListenerHeld implements Listener {

	/** Item held **/
	
	@EventHandler
	public void onHeld(PlayerItemHeldEvent e) {
		if(e.getPreviousSlot() == 1 && e.getNewSlot() == 2) {
			e.setCancelled(true);
			
			e.getPlayer().getInventory().setHeldItemSlot(8);
		} else if(e.getPreviousSlot() == 8 && e.getNewSlot() == 7) {
			e.setCancelled(true);
			
			e.getPlayer().getInventory().setHeldItemSlot(1);
		}
		
		if(e.getNewSlot() > 1 && e.getNewSlot() < 8) e.setCancelled(true);
	}
	
}
