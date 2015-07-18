package com.voxelboxstudios.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.voxelboxstudios.devathlon.Main;

public class Util {
	
	/** Random teleport **/
	
	public static void teleportRandom(Player p) {
		/** Range **/
		
		int range = Main.getPlugin().getConfig().getInt("maprange") * 64;
		
		
		/** Random **/
		
		Random r = new Random();
		
		
		/** Position **/
		
		Location location = new Location(Bukkit.getWorld("map"), r.nextInt(range), 100, r.nextInt(range));
		
		Block b = location.getWorld().getHighestBlockAt(location);
		
		if(b != null) location = b.getLocation();
		
		
		/** Teleport **/
		
		p.teleport(location.clone().add(0, 1, 0));
	}
	
}
