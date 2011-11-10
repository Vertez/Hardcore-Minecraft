package com.Evilgeniuses.Hardcore;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

public class SpawnEventListener extends PlayerListener {

	private HardcorePlugin _plugin = null;
	
	SpawnEventListener(HardcorePlugin plugin) {
		_plugin=plugin;
	}
		
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
