package com.Evilgeniuses.Hardcore;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

public class HardcorePlugin extends JavaPlugin {
	
	
	private Logger _logger = Logger.getLogger("Minecraft");
	private String _pluginName;
	private DeadPlayerList _deadPlayerList = null;
	public HardcoreConfiguration _config = null;
	public HardcorePlugin _thisPlugin;
	
		
	@Override
	public void onDisable() {
		PluginDescriptionFile yml = this.getDescription();
		_logger.info(yml.getName() + " is now disabled.");
	}

	@Override
	public void onEnable() {
		PluginDescriptionFile yml = this.getDescription();
		_pluginName = yml.getName();
		this.log(yml.getVersion()  + " loading.");
				
		PluginManager pm = this.getServer().getPluginManager();
		DeathEventListener deathListener = new DeathEventListener(this);
		SpawnEventListener spawnListener = new SpawnEventListener(this);
		
		//hook on to death and login
		pm.registerEvent(Event.Type.ENTITY_DEATH, deathListener, Event.Priority.Normal, this);
		
		pm.registerEvent(Event.Type.PLAYER_LOGIN, spawnListener, Event.Priority.Normal, this);
				
		_config = new HardcoreConfiguration(this);
		_deadPlayerList = new DeadPlayerList(this);
		
		//hookup the /hardcore command
		HardcoreCommandExecutor cmd=new HardcoreCommandExecutor(this);
		getCommand("hardcore").setExecutor(cmd);
		
		//really java, really?
		_thisPlugin=this;
		this.getServer().getScheduler().scheduleSyncRepeatingTask(_thisPlugin, new Runnable() {

		    public void run() {
		    	//_thisPlugin.log("Checking for dead players.");
		    	_thisPlugin.reapDeadPlayers();
		    }
		}, 200L, _thisPlugin.getHardcoreConfiguration().reaperCheckSeconds * 20L);
		
		this.log(yml.getVersion()  + " is now enabled!");
	}
	
	
	public void log(String message) {
		_logger.info("[" + _pluginName + "] " + message);
	}
	
	public DeadPlayerList getDeadPlayerList() {
		return _deadPlayerList;
	}
	
	public HardcoreConfiguration getHardcoreConfiguration() {
		return _config;
	}
	
	public void reapDeadPlayers() {
		//find people who are on the dead list but still logged on
		
		//get a list of people who are logged on
		Player[] onlinePlayers = this.getServer().getOnlinePlayers();
		
		//check each one to see if they are dead
		for (int i=0; i<onlinePlayers.length; i++) {
			Player player = onlinePlayers[i];
			String playerName = player.getName();
			
			if (this.getDeadPlayerList().isPlayerDead(playerName,true))
			{
				//if so, kick them
				String livedate =  this.getDeadPlayerList().whenWillPlayerLive(playerName).toString();
				player.kickPlayer("You have died. You will be dead until " + livedate);
				_thisPlugin.log("The reaper has caught up with " + playerName + " and taken them away.");
			}	
		}

	}

	
	
}



//http://www.youtube.com/user/GTOTechnology#p/u/6/PBEmldV4LhA