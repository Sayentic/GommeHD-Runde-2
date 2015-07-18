package com.voxelboxstudios.devathlon.actionbar;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ActionBar {

	/** Send action bar **/
	
	public static void sendActionBar(Player p, String message) {
		/** Craft player **/
		
		CraftPlayer cp = (CraftPlayer) p;
		
		
		/** Base component **/
		
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		
		
		/** Packet **/
		
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		
		/** Send packet **/
		
		cp.getHandle().playerConnection.sendPacket(ppoc);
	}
	
}
