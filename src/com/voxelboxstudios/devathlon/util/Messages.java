package com.voxelboxstudios.devathlon.util;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;

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
	
	
	/** Send title **/
	
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		/** Connection **/
		
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
	    
		
		/** Play out title **/
		
	    PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
	    
	    
	    /** Send packet **/
	    
	    connection.sendPacket(packetPlayOutTimes);
	    
	    
	    /** Check subtitle **/
	    
	    if(subtitle != null) {
	    	/** Base component **/
	    	
	    	IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
	    	
	    	
	    	/** Sub title **/
	    	
	    	PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
	    	
	    	
	    	/** Send packet **/
	    	
	    	connection.sendPacket(packetPlayOutSubTitle);
	    } 
	    
	    
	    /** Check title **/
	    
	    if(title != null) {
	    	/** Base component **/
	    	
	    	IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
	    	
	    	
	    	/** Play out title **/
	    	
	    	PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
	    	
	    	
	    	/** Send title **/
	    	
	    	connection.sendPacket(packetPlayOutTitle);
	    }
	    
	}
	
}
