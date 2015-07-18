package com.voxelboxstudios.devathlon.spaceship;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.voxelboxstudios.devathlon.Main;

public class Spaceship {

	/** Spaceships **/
	
	public static List<Spaceship> spaceships = new ArrayList<Spaceship>();
	
	
	/** Player **/
	
	private Player p;
	
	
	/** Armor Stand **/
	
	private ArmorStand as;
	
	
	/** Fuel **/
	
	private int fuel;
	
	
	/** Health **/
	
	private int health;
	
	
	/** Constructor **/
	
	public Spaceship(Player p) {
		/** Player **/
		
		this.p = p;
		
		
		/** Fuel **/
		
		this.fuel = 100;
		
		
		/** Health **/
		
		this.health = Main.getMaxHealth();
		
		
		/** Spawn spaceship **/
		
		spawn();
		
		
		/** Add spaceship **/
		
		spaceships.add(this);
	}
	
	
	/** Get health **/
	
	public int getHealth() {
		return health;
	}
	
	
	/** Set health **/
	
	public void damage(int damage) {
		/** Health **/
		
		health -= damage;
		
		if(health < 0) health = 0;
		
		
		/** Play sound **/
		
		for(Player p : Bukkit.getOnlinePlayers()) p.playSound(as.getLocation(), "hit", 1, 1);
		
		
		/** Explosion effect **/
		
		as.getWorld().playEffect(as.getLocation(), Effect.EXPLOSION_LARGE, 0);
	}
	
	
	/** Get fuel **/
	
	public int getFuel() {
		return fuel;
	}
	
	
	/** Set fuel **/
	
	public void setFuel(int fuel) {
		this.fuel = fuel;
	}
	
	
	/** Spawn **/
	
	private void spawn() {
		/** Armor Stand **/
		
		as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().clone().add(0, 1, 0), EntityType.ARMOR_STAND);
		
		
		/** Gravity **/
		
		as.setGravity(true);
		
		
		/** Plate **/
		
		as.setBasePlate(false);
		
		
		/** Arms **/
		
		as.setArms(false);
		
		
		/** Set helmet **/
		
		as.getEquipment().setHelmet(new ItemStack(Material.SPONGE));
		
		
		/** Invisibility **/
		
		as.setVisible(false);
		
		
		/** Passenger **/
		
		as.setPassenger(p);
	}
	
	
	/** Despawn **/
	
	public void despawn() {
		/** Remove minecart **/
		
		as.remove();
	
		
		/** Remove spaceship **/
		
		spaceships.remove(this);
	}
	
	
	/** Get armor stand **/
	
	public ArmorStand getArmorStand() {
		return as;
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
