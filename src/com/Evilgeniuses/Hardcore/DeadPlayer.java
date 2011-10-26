package com.Evilgeniuses.Hardcore;

import java.util.Date;

public class DeadPlayer {
	private String _playerName;
	private Date DeathDate;
	
	DeadPlayer(String playerName, Date deathDate) {
		setPlayerName(playerName);
		setDeathDate(deathDate);
	}
	
	public String getPlayerName() {
		return _playerName;
	}

	public void setPlayerName(String playerName) {
		_playerName = playerName;
	}

	public Date getDeathDate() {
		return DeathDate;
	}

	public void setDeathDate(Date deathDate) {
		DeathDate = deathDate;
	}
}