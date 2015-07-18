package com.voxelboxstudios.devathlon.shop;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.voxelboxstudios.devathlon.inventorys.Inventorys;
import com.voxelboxstudios.devathlon.mysql.SQL;

public class ShopMenuSpaceships extends ShopMenu {

	/** Inventory **/
	
	@Override
	public Inventory getInventory() {
		/** Inventory **/
		
		Inventory i = Bukkit.createInventory(null, 9, "» Shop | Upgrades");
		
		i.addItem(Inventorys.getShortItem(Material.FEATHER, "§aGeschwindigkeit +1"));
		i.addItem(Inventorys.getShortItem(Material.IRON_SWORD, "§eSchaden +1"));
		i.addItem(Inventorys.getShortItem(Material.LEATHER_CHESTPLATE, "§eVerteidigung +1"));
		i.addItem(Inventorys.getShortItem(Material.COAL, "§eTreibstoff +1"));
		
		
		/** Return inventory **/
		
		return i;
	}

	
	/** Get prices **/
	
	@Override
	public Price[] getPrices() {
		return new Price[] {
				new Price(600), new Price(500), new Price(500), new Price(300)
		};
	}

	
	/** Actions **/
	
	@Override
	public Action[] getActions() {
		return new Action[] {
				new Action() {
					@Override
					public void run(Player p) {
						try {
							SQL.getDatabase().queryUpdate("UPDATE " + SQL.prefix + "upgrades SET speed=speed+1 WHERE uuid='" + p.getUniqueId().toString() + "'");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				},
				
				new Action() {
					@Override
					public void run(Player p) {
						try {
							SQL.getDatabase().queryUpdate("UPDATE " + SQL.prefix + "upgrades SET damage=damage+1 WHERE uuid='" + p.getUniqueId().toString() + "'");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				},
				
				new Action() {
					@Override
					public void run(Player p) {
						try {
							SQL.getDatabase().queryUpdate("UPDATE " + SQL.prefix + "upgrades SET resistance=resistance+1 WHERE uuid='" + p.getUniqueId().toString() + "'");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				},
				
				new Action() {
					@Override
					public void run(Player p) {
						try {
							SQL.getDatabase().queryUpdate("UPDATE " + SQL.prefix + "upgrades SET fuel=fuel+1 WHERE uuid='" + p.getUniqueId().toString() + "'");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
		};
	}
}
