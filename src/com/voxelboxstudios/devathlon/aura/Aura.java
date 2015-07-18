package com.voxelboxstudios.devathlon.aura;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.mysql.SQL;
import com.voxelboxstudios.devathlon.scoreboards.Scoreboards;

public class Aura {

	/** Aura **/
	
	private static Map<String, Integer> aura = new HashMap<String, Integer>();
	
	
	/** Get aura **/
	
	public static int getAura(Player p) {
		/** Aura **/
		
		if(aura.containsKey(p.getName())) {
			return aura.get(p.getName());
		} else {
			/** Get aura **/
			
			ResultSet rs = null;
			
			try {
				rs = SQL.getDatabase().getQuery("SELECT aura FROM " + SQL.prefix + "aura WHERE uuid='" + p.getUniqueId().toString() + "'");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			int a = 0;
			
			try {
				while(rs.next()) a = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			aura.put(p.getName(), a);
			
			return a;
		}
	}
	
	
	/** Gain **/
	
	public static int gain(Player p, int aur) {
		/** Gain aura **/
		
		try {
			SQL.getDatabase().queryUpdate("UPDATE " + SQL.prefix + "aura SET aura=aura+" + aur + " WHERE uuid='" + p.getUniqueId().toString() + "'");
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		
		/** Get aura **/
		
		ResultSet rs = null;
		
		try {
			rs = SQL.getDatabase().getQuery("SELECT aura FROM " + SQL.prefix + "aura WHERE uuid='" + p.getUniqueId().toString() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		int a = 0;
		
		try {
			while(rs.next()) a = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		/** Put into map **/
		
		aura.put(p.getName(), a);
		
		
		/** Send message **/
		
		p.sendMessage(Main.prefix + "Du hast soeben §e" + aur + " Aura §7erhalten.");
		
		
		/** Play sound **/
		
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 3);
		
		
		/** Scoreboard **/
		
		Scoreboards.update(p);
		
		
		/** Return aura **/
		
		return a;
	}
	
	
	/** Subtract **/
	
	public static int subract(Player p, int aur) {
		/** Gain aura **/
		
		try {
			SQL.getDatabase().queryUpdate("UPDATE " + SQL.prefix + "aura SET aura=aura-" + aur + " WHERE uuid='" + p.getUniqueId().toString() + "'");
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		
		/** Get aura **/
		
		ResultSet rs = null;
		
		try {
			rs = SQL.getDatabase().getQuery("SELECT aura FROM " + SQL.prefix + "aura WHERE uuid='" + p.getUniqueId().toString() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		int a = 0;
		
		try {
			while(rs.next()) a = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		/** Put into map **/
		
		aura.put(p.getName(), a);
		
		
		/** Send message **/
		
		p.sendMessage(Main.prefix + "§eDu hast soeben §7" + aur + " Aura §eausgegeben.");
		
		
		/** Play sound **/
		
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 0);
		
		
		/** Scoreboard **/
		
		Scoreboards.update(p);
		
		
		/** Return aura **/
		
		return a;
	}


	/** Load **/
	
	public static void load(Player p) {
		/** Get aura **/
		
		ResultSet rs = null;
		
		try {
			rs = SQL.getDatabase().getQuery("SELECT aura FROM " + SQL.prefix + "aura WHERE uuid='" + p.getUniqueId().toString() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		int a = 0;
		
		try {
			while(rs.next()) a = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		/** Put into map **/
		
		aura.put(p.getName(), a);
	}
	
}
