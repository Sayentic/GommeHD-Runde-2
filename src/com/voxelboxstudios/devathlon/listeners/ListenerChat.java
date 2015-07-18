package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerChat implements Listener {
	
	/** Chat **/
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		/** Set format **/
		
		e.setFormat("§7%s §8» §f%s");
	}

}
