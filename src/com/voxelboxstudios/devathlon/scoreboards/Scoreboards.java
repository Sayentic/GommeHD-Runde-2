package com.voxelboxstudios.devathlon.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.voxelboxstudios.devathlon.aura.Aura;

public class Scoreboards {

	/** Update **/
	
	public static void update(Player p) {
		/** Scoreboard **/
		
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		
		/** Objective **/
		
		Objective obj = sb.registerNewObjective("stats", "dummy");
		
		
		/** Set display slot **/
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		
		/** Name **/
		
		obj.setDisplayName("§8» §6Aviation");
		
		
		/** Scores **/
		
		obj.getScore("§eDeine Aura:").setScore(1);
		obj.getScore("§7" + Aura.getAura(p)).setScore(0);
		
		
		/** Set scoreboard **/
		
		p.setScoreboard(sb);
	}
	
}
