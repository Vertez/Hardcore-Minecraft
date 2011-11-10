package com.Evilgeniuses.Hardcore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.entity.Player;

public class DeadPlayerList {
		
	private ArrayList<DeadPlayer> _deadPlayers = new ArrayList<DeadPlayer>();
	private HardcorePlugin _plugin=null;
	
	DeadPlayerList(HardcorePlugin plugin) {
		_plugin=plugin;
		load();
	}
	
	public void addPlayer(Player player, String deathMessage) {
		String playerName = player.getName();
		_deadPlayers.add(new DeadPlayer(playerName,new Date()));
		
		save();
		
		DeadLog dl = new DeadLog(_plugin);
		dl.addDeadLog(playerName, new Date(), player.getLevel(), player.getTotalExperience(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), deathMessage);
		
		_plugin.log(playerName + " was added to the list of the dead. They will be dead until " + whenWillPlayerLive(playerName).toString() );
	}
	
	public void removePlayer(String playerName) {
		boolean found=true;
		
		//gotta be better collection management
		while(found) {
			int i;
			for( i=0; i<_deadPlayers.size(); i++) {
				if (playerName.compareToIgnoreCase(_deadPlayers.get(i).getPlayerName())==0) {
					_plugin.log("Removing " + playerName + " from deadlist.");
					_deadPlayers.remove(i);
					break;
				}
			}
			
			found = i!=_deadPlayers.size();
			
		}
		
		save();
	}
	
	public boolean isPlayerDead(String playerName, boolean checkForFarewell) {
		boolean isFound=false;
				
		for (int i = 0; i < _deadPlayers.size(); i++) 
		{
			
			//better way to compare?
			if (playerName.compareToIgnoreCase(_deadPlayers.get(i).getPlayerName())==0)
			{
				//if finalFarewell is enabled, check if they can stay for now
				if (_plugin.getHardcoreConfiguration().finalFarewell && checkForFarewell)
				{
					//if the date when their farewell ends after the current time, they can still be here
					if (getFarewellDate(_deadPlayers.get(i)).after(new Date())) {
						continue; //continue on to the next player in the list
					}
					//otherwise fall into the other checks below
				}
				
				//they are on the list, but are they still dead?
				//if the live date is before now, they are ready to come back
				if (getLiveDate(_deadPlayers.get(i)).before(new Date())) 
				{
					DeadPlayer p = _deadPlayers.get(i);
					
					//remove them from the list, thats ok because we are done iterating.
					_plugin.log(playerName + " is ready to come back. They died on " + p.getDeathDate().toString()  + " and could come back any time after " + getLiveDate(p).toString() + ".");
					removePlayer(playerName);
					break;
				} 
				else 
				{
					//they are still dead
					isFound=true;
					break;
				}
			}
		}
		
		return isFound;
	}
	
	public Date whenWillPlayerLive(String playerName) {
		for (int i = 0; i < _deadPlayers.size(); i++) 
		{
			//better way to compare?
			if (playerName.compareToIgnoreCase(_deadPlayers.get(i).getPlayerName())==0)
			{
				return getLiveDate(_deadPlayers.get(i));
			}
		}
		
		return new Date();
	}

	private Date getLiveDate(DeadPlayer player) {
		if (_plugin.getHardcoreConfiguration().useResurrectionDay) {
			//if they died before the start, they can come back after the start
			if (player.getDeathDate().before(_plugin.getHardcoreConfiguration().resurrectionDayStart))
				return _plugin.getHardcoreConfiguration().resurrectionDayStart;
			//if they died after the start, they can come back at the end
			else
				return _plugin.getHardcoreConfiguration().resurrectionDayEnd;
		} else {
			//wow java date math is a pain
			Calendar c = Calendar.getInstance();
			c.setTime(player.getDeathDate());
			c.add(Calendar.SECOND, _plugin.getHardcoreConfiguration().deathSeconds);
			return c.getTime();
		}
	}
	
	private Date getFarewellDate(DeadPlayer player) {
		Calendar c = Calendar.getInstance();
		c.setTime(player.getDeathDate());
		c.add(Calendar.SECOND, _plugin.getHardcoreConfiguration().finalFarewellSeconds);
		return c.getTime();
	}
	
	private void load() {
		File listFile = getDeadplayerFile();
		if (!listFile.exists()) {
			_plugin.log("Dead players file not found, starting a new one.");
		} else {
			try {
				BufferedReader br = new BufferedReader(new FileReader(listFile));
				String line="";
				 while (( line = br.readLine()) != null)
				 {
					 //ignore blank lines
					 if (line.trim()=="") 
						 continue;
					 
					 String[] segments = line.split(",");
					 //DateFormat format = DateFormat.getInstance();
					 SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
					 				 
					 Date deaddate = new Date();
					 try {
						 deaddate = format.parse(segments[1]);
					 } catch (ParseException e) {
						 _plugin.log("can't read the date:" + segments[1] + " resetting ban.");
						 deaddate = new Date(1);
					 }
					 
					 DeadPlayer player = new DeadPlayer(segments[0], deaddate);
					 _deadPlayers.add(player);
				 }
				 
				 _plugin.log(_deadPlayers.size() + " dead players loaded from the file.");
				
			} catch (IOException exc) {
				_plugin.log("Unable to read dead players file. " + exc.getMessage());
			
			}
		}
		
	}
	
	private void save() {
		File listFile = getDeadplayerFile();
		
		//DateFormat format = DateFormat.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		
		try {
			listFile.createNewFile();
			FileWriter fw = new FileWriter(listFile,false);
			
			for(int i=0; i<_deadPlayers.size(); i++)
			{
				fw.write(_deadPlayers.get(i).getPlayerName());
				fw.write(",");
				fw.write(format.format(_deadPlayers.get(i).getDeathDate()));
				fw.write(System.getProperty("line.separator"));				
			}
			
			fw.close();
		} catch (IOException exc) {
			_plugin.log("Unable to save dead players file. " + exc.getMessage());
		}
		
		
	}

	private File getDeadplayerFile() {
		File directory = _plugin.getDataFolder();
		File listFile = new File(directory,"deadplayers.txt");
		return listFile;
	}
	
	
}
