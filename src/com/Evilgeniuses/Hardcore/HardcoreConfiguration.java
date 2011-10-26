package com.Evilgeniuses.Hardcore;

import org.bukkit.util.config.Configuration;

public class HardcoreConfiguration {
	
	//one week
	private int DEFAULT_DEATH_DURATION_SECONDS = 7 * 24 * 60 * 60;
	private int DEFAULT_REAPER_CHECK_SECONDS = 10;
		
	public int deathSeconds;
	public int reaperCheckSeconds;

	HardcoreConfiguration(HardcorePlugin plugin) {
		Configuration config = plugin.getConfiguration();
		
		this.deathSeconds=config.getInt("deathSeconds", DEFAULT_DEATH_DURATION_SECONDS);
		this.reaperCheckSeconds=config.getInt("reaperCheckSeconds", DEFAULT_REAPER_CHECK_SECONDS);
		
		config.save();	
	}
	
	
}
