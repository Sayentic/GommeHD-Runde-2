package com.voxelboxstudios.devathlon;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.devathlon.generator.CleanGenerator;
import com.voxelboxstudios.devathlon.listeners.*;
import com.voxelboxstudios.devathlon.mysql.SQL;
import com.voxelboxstudios.devathlon.resource.IPacketPlayResourcePackStatus;
import com.voxelboxstudios.devathlon.resource.PacketPlayResourcePackStatus;
import com.voxelboxstudios.devathlon.resource.Status;
import com.voxelboxstudios.devathlon.spaceship.SpaceshipScheduler;
import com.voxelboxstudios.devathlon.worlds.Worlds;

public class Main extends JavaPlugin {

	/** Packet **/
	
	public static IPacketPlayResourcePackStatus packet;
	
	
	/** Prefix **/
	
	public static String prefix = "§8» §Aviation: §7";
	
	
	/** Plugin **/
	
	private static Main plugin;
	
	
	/** Laser damage **/
	
	private static int laserdamage;
	
	
	/** Max health **/
	
	private static int maxhealth;
	
	
	/** Explosion damage **/
	
	private static int explosiondamage;
	
	
	/** Explosion cooldown **/
	
	private static int explosioncooldown;
	
	
	/** Enable **/
	
	public void onEnable() {
		/** Plugin **/
		
		plugin = this;
		
		
		/** Packet **/
		
		packet = new PacketPlayResourcePackStatus();
		
		
		/** Inject packet **/
		
		try {
			packet.inject();
		} catch (NoSuchFieldException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		
		/** Setup config **/
		
		saveDefaultConfig();
		
		
		/** Config **/
		
		laserdamage = getConfig().getInt("laserdamage");
		maxhealth = getConfig().getInt("spaceshiphealth");
		explosiondamage = getConfig().getInt("explosiondamage");
		explosioncooldown = getConfig().getInt("explosioncooldown");
		
		
		/** Connect to database **/
		
		try {
			SQL.connect();
		} catch (ClassNotFoundException | SQLException e) {
			/** Log error **/
			
			Bukkit.getLogger().severe("Konnte keine Verbindung zur MySQL-Datenbank aufbauen!");
			
			
			/** Stop server **/
			
			Bukkit.shutdown();
			
			
			/** Return **/
			
			return;
		}
		
		
		/** Worlds **/
		
		new Worlds(getConfig().getInt("maprange"));
		
		
		/** Plugin manager **/
		
		PluginManager pm = Bukkit.getPluginManager();
		
		
		/** Register events **/
		
		pm.registerEvents(new ListenerJoin(), plugin);
		pm.registerEvents(new ListenerVehicle(), plugin);
		pm.registerEvents(new ListenerKick(), plugin);
		pm.registerEvents(new ListenerQuit(), plugin);
		pm.registerEvents(new ListenerDestroy(), plugin);
		pm.registerEvents(new ListenerDamage(), plugin);
		pm.registerEvents(new ListenerWeather(), plugin);
		pm.registerEvents(new ListenerInteract(), plugin);
		pm.registerEvents(new ListenerCreatureSpawn(), plugin);
		pm.registerEvents(new ListenerChat(), plugin);
		pm.registerEvents(new ListenerClick(), plugin);
		pm.registerEvents(new ListenerDrop(), plugin);
		pm.registerEvents(new ListenerSneak(), plugin);
		pm.registerEvents(new ListenerHeld(), plugin);
		pm.registerEvents(new ListenerFood(), plugin);
		pm.registerEvents(new ListenerBreak(), plugin);
		
		
		/** Scheduler **/
		
		new SpaceshipScheduler();
	}
	
	
	/** Disable **/
	
	public void onDisable() {
		/** Remove entities **/
		
		for(World w : Bukkit.getWorlds()) {
			for(Entity e : w.getEntities()) {
				e.remove();
			}
		}
	}
	
	
	/** Get plugin **/
	
	public static Main getPlugin() {
		return plugin;
	}
	
	
	/** Default world generator **/
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new CleanGenerator();
    }


	/** Laser damage **/
	
	public static int getLaserDamage() {
		return laserdamage;
	}


	/** Max health **/
	
	public static int getMaxHealth() {
		return maxhealth;
	}

	
	/** Explosion damage **/
	
	public static int getExplosionDamage() {
		return explosiondamage;
	}
	
	
	/** Explosion cooldown **/
	
	public static int getExplosionCooldown() {
		return explosioncooldown;
	}
	

	/** Resource pack result **/
	
	public static void resourcePackResult(final Player p, Status status) {
		/** Check status **/
		
		if(!(status == Status.SUCCESSFULLY_LOADED || status == Status.ACCEPTED)) {
			/** Kick player **/
			
			new BukkitRunnable() {
				public void run() {
					p.kickPlayer("§cBitte aktiviere Resource Packs in deinen Einstellungen.");
				}
			}.runTask(plugin);
		}
	}
}
