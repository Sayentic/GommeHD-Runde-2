package com.voxelboxstudios.devathlon.resource;

import org.bukkit.entity.Player;

public abstract interface IPacketPlayResourcePackStatus {

	/** Inject **/
	
	public abstract void inject() throws NoSuchFieldException, IllegalAccessException;
	
	
	/** Get status **/
	
	public abstract Status getStatus();
	
	
	/** Get hash **/
	
	public abstract String getHash();
	
	
	/** Packet receive **/
	
	public abstract void onPacketReceive(Object paramObject, Player paramPlayer);
	
	
	/** Add channel for player **/
	
	public abstract void addChannelForPlayer(Player paramPlayer);
	
	
	/** Remove channel for player **/
	
	public abstract void removeChannelForPlayer(Player paramPlayer);
	
}
