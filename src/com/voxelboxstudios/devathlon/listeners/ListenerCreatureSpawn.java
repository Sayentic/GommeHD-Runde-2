package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class ListenerCreatureSpawn implements Listener {
	
	/** Creature spawn **/
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		/** Cancel creature spawn event **/
		
		if(!(e.getEntity() instanceof ArmorStand || e.getEntity() instanceof Villager)) {
			e.setCancelled(true);
		}
	}

}
