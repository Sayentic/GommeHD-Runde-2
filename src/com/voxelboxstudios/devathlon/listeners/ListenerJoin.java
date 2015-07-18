package com.voxelboxstudios.devathlon.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.aura.Aura;
import com.voxelboxstudios.devathlon.hologram.Hologram;
import com.voxelboxstudios.devathlon.inventorys.Inventorys;
import com.voxelboxstudios.devathlon.mysql.SQL;
import com.voxelboxstudios.devathlon.scoreboards.Scoreboards;
import com.voxelboxstudios.devathlon.util.Messages;

public class ListenerJoin implements Listener {

	/** Join **/
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		/** Player **/
		
		final Player p = e.getPlayer();
		
		
		/** Aura **/
		
		new BukkitRunnable() {
			public void run() {
				/** Aura **/
				
				try {
					if(!SQL.getDatabase().getQuery("SELECT aura FROM " + SQL.prefix + "aura WHERE uuid='" + p.getUniqueId().toString() + "'").next()) {
						SQL.getDatabase().queryUpdate("INSERT INTO " + SQL.prefix + "aura (uuid, aura) VALUES ('" + p.getUniqueId().toString() + "', '0')");
					}
					
					if(!SQL.getDatabase().getQuery("SELECT * FROM " + SQL.prefix + "upgrades WHERE uuid='" + p.getUniqueId().toString() + "'").next()) {
						SQL.getDatabase().queryUpdate("INSERT INTO " + SQL.prefix + "upgrades (uuid, speed, damage, resistance, fuel, afuel) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0', '0', '0')");
					}
					
					ResultSet r = SQL.getDatabase().getQuery("SELECT afuel FROM " + SQL.prefix + "upgrades WHERE uuid='" + p.getUniqueId().toString() + "'");
					
					if(r.next()) {
						Main.fuel.put(p.getName(), r.getInt(1));
					}
					
					Aura.load(p);
				
					
					/** Equip player **/
					
					Inventorys.equip(p);
					p.getInventory().setItem(8, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				/** Scoreboards **/
				
				new BukkitRunnable() {
					public void run() {
						Scoreboards.update(p);
					}
				}.runTask(Main.getPlugin());
			}
		}.runTaskAsynchronously(Main.getPlugin());
		
		
		/** Config **/
		
		final FileConfiguration cfg = Main.getPlugin().getConfig();
		
		
		/** Resource pack **/
		
		Main.packet.addChannelForPlayer(p);
		
		
		/** Teleport **/

		try {
			Location hangar_spawn = new Location(Bukkit.getWorld(cfg.getString("hangar.world")), cfg.getDouble("hangar.x"), cfg.getDouble("hangar.y"), cfg.getDouble("hangar.z"), cfg.getInt("hangar.yaw"), cfg.getInt("hangar.pitch"));
			p.teleport(hangar_spawn);
			p.setCompassTarget(hangar_spawn);
		} catch(Exception ex) {}
		
		
		/** Clear Player **/
		
		p.resetMaxHealth();
		p.setHealth(20);
		
		p.setExp(0f);
		p.setLevel(0);
		
		for(PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
		
		
		/** Show Holograms **/
		
		for(Hologram h : Main.holograms){
			h.show(p);
		}
		
		final Location hangar_fuel_location = new Location(Bukkit.getWorld(cfg.getString("hangar.world")), cfg.getDouble("hangarfuel.x"), cfg.getDouble("hangarfuel.y"), cfg.getDouble("hangarfuel.z"), cfg.getInt("hangarfuel.yaw"), cfg.getInt("hangarfuel.pitch"));
		
		Hologram h2 = new Hologram(hangar_fuel_location, "§cTreibstoff: §e100%");
		h2.show(p);
		Main.fuel_holograms.put(p.getName(), h2);
		
		
		/** Clear inventory **/
		
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		
		
		/** Game Mode **/
		
		p.setGameMode(GameMode.ADVENTURE);
		
		
		/** Join message **/
		
		e.setJoinMessage("§8» §6" + e.getPlayer().getName() + " §7hat das Spiel betreten.");
		
		
		/** Send title **/
		
		Messages.sendTitle(e.getPlayer(), 20, 20 * 3, 20, "§eAviation", "§7Du bist im Hangar auf §8Planet Alpha§7.");
	}
	
}
