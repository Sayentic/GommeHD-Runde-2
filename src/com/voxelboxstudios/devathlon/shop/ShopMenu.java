package com.voxelboxstudios.devathlon.shop;

import org.bukkit.inventory.Inventory;

public abstract class ShopMenu {

	/** Get inventory **/
	
	public abstract Inventory getInventory();
	
	
	/** Get prices **/
	
	public abstract Price[] getPrices();
	
	
	/** Get actions **/
	
	public abstract Action[] getActions();
	
}
