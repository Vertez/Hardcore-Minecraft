package com.Evilgeniuses.Hardcore;

import org.bukkit.util.config.Configuration;

public class HardcoreConfiguration {
	
	//one week
	private int DEFAULT_DEATH_DURATION_SECONDS = 7 * 24 * 60 * 60;
	private int DEFAULT_REAPER_CHECK_SECONDS = 10;
	private boolean DEFAULT_THUNDER_LIGHTNING = true;
	private int DEFAULT_THUNDER_LENGTH_SECONDS = 1;
		
	public int deathSeconds;
	public int reaperCheckSeconds;
	public boolean doThunderAndLightningOnDeath;
	public int thunderLengthSeconds;

	HardcoreConfiguration(HardcorePlugin plugin) {
		Configuration config = plugin.getConfiguration();
		
		this.deathSeconds=config.getInt("deathSeconds", DEFAULT_DEATH_DURATION_SECONDS);
		this.reaperCheckSeconds=config.getInt("reaperCheckSeconds", DEFAULT_REAPER_CHECK_SECONDS);
		this.doThunderAndLightningOnDeath=config.getBoolean("doThunderAndLightningOnDeath", DEFAULT_THUNDER_LIGHTNING);
		this.thunderLengthSeconds=config.getInt("thunderLengthSeconds",DEFAULT_THUNDER_LENGTH_SECONDS);
		
		config.save();	
	}
	
	
}
