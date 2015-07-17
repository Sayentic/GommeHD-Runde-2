package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.voxelboxstudios.devathlon.spaceship.Spaceship;

public class ListenerQuit implements Listener {

	/** Quit **/
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		/** Despawn vehicle **/
		
		Spaceship.checkDespawn(e.getPlayer());
		
		
		/** Quit message **/
		
		e.setQuitMessage("§8» §6" + e.getPlayer().getName() + " §7hat das Spiel verlassen.");
	}
	
}
