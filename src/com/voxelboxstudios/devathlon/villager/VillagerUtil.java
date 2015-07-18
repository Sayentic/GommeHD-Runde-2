package com.voxelboxstudios.devathlon.villager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class VillagerUtil {

	/** Set invulnerable **/
	
	public static void setInvulnerable(Entity ent) {
		try {
			/** Get handle **/
			
			Method getHandle = getCraftClass("entity.CraftEntity").getMethod("getHandle");
			
			
			/** Constructor **/
			
			Constructor<?> nbttag = getMCClass("NBTTagCompound").getConstructor();
			
			
			/** Methods **/
			
			Method e = getMCClass("Entity").getMethod("e", getMCClass("NBTTagCompound"));
			Method f = getMCClass("Entity").getMethod("f", getMCClass("NBTTagCompound"));
			
			Method setBoolean = getMCClass("NBTTagCompound").getMethod("setBoolean", String.class, boolean.class);

			
			/** Objects **/
			
			
			Object nms_entity = getHandle.invoke(ent);
			Object nms_tag = nbttag.newInstance();
			
			
			/** Invoke entity **/
			
			e.invoke(nms_entity, nms_tag);
			
			
			/** Invoke tags **/
			
			setBoolean.invoke(nms_tag, "Invulnerable", true);
			setBoolean.invoke(nms_tag, "Silent", true);
			setBoolean.invoke(nms_tag, "NoAI", true);
			
			
			/** Invoke **/
			
			f.invoke(nms_entity, nms_tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** Get minecraft class **/
	
	private static Class<?> getMCClass(String name) throws ClassNotFoundException {
		/** Classes **/
		
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String className = "net.minecraft.server." + version + name;
		 
		 
		/** Return class **/
		 
		return Class.forName(className);
	}
	
	
	/** Get craft class **/
	
	private static Class<?> getCraftClass(String name) throws ClassNotFoundException {
		/** Classes **/ 
		
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String className = "org.bukkit.craftbukkit." + version + name;
		 
		 
		/** Return class **/
		
		return Class.forName(className);
	 }
	
}
