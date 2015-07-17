package com.voxelboxstudios.devathlon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ListenerWeather implements Listener {

	/** Weather **/
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		/** Cancel event **/
		
		e.setCancelled(true);
	}
	
}
