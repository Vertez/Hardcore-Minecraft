package com.Evilgeniuses.Hardcore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class DeadLog {

	private HardcorePlugin _plugin;

	DeadLog(HardcorePlugin plugin) {
		_plugin = plugin;
	}

	public void addDeadLog(String playerName, Date deadDate, int expLevel,
			int points, double playerX, double playerY, double playerZ, String deathMessage) {
		// check if the file exists
		File logFile = this.getDeadlogFile();

		FileWriter fw;

		try {

			if (!logFile.exists()) {
				// if not, make it and put the header in
				fw = new FileWriter(logFile, false);
				this.writeHeader(fw);
			} else {
				// if it exists open start appending.
				fw = new FileWriter(logFile, true);
			}

			// write the log entry
			fw.write(playerName + ",");
			fw.write(deadDate.toString() + ",");
			fw.write(expLevel + ",");
			fw.write(points + ",");
			fw.write(playerX + ",");
			fw.write(playerY + ",");
			fw.write(playerZ + ",");
			fw.write(deathMessage.replaceAll(",", ""));
			
			fw.write(System.getProperty("line.separator"));		

			fw.close();

		} catch (IOException exc) {
			_plugin.log("Unable to save dead players file. " + exc.getMessage());
		}
	}

	private void writeHeader(FileWriter fw) throws IOException {
		fw.write("PlayerName,");
		fw.write("DeadDate,");
		fw.write("ExperienceLevel,");
		fw.write("Points,");
		fw.write("X,");
		fw.write("Y,");
		fw.write("Z,");
		fw.write("DeathMessage");
		fw.write(System.getProperty("line.separator"));		
	}

	private File getDeadlogFile() {
		File directory = _plugin.getDataFolder();
		File listFile = new File(directory, "deadlog.csv");
		return listFile;
	}

}
