package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.spaceship.Spaceship;
import com.voxelboxstudios.inventorys.Inventorys;
import com.voxelboxstudios.util.Util;

public class ListenerJoin implements Listener {

	/** Join **/
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		/** Player **/
		
		Player p = e.getPlayer();
		
		
		/** Resource pack **/
		
		Main.packet.addChannelForPlayer(p);
		
		
		/** Teleport **/
		
		Util.teleportRandom(e.getPlayer());
		
		
		/** Remove vehicle **/
		
		if(e.getPlayer().isInsideVehicle()) {
			e.getPlayer().getVehicle().eject();
			e.getPlayer().getVehicle().remove();
		}
		
		
		/** Spaceship **/
		
		new BukkitRunnable() {
			public void run() {
				new Spaceship(e.getPlayer());
			}
		}.runTaskLater(Main.getPlugin(), 10L);
		
		
		/** Clear Player **/
		
		p.resetMaxHealth();
		p.setHealth(20);
		
		p.setExp(0f);
		p.setLevel(0);
		
		for(PotionEffect pe : p.getActivePotionEffects()){
			p.removePotionEffect(pe.getType());
		}
		
		
		/** Clear inventory **/
		
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		
		
		/** Equip player **/
		
		Inventorys.equip(p);
		
		
		/** Game Mode **/
		
		p.setGameMode(GameMode.ADVENTURE);
		
		
		/** Join message **/
		
		e.setJoinMessage("§8» §6" + e.getPlayer().getName() + " §7hat das Spiel betreten.");
	}
	
}
