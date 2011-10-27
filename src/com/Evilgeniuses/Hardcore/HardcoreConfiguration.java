package com.Evilgeniuses.Hardcore;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.bukkit.util.config.Configuration;

public class HardcoreConfiguration {
	
	//one week
	private int DEFAULT_DEATH_DURATION_SECONDS = 7 * 24 * 60 * 60;
	private int DEFAULT_REAPER_CHECK_SECONDS = 10;
	private boolean DEFAULT_FINAL_FAREWELL = false;
	private int DEFAULT_FINAL_FAREWELL_SECONDS = 60;
	private boolean DEFAULT_THUNDER_LIGHTNING = true;
	private int DEFAULT_THUNDER_LENGTH_SECONDS = 1;
	private boolean DEFAULT_USE_RESURRECTION_DAY = false;
	private String DEFAULT_RESURRECTION_DAY_START = DateFormat.getInstance().format(new Date(0));
	private String DEFAULT_RESURRECTION_DAY_END = DateFormat.getInstance().format(new Date(1));
	
	public int deathSeconds;
	public int reaperCheckSeconds;
	public boolean finalFarewell;
	public int finalFarewellSeconds;
	public boolean doThunderAndLightningOnDeath;
	public int thunderLengthSeconds;
	public boolean useResurrectionDay;
	public Date resurrectionDayStart;
	public Date resurrectionDayEnd;
	
	HardcoreConfiguration(HardcorePlugin plugin) {
		Configuration config = plugin.getConfiguration();
		
		this.deathSeconds=config.getInt("deathSeconds", DEFAULT_DEATH_DURATION_SECONDS);
		this.reaperCheckSeconds=config.getInt("reaperCheckSeconds", DEFAULT_REAPER_CHECK_SECONDS);

		this.finalFarewell=config.getBoolean("finalFarewell", DEFAULT_FINAL_FAREWELL);
		this.finalFarewellSeconds=config.getInt("finalFarewellSeconds", DEFAULT_FINAL_FAREWELL_SECONDS);

		this.doThunderAndLightningOnDeath=config.getBoolean("doThunderAndLightningOnDeath", DEFAULT_THUNDER_LIGHTNING);
		this.thunderLengthSeconds=config.getInt("thunderLengthSeconds",DEFAULT_THUNDER_LENGTH_SECONDS);

		this.useResurrectionDay=config.getBoolean("useResurrectionDay", DEFAULT_USE_RESURRECTION_DAY);
		
		String resDateRawStart = config.getString("resurrectionDayStart", DEFAULT_RESURRECTION_DAY_START);
		String resDateRawEnd = config.getString("resurrectionDayEnd", DEFAULT_RESURRECTION_DAY_END);
		
		try {
			this.resurrectionDayStart = DateFormat.getInstance().parse(resDateRawStart);
		} catch(ParseException exc) {
			plugin.log("Cound not read resurectionDayStart setting: (" + resDateRawStart  + "), it needs to be in MM/DD/YYYY HH:MM am/pm. Resurection Day is turned off, defaulting to rolling kill date.");
			this.useResurrectionDay=false;
		}
		
		try {
			this.resurrectionDayEnd = DateFormat.getInstance().parse(resDateRawEnd);
		} catch(ParseException exc) {
			plugin.log("Cound not read resurectionDayEnd setting: (" + resDateRawEnd  + "), it needs to be in MM/DD/YYYY HH:MM am/pm. Resurection Day is turned off, defaulting to rolling kill date.");
			this.useResurrectionDay=false;
		}
				
				
		if (useResurrectionDay) {
			if (!resurrectionDayStart.before(resurrectionDayEnd)) {
				plugin.log("The start of the Resurrection must be before the end. Resurection Day is turned off, defaulting to rolling kill date.");
				this.useResurrectionDay=false;
			}
			else
				plugin.log("Resurrection is between: " + this.resurrectionDayStart.toString() + " and " + this.resurrectionDayEnd.toString());
		}
		
		config.save();	
	}
	
	
}
