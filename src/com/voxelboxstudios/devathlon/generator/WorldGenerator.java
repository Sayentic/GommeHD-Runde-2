package com.voxelboxstudios.devathlon.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldGenerator {
	
	/** Generate **/
	
	public static void generate(World w, int range) {
		/** Log **/
		
		Bukkit.getLogger().info("Welt wird generiert...");
		
		
		/** Random **/
		
		Random random = new Random();
		
		
		/** Materials **/
		
		List<Material> materials = new ArrayList<Material>();
		
		materials.add(Material.STONE);
		materials.add(Material.MYCEL);
		materials.add(Material.ENDER_STONE);
		materials.add(Material.COBBLESTONE);
		materials.add(Material.DIRT);
		
		
		/** Set WorldBorder **/
		
		WorldBorder wb = w.getWorldBorder();
		wb.setCenter((64 * range) / 2, (64 * range) / 2);
		wb.setSize(64 * range);

		
		/** Loop **/
		
		for(int x = 0; x < 64 * range; x += 64) {
			for(int y = 0; y < 64 * range; y += 64) {
				for(int z = 0; z < 64 * range; z += 64) {
					/** Check random **/
					
					if(random.nextInt(15) == 5) {
						/** Shuffle **/
						
						Collections.shuffle(materials);
						
						
						/** Generate sphere **/
						
						generateSphere(new Location(w, x, y + 35, z), random.nextInt(10) + 5, materials.get(0), true);
					}
				}
			}
		}
		
		
		/** Log **/
		
		Bukkit.getLogger().info("Generation abgeschlossen.");
	}
	
	
	/** Generate sphere **/
	
	private static void generateSphere(Location center, int radius, Material material, boolean hollow) {
		/** Circle blocks **/
		
		List<Location> circles = new ArrayList<Location>();
		
		
		/** Position **/
		
		int bX = center.getBlockX();
		int bY = center.getBlockY();
		int bZ = center.getBlockZ();
		
		
		/** Loop **/
		
		for(int x = bX - radius; x <= bX + radius; x++) {
			for(int y = bY - radius; y <= bY + radius; y++) {
				for(int z = bZ - radius; z <= bZ + radius; z++) {
					/** Distance **/
					
					double distance = ((bX - x) * (bX - x) + ((bZ - z) * (bZ - z)) + ((bY - y) * (bY - y)));
					
					
					/** Check distance **/
					
					if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
						Location l = new Location(center.getWorld(), x, y, z);
						
						circles.add(l);
					}
				}
			}
		}
		
		
		/** Set blocks **/
		
		for(Location loc : circles) {
			loc.getBlock().setType(material);
		}
	}

}
