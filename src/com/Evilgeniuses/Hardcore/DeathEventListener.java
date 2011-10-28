package com.Evilgeniuses.Hardcore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEventListener extends EntityListener {
	
	private HardcorePlugin _plugin = null;
	 
	DeathEventListener(HardcorePlugin plugin) {
		_plugin = plugin;
	}

	public void onEntityDeath(EntityDeathEvent event) {
		//make sure it is a player
		if (!(event instanceof PlayerDeathEvent)) 
			return;
		
		Player player = (Player)event.getEntity();
		PlayerDeathEvent de = (PlayerDeathEvent)event;
				
		//don't add the player more than once to the list
		if (!_plugin.getDeadPlayerList().isPlayerDead(player.getName(), false)) {
			_plugin.getDeadPlayerList().addPlayer(player,de.getDeathMessage());
		
			//they won't get another farewell message if they die during farewell
			if (_plugin.getHardcoreConfiguration().finalFarewell) {
				doFinalFarewellMessage(player);
			}
		}
		
		if (_plugin.getHardcoreConfiguration().doThunderAndLightningOnDeath)
			doSoundAndFury(player.getLocation(),player.getWorld());
				
	}
	
	private void doFinalFarewellMessage(Player player) {
		player.sendMessage(ChatColor.RED + "You have died. You have been granted " + _plugin.getHardcoreConfiguration().finalFarewellSeconds + " seconds to say your final farewell.");
		
	}
	
	public void doSoundAndFury(Location where, World whatWorld) {
		whatWorld.strikeLightningEffect(where);
		whatWorld.setThunderDuration(_plugin.getHardcoreConfiguration().thunderLengthSeconds*20);
		whatWorld.setThundering(true);

	}
	
	
	
}
