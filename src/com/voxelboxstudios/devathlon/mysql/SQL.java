package com.voxelboxstudios.devathlon.mysql;

import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;

import com.voxelboxstudios.devathlon.Main;

public class SQL {

	/** Database **/
	
	private static Database db;
	
	
	/** Prefix **/
	
	public static String prefix;
	
	
	/** Connect **/
	
	public static void connect() throws ClassNotFoundException, SQLException {
		/** Get file configuration from main class **/
		
		FileConfiguration cfg = Main.getPlugin().getConfig();
		
		
		/** Prefix **/
		
		prefix = cfg.getString("mysql.tableprefix");
		
		
		/** Setup database **/
		
		db = new Database(
				cfg.getString("mysql.host"), 
				cfg.getInt("mysql.port"), 
				cfg.getString("mysql.database"), 
				cfg.getString("mysql.username"), 
				cfg.getString("mysql.password"), 
				true); /** Auto reconnect **/
		
		
		/** Open connection **/
		
		db.openConnection();
		
		
		/** Table **/
		
		db.queryUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "aura (id int AUTO_INCREMENT primary key NOT NULL, uuid VARCHAR(100), aura INT)");
		db.queryUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "upgrades (id int AUTO_INCREMENT primary key NOT NULL, uuid VARCHAR(100), speed INT, damage INT, resistance INT, fuel INT)");
	}
	
	
	/** Get database  **/
	
	public static Database getDatabase() {
		return db;
	}
	
}