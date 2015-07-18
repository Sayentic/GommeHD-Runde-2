package com.voxelboxstudios.devathlon.shop;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class ShopMenuArmor extends ShopMenu {

	/** Inventory **/
	
	@Override
	public Inventory getInventory() {
		/** Inventory **/
		
		Inventory i = Bukkit.createInventory(null, 9, "» Shop | Kleidung");
		
		
		/** Return inventory **/
		
		return i;
	}

	
	/** Get prices **/
	
	@Override
	public Price[] getPrices() {
		return new Price[] {};
	}

	
	/** Actions **/
	
	@Override
	public Action[] getActions() {
		return new Action[] {};
	}
}
