package com.voxelboxstudios.devathlon.shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Shop {

	/** Menus **/
	
	private static ShopMenuArmor menu_armor = new ShopMenuArmor();
	private static ShopMenuHelmet menu_helmet = new ShopMenuHelmet();
	private static ShopMenuSpaceships menu_spaceships = new ShopMenuSpaceships();
	private static ShopMenuWeapons menu_weapons = new ShopMenuWeapons();
	
	public static List<ShopMenu> menus = new ArrayList<ShopMenu>();
	
	
	/** Setup **/
	
	public static void setup() {
		/** Menus **/
		
		menus.add(menu_armor);
		menus.add(menu_helmet);
		menus.add(menu_spaceships);
		menus.add(menu_weapons);
	}
	
	
	/** Open **/
	
	public static void open(Player p, int id) {
		/** Shop **/
		
		ShopMenu sm = menus.get(id);
		
		
		/** Inventory **/
		
		Inventory inv = sm.getInventory();
		
		
		/** Prices **/
		
		for(int j = 0; j < sm.getPrices().length; j++) {
			/** Price **/
			
			Price price = sm.getPrices()[j];
			
			
			/** Item stack **/
			
			ItemStack is = inv.getItem(j);
			
			
			/** If item isn't null **/
			
			if(is != null) {
				if(is.hasItemMeta()) {
					/** Item meta **/
					
					ItemMeta im = is.getItemMeta();
					
					
					/** Lore **/
					
					List<String> lore = new ArrayList<String>();
					
					
					/** Add price **/
					
					lore.add("§6" + price.getAura() + " Aura");
					
					
					/** Set lore **/
					
					im.setLore(lore);
					
					
					/** Set item meta **/
					
					is.setItemMeta(im);
				}
			}
		}
		
		
		/** Open **/
		
		p.openInventory(inv);
	}
	
}
