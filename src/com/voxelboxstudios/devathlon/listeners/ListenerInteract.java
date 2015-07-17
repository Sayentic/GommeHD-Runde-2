package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.voxelboxstudios.devathlon.spaceship.Spaceship;

public class ListenerInteract implements Listener {

	
	/** Entity interact entity **/
	
	@EventHandler
	public void onEntityInteractEntityEvent(PlayerInteractAtEntityEvent e) {
		/** Check right clicked **/
		
		if(e.getRightClicked() != null) {
			/** Right clicked **/
			
			Entity en = e.getRightClicked();
			
			
			/** Check if armor stand **/
			
			if(en instanceof ArmorStand) {
				/** Cancel event **/
				
				e.setCancelled(true);
			}
		}
	}
	
	
	/** Interact **/
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		/** Find spaceship **/
			
		Spaceship s = null;
			
		for(Spaceship t : Spaceship.spaceships) {
			if(t.getPlayer() == e.getPlayer()) {
				s = t;
			}
		}
			
		if(s != null) {
			/** Play sound **/
			
			for(Player t : Bukkit.getOnlinePlayers()) t.playSound(e.getPlayer().getLocation(), "laser", 1, 3);
			
			
			/** Spawn effect **/
				
			Location start = s.getPlayer().getLocation();
			
			Vector increase = start.getDirection();
			
			for(int count = 0; count < 150; count++) {
				Location point = start.add(increase);
				
				for(int i = 0; i < 10; i++) e.getPlayer().getWorld().playEffect(point, Effect.COLOURED_DUST, 14);
			}
		}
	}
	
}