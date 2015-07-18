package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ListenerSneak implements Listener {

	/** Sneak **/
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		/** Cancel event **/
		
		e.setCancelled(true);
	}
	
}
