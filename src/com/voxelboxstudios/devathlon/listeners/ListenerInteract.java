package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.voxelboxstudios.devathlon.spaceship.Spaceship;

public class ListenerInteract implements Listener {

	/** Interact **/
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		/** Check action **/
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			/** Find spaceship **/
			
			Spaceship s = null;
			
			for(Spaceship t : Spaceship.spaceships) {
				if(t.getPlayer() == e.getPlayer()) {
					s = t;
				}
			}
			
			if(s != null) {
				/** Play sound **/
				
				s.getMinecart().getWorld().playSound(s.getMinecart().getLocation(), Sound.SHOOT_ARROW, 1, 3);
				
				
				/** Spawn arrow **/
				
				s.getMinecart().getWorld().spawnArrow(s.getMinecart().getLocation(), s.getMinecart().getVelocity(), 12F, 0F);
			}
		}
	}
	
}