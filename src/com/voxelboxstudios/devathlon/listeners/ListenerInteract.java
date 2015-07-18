package com.voxelboxstudios.devathlon.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.actionbar.ActionBar;
import com.voxelboxstudios.devathlon.spaceship.Spaceship;
import com.voxelboxstudios.inventorys.Inventorys;
import com.voxelboxstudios.util.Messages;
import com.voxelboxstudios.util.Util;

public class ListenerInteract implements Listener {
	
	/** Cooldown **/
	
	private Map<String, Long> cooldown = new HashMap<String, Long>();
	private Map<String, Long> hcooldown = new HashMap<String, Long>();
	
	
	/** Entity interact entity **/
	
	@EventHandler
	public void onEntityInteractEntityEvent(PlayerInteractAtEntityEvent e) {
		/** Check right clicked **/
		
		if(e.getRightClicked() != null) {
			/** Right clicked **/
			
			Entity en = e.getRightClicked();
			
			
			/** Check if armor stand **/
			
			if(en instanceof ArmorStand) {
				/** Cancel event **/
				
				e.setCancelled(true);
			}
		}
	}
	
	
	/** Interact **/
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(final PlayerInteractEvent e) {
		/** Find spaceship **/
			
		Spaceship s = null;
			
		for(Spaceship t : Spaceship.spaceships) {
			if(t.getPlayer() == e.getPlayer()) {
				s = t;
			}
		}
			
		if(s != null) {
			/** Check Fuel **/
			
			if(s.getFuel() > 0) {
				/** Slot - Laser Canon **/
				
				if(e.getPlayer().getInventory().getHeldItemSlot() == 0) {
					/** Play sound **/
					
					for(Player t : Bukkit.getOnlinePlayers()) t.playSound(e.getPlayer().getLocation(), "laser", 1, 3);
					
					
					/** Spawn effect **/
						
					Location start = s.getPlayer().getLocation();
					
					Vector increase = start.getDirection();
					
					inc: for(int count = 0; count < 150; count++) {
						/** Point **/
							
						Location point = start.add(increase);
						
						
						/** Check for hit **/
						
						List<Spaceship> damaged = new ArrayList<Spaceship>();
						
						for(Spaceship b : Spaceship.spaceships) {
							if(b != s && b.getArmorStand().getLocation().distance(point) <= 1.5) {
								if(!damaged.contains(b)) {
									/** Add to damaged **/
									
									damaged.add(b);
									
									
									/** Damage **/
									
									b.damage(Main.getLaserDamage());
									
									
									/** Cooldown **/
									
									hcooldown.put(b.getPlayer().getName(), System.currentTimeMillis());
									
									
									/** Break **/
									
									break inc;
								}
							}
						}
						
						
						/** Play particles **/
							
						for(int i = 0; i < 10; i++) e.getPlayer().getWorld().playEffect(point, Effect.COLOURED_DUST, 14);
					}
				}
				
				
				/** Rocket launcher **/
				
				if(e.getPlayer().getInventory().getHeldItemSlot() == 1) {
					/** Check cooldown **/
					
					boolean cool = false;
					
					if(cooldown.containsKey(e.getPlayer().getName())) {
						if(System.currentTimeMillis() - cooldown.get(e.getPlayer().getName()) >= Main.getExplosionCooldown()) {
							cool = true;
						}
					} else {
						cool = true;
					}
					
					if(cool) {
						/** Cooldown **/
						
						cooldown.put(e.getPlayer().getName(), System.currentTimeMillis());
						
						
						/** Play sound **/
						
						for(Player t : Bukkit.getOnlinePlayers()) t.playSound(e.getPlayer().getLocation(), "missile", 2, 3);
						
						
						/** Spawn effect **/
							
						Location start = s.getPlayer().getLocation();
						
						Vector increase = start.getDirection();
						
						inc: for(int count = 0; count < 150; count++) {
							/** Point **/
								
							Location point = start.add(increase);
							
							
							/** Check for hit **/
							
							List<Spaceship> damaged = new ArrayList<Spaceship>();
							
							for(Spaceship b : Spaceship.spaceships) {
								if(b != s && b.getArmorStand().getLocation().clone().add(0, 1, 0).distance(point) <= 1.5) {
									if(!damaged.contains(b)) {
										/** Damaged **/
										
										damaged.add(b);
										
										
										/** Cooldown **/
										
										hcooldown.put(b.getPlayer().getName(), System.currentTimeMillis());
										
										
										/** Damage **/
										
										b.damage(Main.getExplosionDamage());
										
										
										/** Play sounds **/
										
										e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.EXPLODE, 1f,  1f);
										e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.PISTON_EXTEND, 1f,  1f);
										
										
										/** Velocity **/
										
										b.getArmorStand().setVelocity(b.getArmorStand().getVelocity().add(new Vector(0, 2, 0)));
										
										
										/** Break **/
										
										break inc;
									}
								}
							}
							
							
							/** Play particles **/
								
							for(int i = 0; i < 10; i++) e.getPlayer().getWorld().playEffect(point, Effect.SNOWBALL_BREAK, 14);
						}
					}
				}
				
				
				/** Radar **/
				
				if(e.getPlayer().getInventory().getHeldItemSlot() == 2) {
					for(Spaceship se : Spaceship.spaceships) {
						if(se.getPlayer() != e.getPlayer()) {
							e.getPlayer().setCompassTarget(se.getPlayer().getLocation());
							break;
						}
					}
				}
			} else {
				/** Send player fuel Message **/
					
				Messages.sendMessageAttention(e.getPlayer(), "Du benötigst Treibstoff um dies zu tun.");
			}
			
			
			/** Hangar **/
			
