package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import com.voxelboxstudios.devathlon.spaceship.Spaceship;

public class ListenerKick implements Listener {

	/** Kick **/
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		/** Despawn vehicle **/
		
		Spaceship.checkDespawn(e.getPlayer());
		
		
		/** Leave message **/
		
		e.setLeaveMessage("§8» §6" + e.getPlayer().getName() + " §7hat das Spiel verlassen.");
	}
	
}
