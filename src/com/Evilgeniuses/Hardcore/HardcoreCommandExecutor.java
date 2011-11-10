package com.Evilgeniuses.Hardcore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class HardcoreCommandExecutor implements CommandExecutor  {
	
	private HardcorePlugin _plugin;
	
	HardcoreCommandExecutor(HardcorePlugin plugin) {
		_plugin=plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
				
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
	 
		if (command.getName().equalsIgnoreCase("hardcore"))
		{
			if (args.length==0)
				return false;
			
			if (args[0].equalsIgnoreCase("res")) 
				return runResCommand(sender,player,args);
			else if (args[0].equalsIgnoreCase("slay"))
				return runSlayCommand(sender,player,args);
			else if (args[0].equalsIgnoreCase("info"))
				return runInfoCommand(sender, player, args);
			else if (args[0].equalsIgnoreCase("setpurgatory"))
				return runSetPurgatoryCommand(sender, player);
			else 
				return false;

		}
		
		return true;
	}
	
		private boolean runSetPurgatoryCommand(CommandSender sender, Player player) {
			if (player==null)  {
				sender.sendMessage("You can only set purgatory's location in person");
				return false;
			}
			
			_plugin.getHardcoreConfiguration().setPurgatory(player.getLocation());
			
			sender.sendMessage("Purgatory location set to " + player.getLocation().toString());
			return true;
		}

		private boolean runSlayCommand(CommandSender sender, Player player,
			String[] args) {
		
			//if it was run from the console or if they have permission
			if((player==null) || (player.hasPermission("hardcore.slay")) || player.isOp())
			{
				if (args.length==2) 
				{
					String playerName = args[1];
					
					//make sure they aren't dead first, that would be overkill, ha!
					if (_plugin.getDeadPlayerList().isPlayerDead(playerName, false)) 
					{
						
						//let them know
						sender.sendMessage(playerName + " is already on the dead list. Nothing to do.");
						return false;
						
					}
					else
					{
						//if they are, remove them from the list.
						Player victim = _plugin.getServer().getPlayerExact(playerName);
						if (victim==null) {
							sender.sendMessage(playerName + " was not found, they must be online to slay.");
							return false;
						} else {
							victim.damage(10000);
							sender.sendMessage(playerName + " slain. When the reaper comes, they will be kicked.");
							return true;
						}
					}

				}
				else //not enough params
				{
					sender.sendMessage("You must supply a playername.");
					return false;
					
				}

			} 
			else  //no permissions
			{
				sender.sendMessage("You don't have permission to run this command.");
				return true;
			}

	}

		private boolean runResCommand(CommandSender sender, Player player, String[] args) {

			//if it was run from the console or if they have permission
			if((player==null) || (player.hasPermission("hardcore.res")) || player.isOp())
			{
				if (args.length==2) 
				{
					String playerName = args[1];
					
					//make sure they are dead first
					if (_plugin.getDeadPlayerList().isPlayerDead(playerName, false)) 
					{
						//if they are, remove them from the list.
						_plugin.getDeadPlayerList().removePlayer(playerName);
						sender.sendMessage(playerName + " was removed from the dead list. They should be able to log on again.");
						return true;
					}
					else
					{
						//otherwise let them know
						sender.sendMessage(playerName + " was not on the dead list. Nothing to do.");
						return false;
					}

				}
				else //not enough params
				{
					sender.sendMessage("You must supply a playername.");
					return false;
					
				}

			} 
			else  //no permissions
			{
				sender.sendMessage("You don't have permission to run this command.");
				return true;
			}

		}

		
		private boolean runInfoCommand(CommandSender sender, Player player, String[] args) 
		{
			
				//if it was run from the console or if they have permission
				if((player==null) || (player.hasPermission("hardcore.info")) || player.isOp())
				{
					if (args.length==2) 
					{
						String playerName = args[1];
						
						if (_plugin.getDeadPlayerList().isPlayerDead(playerName, false)) 
						{
							sender.sendMessage(playerName + " is dead and will be allowed back on " + _plugin.getDeadPlayerList().whenWillPlayerLive(playerName));
						} 
						else 
						{
							sender.sendMessage(playerName + " is not on the dead list.");
						}

					}
					else //not enough params
					{
						sender.sendMessage("You must supply a playername.");
						return false;
						
					}

				} 
				else  //no permissions
				{
					sender.sendMessage("You don't have permission to run this command.");
					return true;
				}
				
				return true;

		}

}
