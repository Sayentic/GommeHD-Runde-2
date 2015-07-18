package com.voxelboxstudios.devathlon.worlds;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Minecart;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.hologram.Hologram;
import com.voxelboxstudios.devathlon.villager.VillagerShop;

public class Worlds {

	/** Constructor **/
	
	public Worlds(int range) {
		/** Config **/
		
		final FileConfiguration cfg = Main.getPlugin().getConfig();
		
		
		/** Create map **/
		
		WorldCreator wc = new WorldCreator("map");
		
		wc.generator(Main.getPlugin().getDefaultWorldGenerator("map", null));
		
		World ww = Bukkit.createWorld(wc);
		
		Bukkit.getWorlds().add(ww);
		
		
		/** Load Hangar Map **/
		
		WorldCreator wc2 = new WorldCreator("hangar");
		
		wc2.generator(Main.getPlugin().getDefaultWorldGenerator("hangar", null));
		
		World ww2 = Bukkit.createWorld(wc2);
		
		Bukkit.getWorlds().add(ww2);
		
		
		/** Generate planets **/
		
		//WorldGenerator.generate(ww, range);
		
		
		/** Loop through worlds **/
		
		for(final World w : Bukkit.getWorlds()) {
			/** Load all chunks **/
			
			//loadAllChunks(w);
			
			
			/** Auto save **/
			
			w.setAutoSave(false);
			
			
			/** Remove entities **/
			
			new BukkitRunnable() {
				@Override
				public void run() {
					for(Entity e : w.getEntities()) {
						if(e instanceof Minecart){
							((Minecart) e).eject();
						}
						
						/** Dont remove Item Frames **/
						
						if(!(e instanceof ItemFrame)){
							e.remove();
						}
					}
					
					if(w.getName().equalsIgnoreCase("hangar")){
						/** Location **/
						
						Location hangar_spawn = new Location(Bukkit.getWorld(cfg.getString("hangar.world")), cfg.getDouble("hangar.x"), cfg.getDouble("hangar.y"), cfg.getDouble("hangar.z"), cfg.getInt("hangar.yaw"), cfg.getInt("hangar.pitch"));
						
						Hologram h = new Hologram(hangar_spawn, "§7» §6Zurück zum Spiel §7«");
						
						
						/** Add Holograms **/
						
						Main.holograms.add(h);
						
						
						/** Create Villagers **/
						
						VillagerShop.spawnVillagers();
					}
				}
			}.runTaskLater(Main.getPlugin(), 20L);
			
			
			/** Set gamerules **/
			
			w.setGameRuleValue("doDaylightCycle", "false");
			
			
			/** Set time **/
			
			w.setTime(13000);
			
			
			/** Weather **/
			
			w.setStorm(false);
			w.setThundering(false);
		}
	}
	
	
	/** Load all chunks **/
	
	@SuppressWarnings("unused")
	private static void loadAllChunks(World world) {
		/** Pattern **/
		
        final Pattern regionPattern = Pattern.compile("r\\.([0-9-]+)\\.([0-9-]+)\\.mca");
        
        
        /** Directories **/
        
        File worldDir = new File(Bukkit.getWorldContainer(), world.getName());
        File regionDir = new File(worldDir, "region");
 
        
        /** Region files **/
        
        File[] regionFiles = regionDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return regionPattern.matcher(name).matches();
            }
        });
 
        
        /** Math regions **/
        
        for(File f : regionFiles) {
        	/** Matcher **/
        	
            Matcher matcher = regionPattern.matcher(f.getName());
            
            
            /** Check if matches **/
            
            if(!matcher.matches()) {
                continue;
            }
 
            
            /** Position **/
            
            int mcaX = Integer.parseInt(matcher.group(1));
            int mcaZ = Integer.parseInt(matcher.group(2));
 
            
            /** Loop **/
             
            for(int cx = 0; cx < 32; cx++) {
                for(int cz = 0; cz < 32; cz++) {
                	/** Load chunk **/
                	
                	world.loadChunk((mcaX << 5) + cx, (mcaZ << 5) + cz, false);
                }
            }
        }
    }
	
}
