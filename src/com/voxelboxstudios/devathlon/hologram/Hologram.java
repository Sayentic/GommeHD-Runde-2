package com.voxelboxstudios.devathlon.hologram;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Hologram {

	/** Location **/
	
	private Location location;
	
	
	/** Message **/
	
	private String message;
	
	
	/** Entity **/
	
	private EntityArmorStand as;
	
	
	/** Constructor **/
	
	public Hologram(Location location, String message) {
		/** Location **/
		
		this.location = location;
		
		
		/** Message **/
		
		this.message = message;
		
		
		/** Armor stand **/
		
		as = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle());
	      
		
		/** Set custom name **/
		
	    as.setCustomName(message);
	    
	    
	    /** Set visible **/
	    
	    as.setCustomNameVisible(true);
	    
	    
	    /** Set invisible **/
	    
	    as.setInvisible(true);
	    
	    
	    /** Set gravity **/
	    
	    as.setGravity(false);
	    
	    
	    /** Set location **/
	    
	    as.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
	}
	
	
	/** Get location **/
	
	public Location getLocation() {
		return location;
	}
	
	
	/** Get message **/
	
	public String getMessage() {
		return message;
	}
	
	
	/** Spawn **/
	
	public void show(Player p) {
	    /** Packet **/
	      
	    PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(as);
	    
	    
	    /** Send packet **/
	    
	    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
	
	/** Hide **/
	
	public void hide(Player p) {
		/** Packet **/
	      
	    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(as.getId());
	      
	      
	    /** Send packet **/
	    
	    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
}
