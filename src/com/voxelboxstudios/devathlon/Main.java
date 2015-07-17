package com.voxelboxstudios.devathlon;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.voxelboxstudios.devathlon.generator.CleanGenerator;
import com.voxelboxstudios.devathlon.listeners.*;
import com.voxelboxstudios.devathlon.mysql.SQL;
import com.voxelboxstudios.devathlon.spaceship.SpaceshipScheduler;
import com.voxelboxstudios.devathlon.worlds.Worlds;

public class Main extends JavaPlugin {

	/** Plugin **/
	
	private static Main plugin;
	
	
	/** Enable **/
	
	public void onEnable() {
		/** Plugin **/
		
		plugin = this;
		
		
		/** Setup config **/
		
		saveDefaultConfig();
		
		
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
		
		new Worlds();
		
		
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
		
		
		/** Scheduler **/
		
		new SpaceshipScheduler();
	}
	
	
	/** Disable **/
	
	public void onDisable() {
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
}
