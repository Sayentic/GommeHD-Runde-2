package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.voxelboxstudios.devathlon.Main;
import com.voxelboxstudios.devathlon.aura.Aura;
import com.voxelboxstudios.devathlon.shop.Shop;
import com.voxelboxstudios.devathlon.shop.ShopMenu;

public class ListenerShop implements Listener {

	/** Click **/
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		/** Check inventory **/
		
		if(e.getClickedInventory() != null) {
			if(e.getClickedInventory().getTitle() != null) {
				if(e.getClickedInventory().getTitle().startsWith("» Shop")) {
					/** Item **/
					
					ItemStack is = e.getCurrentItem();
					
					
					/** Check item **/
					
					if(is != null) {
						if(is.hasItemMeta()) {
							/** Price **/
							
							int aura = Integer.parseInt(ChatColor.stripColor(is.getItemMeta().getLore().get(0)).split(" ")[0]);
							
							
							/** Check aura **/
							
							if(Aura.getAura((Player) e.getWhoClicked()) >= aura) {
								for(ShopMenu sm : Shop.menus) {
									if(sm.getInventory().getTitle().equalsIgnoreCase(e.getClickedInventory().getTitle())) {
										/** Lose **/
										
										Aura.subract((Player) e.getWhoClicked(), aura);
										
										
										/** Action **/
										
										sm.getActions()[e.getRawSlot()].run((Player) e.getWhoClicked());
										
										
										/** Close inventory **/
										
										e.getWhoClicked().closeInventory();
										
										
										/** Break **/
										
										break;
									}
								}
							} else {
								e.getWhoClicked().sendMessage(Main.prefix + "Du hast nicht genügend Aura um dies zu tun.");
							}
						}
					}
				}
			}
		}
	}
	
}
