package com.voxelboxstudios.devathlon.listeners;

import java.sql.SQLException;
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
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.actionbar.ActionBar;
import com.voxelboxstudios.devathlon.hologram.Hologram;
import com.voxelboxstudios.devathlon.mysql.SQL;
import com.voxelboxstudios.devathlon.shop.Shop;
import com.voxelboxstudios.devathlon.spaceship.Spaceship;
import com.voxelboxstudios.devathlon.util.Messages;

import net.md_5.bungee.api.ChatColor;

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
			
			
			/** Check entity **/
			
			if(en instanceof ArmorStand || en instanceof ItemFrame) {
				/** Cancel event **/
				
				e.setCancelled(true);
			}
		}
	}
	
	
	/** Interact villager **/
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		/** Entity **/
		
		Entity en = e.getRightClicked();
		
		if(en == null) return;
		
		
		/** Check if villager **/
		
		if(en instanceof Villager) {
			/** Cancel event **/
			
			e.setCancelled(true);
			
			
			/** Config **/
			
			FileConfiguration cfg = Main.getPlugin().getConfig();
			
			
			/** Check which villager **/
			
			if(e.getRightClicked().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', cfg.getString("villager.1.name")))) {
				//Shop.open(e.getPlayer(), 1);
				
				e.getRightClicked().sendMessage(Main.prefix + "Dieser Shop wird momentan repariert. Tech-Nick ist informiert!");
			} else if(e.getRightClicked().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', cfg.getString("villager.2.name")))) {
				//Shop.open(e.getPlayer(), 0);
				
				e.getRightClicked().sendMessage(Main.prefix + "Dieser Shop wird momentan repariert. Tech-Nick ist informiert!");
			} else if(e.getRightClicked().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', cfg.getString("villager.3.name")))) {
				//Shop.open(e.getPlayer(), 3);
				
				e.getRightClicked().sendMessage(Main.prefix + "Dieser Shop wird momentan repariert. Tech-Nick ist informiert!");
			} else if(e.getRightClicked().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', cfg.getString("villager.4.name")))) {
				Shop.open(e.getPlayer(), 2);
			}
		}
	}
	
	
	/** Interact **/
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(final PlayerInteractEvent e) {
		/** Cancel slot 4 **/
		
		if(e.getPlayer().getInventory().getHeldItemSlot() == 3) e.setCancelled(true);
		
		
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
									
									b.damage(Main.getLaserDamage(), e.getPlayer());
									
									
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
	
				
				/** Fuel **/
				
				if(e.getPlayer().getInventory().getHeldItemSlot() == 3) {
					if(Main.fuel.get(e.getPlayer().getName()) > 0) {
						/** Put into hashmap **/
						
						Main.fuel.put(e.getPlayer().getName(), Main.fuel.get(e.getPlayer().getName()) - 1);
						
						
						/** MySQL **/
						
						try {
							SQL.getDatabase().queryUpdate("UPDATE " + SQL.prefix + "upgrades SET afuel=" + Main.fuel.get(e.getPlayer().getName()) + " WHERE uuid='" + e.getPlayer().getUniqueId().toString() + "'");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						
						/** Fill tank **/
						
						s.setFuel(100 + s.getExtraFuel());
						
						
						/** Play sound **/
						
						e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 3);
						
						
						/** Send message **/
						
						e.getPlayer().sendMessage(Main.prefix + "Du hast deinen Tank aufgefüllt (§e" + Main.fuel.get(e.getPlayer().getName()) + " übrig§7).");
						
						
						/** Amount **/
						
						e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
					} else {
						e.getPlayer().sendMessage(Main.prefix + "Du hast keinen Treibstoff mehr. Erwerbe diesen im Shop.");
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
										
										b.damage(Main.getExplosionDamage() - b.getResistance() + s.getDamage(), e.getPlayer());
										
										
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
			} else {
				/** Send player fuel Message **/
							
				if(e.getPlayer().getInventory().getHeldItemSlot() != 8) Messages.sendMessageAttention(e.getPlayer(), "Du benötigst Treibstoff um dies zu tun.");
			}
			
			
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
					
					e.getPlayer().setExp(0f);
					
					
					/** Set item slot **/
					
					e.getPlayer().getInventory().setItem(8, null);
					
					
					/** Action bar **/
					
					ActionBar.sendActionBar(e.getPlayer(), "");
					
					
					/** Hangar Spawn **/
					
					FileConfiguration cfg = Main.getPlugin().getConfig();
					Location hangar_spawn = new Location(Bukkit.getWorld(cfg.getString("hangar.world")), cfg.getDouble("hangar.x"), cfg.getDouble("hangar.y"), cfg.getDouble("hangar.z"), cfg.getInt("hangar.yaw"), cfg.getInt("hangar.pitch"));
					
					/** Teleport **/
					
					e.getPlayer().teleport(hangar_spawn);
					
					/** Config **/
					
					final Location hangar_fuel_location = new Location(Bukkit.getWorld(cfg.getString("hangar.world")), cfg.getDouble("hangarfuel.x"), cfg.getDouble("hangarfuel.y"), cfg.getDouble("hangarfuel.z"), cfg.getInt("hangarfuel.yaw"), cfg.getInt("hangarfuel.pitch"));
					
					
					/** Set Fuel **/
					
					Main.fuel_reload.put(e.getPlayer().getName(), 0);
					Hologram h1 = Main.fuel_holograms.get(e.getPlayer().getName());
					h1.hide(e.getPlayer());
					
					Hologram h2 = new Hologram(hangar_fuel_location, "§cTreibstoff: §e0%");
					h2.show(e.getPlayer());
					Main.fuel_reload.put(e.getPlayer().getName(), 0);
					Main.fuel_holograms.put(e.getPlayer().getName(), h2);
					
					/** Show Holograms **/
					
					for(Hologram h : Main.holograms){
						h.show(e.getPlayer());
					}
					
					
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
						Spaceship.createSpaceship(e.getPlayer());
					}
				}
			}
		}
	}
}