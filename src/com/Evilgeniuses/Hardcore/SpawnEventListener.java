package com.Evilgeniuses.Hardcore;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

public class SpawnEventListener extends PlayerListener {

	private HardcorePlugin _plugin = null;
	
	SpawnEventListener(HardcorePlugin plugin) {
		_plugin=plugin;
	}
	
	
	//this was probably too slow for real use
	/*public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (_plugin.getDeadPlayerList().isPlayerDead(playerName))
		{
			String livedate =  _plugin.getDeadPlayerList().whenWillPlayerLive(playerName).toString();
			player.kickPlayer("You have died. You will be dead until " + livedate);
			//PlayerRespawnEvent.Result.DENY
			
		}	
	}*/
	
	//this wasn't always being called for some reason, so I used login instead
	/*public void onPlayerPreLogin(PlayerPreLoginEvent event) {
		String playerName = event.getName();
		
		_plugin.log(playerName + " has prelogged in.");
		
		if (_plugin.getDeadPlayerList().isPlayerDead(playerName))
		{
			String livedate =  _plugin.getDeadPlayerList().whenWillPlayerLive(playerName).toString();
			event.setKickMessage("You will be dead until " + livedate);
			event.setResult(PlayerPreLoginEvent.Result.KICK_BANNED );
			_plugin.log(playerName + " was not allowed to login for being dead until " +  livedate +  ".");
		}
		
	}*/
	
	public void onPlayerLogin(PlayerLoginEvent event) {
		String playerName = event.getPlayer().getName();
		
		_plugin.log(playerName + " is trying to log in.");
		
		//if you get disconnected during your farewell, you can still reconnect within the farewell counter
		if (_plugin.getDeadPlayerList().isPlayerDead(playerName, true))
		{
			String livedate =  _plugin.getDeadPlayerList().whenWillPlayerLive(playerName).toString();
			event.setKickMessage("You will be dead until " + livedate);
			event.setResult(PlayerLoginEvent.Result.KICK_BANNED );
			_plugin.log(playerName + " was not allowed to login for being dead until " +  livedate +  ".");
		}
	
	}
}
