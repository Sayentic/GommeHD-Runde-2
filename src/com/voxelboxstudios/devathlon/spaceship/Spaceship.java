package com.voxelboxstudios.devathlon.spaceship;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.aura.Aura;
import com.voxelboxstudios.devathlon.inventorys.Inventorys;
import com.voxelboxstudios.devathlon.mysql.SQL;
import com.voxelboxstudios.devathlon.util.Messages;
import com.voxelboxstudios.devathlon.util.Util;

public class Spaceship {

	/** Spaceships **/
	
	public static List<Spaceship> spaceships = new ArrayList<Spaceship>();
	
	
	/** Player **/
	
	private Player p;
	
	
	/** Armor Stand **/
	
	private ArmorStand as;
	
	
	/** Fuel **/
	
	private int fuel;
	
	
	/** Destroy seconds **/
	
	private int respawn_seconds;
	
	
	/** Health **/
	
	private int health;


	/** Extra fuel **/
	
	private int extrafuel;
	
	
	/** Speed **/
	
	private int speed;
	
	
	/** Damage **/
	
	private int damage;
	
	
	/** Resistance **/
	
	private int resistance;
	
	
	/** Constructor **/
	
	public Spaceship(Player p) {
		/** Player **/
		
		this.p = p;
		
		
		/** Extra fuel **/
		
		try {
			this.extrafuel = SQL.getDatabase().getQuery("SELECT fuel FROM " + SQL.prefix + "upgrades WHERE uuid='" + p.getUniqueId().toString() + "'").getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			this.speed = SQL.getDatabase().getQuery("SELECT speed FROM " + SQL.prefix + "upgrades WHERE uuid='" + p.getUniqueId().toString() + "'").getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			this.damage = SQL.getDatabase().getQuery("SELECT damage FROM " + SQL.prefix + "upgrades WHERE uuid='" + p.getUniqueId().toString() + "'").getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			this.resistance = SQL.getDatabase().getQuery("SELECT resistance FROM " + SQL.prefix + "upgrades WHERE uuid='" + p.getUniqueId().toString() + "'").getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		/** Fuel **/
		
		this.fuel = 100 + extrafuel;
		
		
		/** Set destroy time **/
		
		this.respawn_seconds = 5;
		
		
		/** Health **/
		
		this.health = Main.getMaxHealth();
		
		
		/** Spawn spaceship **/
		
		spawn();
		
		
		/** Add spaceship **/
		
		spaceships.add(this);
	}
	
	
	/** Get speed **/
	
	public int getSpeed() {
		return speed;
	}
	
	
	/** Resistance **/
	
	public int getResistance() {
		return resistance;
	}
	
	
	/** Damage **/
	
	public int getDamage() {
		return damage;
	}
	
	
	/** Get extra fuel **/
	
	public int getExtraFuel() {
		return extrafuel;
	}
	
	
	/** Get health **/
	
	public int getHealth() {
		return health;
	}
	
	
	/** Set health **/
	
	public void damage(int damage, Player damager) {
		/** Health **/
		
		health -= damage;
		
		if(health < 0) {
			/** Health **/
			
			health = 0;
			
			
			/** Destroy **/
			
			destroyed(damager);
		}
		
		
		/** Play sound **/
		
		for(Player p : Bukkit.getOnlinePlayers()) p.playSound(as.getLocation(), "hit", 1, 1);
		
		
		/** Explosion effect **/
		
		as.getWorld().playEffect(as.getLocation(), Effect.EXPLOSION_LARGE, 0);
	}
	
	
	/** Destroyed **/
	
	public void destroyed(Player damager) {
		/** Despawn **/
		
		despawn();
		
		
		/** Create Firework **/
		
		Util.spawnFirework(p.getLocation());
		
		
		/** Aura **/
		
		if(damager != null){ 
			/** Broadcast **/
			
			Bukkit.broadcastMessage("§8» §e" + p.getName() + "§7 wurde von §e" + damager.getName() + " gekillt.");
			
			
			/** Gain aura **/
			
			Aura.gain(damager, Main.getKillAura());
		} else {
			Bukkit.broadcastMessage("§8» §e" + p.getName()+"§7 ist gestorben.");
		}
		
		
		/** Set gamemode **/
		
		p.setGameMode(GameMode.SPECTATOR);
		
		
		/** Bukkit runnable **/
		
		new BukkitRunnable() {
			@Override
			public void run() {		
				/** Send title **/
				
				Messages.sendTitle(p, 0, 22, 0, "", "§cRespawning in §e" + respawn_seconds);
				
				
				/** Play sound **/
				
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 3);
				
				
				/** Seconds until respawn **/
				
				if(respawn_seconds > 0) {
					respawn_seconds--;
				} else {
					/** Create spaceship **/
					
					Spaceship.createSpaceship(p);
					
					
					/** Cancel task **/
					
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 20l, 20l);
		
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
		
		
		/** Turn legs up **/
		
		as.setLeftLegPose(new EulerAngle(0, 0, Math.PI / 2));
		as.setRightLegPose(new EulerAngle(0, 0, Math.PI / 2));
		
		
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
	
	/** Set player in Ship **/
	
	public static void createSpaceship(final Player p) {
		/** Clear inventory **/
		
		p.getInventory().clear();
		
		
		/** Inventory **/
		
		Inventorys.equip(p);
		
		
		/** Potions **/
		
		for(PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
		
		
		/** Gamemode **/
		
		p.setGameMode(GameMode.ADVENTURE);
		
		
		/** Teleport **/
		
		Util.teleportRandom(p);
		
		
		/** Spaceship **/
		
		new BukkitRunnable() {
			public void run() {
				new Spaceship(p);
			}
		}.runTaskLater(Main.getPlugin(), 10L);
	}
}