			if(e.getPlayer().getInventory().getHeldItemSlot() == 8) {
				/** Check cooldown **/
				
				boolean cool = false;
				
				if(hcooldown.containsKey(e.getPlayer().getName())) {
					if(System.currentTimeMillis() - hcooldown.get(e.getPlayer().getName()) >= Main.getHangarCooldown()) {
						cool = true;
					}
				} else {
					cool = true;
				}
				
				if(cool) {
					/** Remove spaceship **/
					
					Spaceship.checkDespawn(e.getPlayer());
					
					
					/** Set exp **/
					
					e.getPlayer().setExp(1f);
					
					
					/** Set item slot **/
					
					e.getPlayer().getInventory().setItem(8, null);
					
					
					/** Action bar **/
					
					ActionBar.sendActionBar(e.getPlayer(), "");
					
					
					/** Config **/
					
					FileConfiguration cfg = Main.getPlugin().getConfig();
					
					
					/** Location **/
					
					Location hangar_spawn = new Location(Bukkit.getWorld(cfg.getString("hangar.world")), cfg.getDouble("hangar.x"), cfg.getDouble("hangar.y"), cfg.getDouble("hangar.z"), cfg.getInt("hangar.yaw"), cfg.getInt("hangar.pitch"));
					
					
					/** Teleport **/
					
					e.getPlayer().teleport(hangar_spawn);
					
					
					/** Play effect **/
					
					for(int i = 0; i < 12; i++) e.getPlayer().playEffect(hangar_spawn, Effect.LARGE_SMOKE, i);
					
					
					/** Gravity **/
					
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20000, 0));
					
					
					/** Play sound **/
					
					e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.PISTON_RETRACT, 1, 0.5f);
				} else {
					Messages.sendMessageWarning(e.getPlayer(), "Während des Gefechts kannst du nicht zum Hangar!");
				}
			}
		} else {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock() != null) {
					if(e.getClickedBlock().getType() == Material.SPONGE) {
						/** Clear inventory **/
						
						e.getPlayer().getInventory().clear();
						
						
						/** Inventory **/
						
						Inventorys.equip(e.getPlayer());
						
						
						/** Potions **/
						
						for(PotionEffect pe : e.getPlayer().getActivePotionEffects()) {
							e.getPlayer().removePotionEffect(pe.getType());
						}
						
						
						/** Teleport **/
						
						Util.teleportRandom(e.getPlayer());
						
						
						/** Spaceship **/
						
						new BukkitRunnable() {
							public void run() {
								new Spaceship(e.getPlayer());
							}
						}.runTaskLater(Main.getPlugin(), 10L);
					}
				}
			}
		}
	}
}