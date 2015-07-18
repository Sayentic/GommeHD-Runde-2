package com.voxelboxstudios.util;

import org.bukkit.entity.Player;

public class Messages {

	/** Attention message **/
	
	public static void sendMessageAttention(Player p, String message){
		p.sendMessage("§8» §c" + message);
	}
	
	
	/** Info message **/
	
	public static void sendMessageInfo(Player p, String message) {
		p.sendMessage("§8» §7" + message);
	}
	
	
	/** Warning message **/
	
	public static void sendMessageWarning(Player p, String message) {
		p.sendMessage("§8» §e" + message);
	}
	
}
