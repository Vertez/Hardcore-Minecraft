package com.Evilgeniuses.Hardcore;

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
				
		_plugin.getDeadPlayerList().addPlayer(player,de.getDeathMessage());
				
		if (_plugin.getHardcoreConfiguration().doThunderAndLightningOnDeath)
			doSoundAndFury(player.getLocation(),player.getWorld());
		
	}
	
	public void doSoundAndFury(Location where, World whatWorld) {
		whatWorld.strikeLightningEffect(where);
		whatWorld.setThunderDuration(_plugin.getHardcoreConfiguration().thunderLengthSeconds*20);
		whatWorld.setThundering(true);
	}
	
}
