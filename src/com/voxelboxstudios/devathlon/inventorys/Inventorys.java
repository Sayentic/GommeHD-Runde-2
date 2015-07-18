package com.voxelboxstudios.devathlon.inventorys;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Inventorys {

	/** Get inventory **/
	
	public static void equip(Player p) {
		/** Add to players inventory **/
		
		p.getInventory().addItem(getShortItem(Material.BONE, "§7» §cLaser §7«"));
		p.getInventory().addItem(getShortItem(Material.FIREBALL, "§7» §cRaketenwerfer §7«"));
		p.getInventory().addItem(getShortItem(Material.COMPASS, "§7» §cRadar §7«"));
		p.getInventory().addItem(getShortItem(Material.CARROT, "§7» §cTreibstoff §7«"));
		p.getInventory().setItem(8, getShortItem(Material.BARRIER, "§7» §cZurück zum Hangar§7«"));
		
		
		/** Head **/
		
		p.getEquipment().setHelmet(getShortItem(Material.GLASS, "§7Helm"));
		
		
		/** Chestplate **/
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta im4 = (LeatherArmorMeta) chestplate.getItemMeta();
		im4.setColor(Color.WHITE);
		im4.setDisplayName("§7Astronauten Anzug");
		chestplate.setItemMeta(im4);
		
		p.getEquipment().setChestplate(chestplate);
		
		
		/** Leggings **/
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta im5 = (LeatherArmorMeta) leggings.getItemMeta();
		im5.setColor(Color.WHITE);
		im5.setDisplayName("§7Astronauten Anzug");
		leggings.setItemMeta(im5);
		
		p.getEquipment().setLeggings(leggings);
		
		
		/** Boots **/
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta im6 = (LeatherArmorMeta) boots.getItemMeta();
		im6.setColor(Color.BLACK);
		im6.setDisplayName("§7Stahlkappen Schuhe");
		boots.setItemMeta(im6);
		
		p.getEquipment().setBoots(boots);
	}
	
	
	/** Get item **/
	
	public static ItemStack getShortItem(Material material, String name) {
		/** Item sStack **/
		
		ItemStack is = new ItemStack(material, 1);
		
		
		/** Item meta **/
		
		ItemMeta im = is.getItemMeta();
		
		
		/** Set display name **/
		
		im.setDisplayName(name);
		
		
		/** Set item meta **/
		
		is.setItemMeta(im);
		
		
		/** Return item **/
		
		return is;
	}
	
}
