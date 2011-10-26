package com.Evilgeniuses.Hardcore;

import org.bukkit.util.config.Configuration;

public class HardcoreConfiguration {
	
	//one week
	private int DEFAULT_DEATH_DURATION_SECONDS = 7 * 24 * 60 * 60;
	private int DEFAULT_REAPER_CHECK_SECONDS = 10;
	private boolean DEFAULT_FINAL_FAREWELL = false;
	private int DEFAULT_FINAL_FAREWELL_SECONDS = 60;
		
	public int deathSeconds;
	public int reaperCheckSeconds;
	public boolean finalFarewell;
	public int finalFarewellSeconds;

	HardcoreConfiguration(HardcorePlugin plugin) {
		Configuration config = plugin.getConfiguration();
		
		this.deathSeconds=config.getInt("deathSeconds", DEFAULT_DEATH_DURATION_SECONDS);
		this.reaperCheckSeconds=config.getInt("reaperCheckSeconds", DEFAULT_REAPER_CHECK_SECONDS);
		this.finalFarewell=config.getBoolean("finalFarewell", DEFAULT_FINAL_FAREWELL);
		this.finalFarewellSeconds=config.getInt("finalFarewellSeconds", DEFAULT_FINAL_FAREWELL_SECONDS);
		
		config.save();	
	}
	
	
}
