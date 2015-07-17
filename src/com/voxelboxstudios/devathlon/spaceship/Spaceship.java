package com.voxelboxstudios.devathlon.spaceship;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Spaceship {

	/** Spaceships **/
	
	public static List<Spaceship> spaceships = new ArrayList<Spaceship>();
	
	
	/** Player **/
	
	private Player p;
	
	
	/** Minecart **/
	
	private Minecart mc;
	
	
	/** Constructor **/
	
	public Spaceship(Player p) {
		/** Player **/
		
		this.p = p;
		
		
		/** Spawn spaceship **/
		
		spawn();
		
		
		/** Add spaceship **/
		
		spaceships.add(this);
	}
	
	
	/** Spawn **/
	
	private void spawn() {
		/** Minecart **/
		
		mc = (Minecart) p.getWorld().spawnEntity(p.getLocation().clone().add(0, 1, 0), EntityType.MINECART);
		
		
		/** Offset **/
		
		mc.setDisplayBlockOffset(-1);
		
		/** Block **/
		
		mc.setDisplayBlock(new ItemStack(Material.SPONGE, 1).getData());
		
		
		/** Passenger **/
		
		mc.setPassenger(p);
	}
	
	
	/** Despawn **/
	
	public void despawn() {
		/** Remove minecart **/
		
		mc.remove();
	
		
		/** Remove spaceship **/
		
		spaceships.remove(this);
	}
	
	
	/** Get minecart **/
	
	public Minecart getMinecart() {
		return mc;
	}
	
	
	/** Get player **/
	
	public Player getPlayer() {
		return p;
	}


	/** Check despawn **/
	
	public static void checkDespawn(Player player) {
		/** Find spaceship **/
		
		for(Spaceship s : new ArrayList<Spaceship>(spaceships)) {
			if(s.getPlayer() == player) {
				/** Despawn spaceship **/
				
				s.despawn();
			}
		}
	}
	
}
