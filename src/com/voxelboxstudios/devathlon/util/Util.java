package com.voxelboxstudios.devathlon.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

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
	
	
	/** Get random color **/
	
	public static Color getRandomFireworkColor() {
		/** Colors **/
		
		List<Color> colors = new ArrayList<Color>();
		
		colors.add(Color.AQUA);
		colors.add(Color.BLUE);
		colors.add(Color.FUCHSIA);
		colors.add(Color.LIME);
		colors.add(Color.GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.MAROON);
		colors.add(Color.NAVY);
		colors.add(Color.OLIVE);
		colors.add(Color.ORANGE);
		colors.add(Color.RED);
		colors.add(Color.PURPLE);
		colors.add(Color.SILVER);
		colors.add(Color.YELLOW);
		colors.add(Color.WHITE);
		
		
		/** Shuffle **/
		
		Collections.shuffle(colors);

		
		/** Return color **/
		
		return colors.get(0);
	}
	
	
	/** Spawn firework **/
	
	public static void spawnFirework(Location l) {
		/** Create firework **/
		
        Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        
        
        /** Firework meta **/
        
        FireworkMeta fwm = fw.getFireworkMeta();

        
        /** Random **/
        
        Random r = new Random();

        
        /** Type **/
        
        int rt = r.nextInt(3) + 1;
        
        Type type = Type.BALL;    
        
        if (rt == 1) type = Type.BALL;
        if (rt == 2) type = Type.BALL_LARGE;
        if (rt == 3) type = Type.BURST;
        if (rt == 4) type = Type.STAR;

        
        /** Colors **/
        
        Color c1 = getRandomFireworkColor();
        Color c2 = getRandomFireworkColor();
     
        
        /** Effect **/
        
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
     
        
        /** Add effect **/
        
        fwm.addEffect(effect);

        
        /** Set power **/
        
        fwm.setPower(0);

        
        /** Apply meta **/
        
        fw.setFireworkMeta(fwm);
	}
}
