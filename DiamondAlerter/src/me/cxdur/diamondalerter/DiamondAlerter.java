package me.cxdur.diamondalerter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DiamondAlerter extends JavaPlugin {
	
	public AlertListener listener = new AlertListener(this);
	
	public boolean alertOp;
	public boolean alertPermission;
	public boolean logMessage;
	public boolean enableOp;
	public boolean enablePermission;
	public boolean enableCreative;
	
	public String watchPermission = "diamondalerter.alert";
	public String ignorePermission = "diamondalerter.ignore";
	
	public String alertMessage;
	
	private FileConfiguration config;
	
	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
	public void alertStab(Player p, int x, int y, int z) {
		for (Player online: Bukkit.getOnlinePlayers()) {
			if (alertOp = true && online.isOp() || (alertPermission = true && online.hasPermission("diamondalerter.alert"))) {
				String message = alertMessage.replace("#Player", p.getName());
				String locatedMsg = message + "X: " + x + ", Y: " + y + ", Z: " + z + ".";
				online.sendMessage(ChatColor.GREEN + "["+ChatColor.RED + "DiamondAlerter"+ChatColor.GREEN + "] " + locatedMsg); 
				print(locatedMsg);
			 if (logMessage = true) {
				try {
					File logFile = new File(getDataFolder() + "/log.txt");
					BufferedWriter br = new BufferedWriter(new FileWriter(logFile, true));
					br.write("["+getDateTime()+"] " + locatedMsg);
					br.newLine();
					br.close();
					print("Logged DiamondAlerter stuff to file.");
				} catch (Exception e) {
					print("Error on logging DiamondAlerter stuff to file.");
					e.printStackTrace();
				}				} else {
					print("Did not log the DiamondAlerter stuff to file, not enabled in config?.");
				}
			}
		}	}
	
	public void loadConfig() throws FileNotFoundException, IOException, InvalidConfigurationException {
		File configFile = new File(this.getDataFolder() + "/config.yml");
		if (!configFile.exists()) {
		config.options().header("DiamondAlerter configuration # please handle this as a normal YML file.");
		config.addDefault("diamondalerter.alert.op", true);
		config.addDefault("diamondalerter.alert.permission", true);
		config.addDefault("diamondalerter.alert.log", true);
		config.addDefault("diamondalerter.enable.op", false);
		config.addDefault("diamondalerter.enable.permission", false);
		config.addDefault("diamondalerter.enable.creative", false);
		config.addDefault("diamondalerter.alert.message", "#Player mined a diamond block at: ");
		config.options().copyDefaults(true);
			config.save(configFile);
			config.load(configFile);
			
		alertOp = true;
		alertPermission = true;
		logMessage = true;
		enableOp = false;
		enablePermission = false;
		enableCreative = false;
		
		alertMessage = "#Player mined a diamond block at: ";
		
			print("# - Config successfully loaded.");
		} else {
			config.load(configFile);
		
			alertOp = config.getBoolean("diamondalerter.alert.op");
			alertPermission = config.getBoolean("diamondalerter.alert.permission");
			logMessage = config.getBoolean("diamondalerter.alert.log");
			alertMessage = config.getString("diamondalerter.alert.message");
			enableOp = config.getBoolean("diamondalerter.enable.op");
			enablePermission = config.getBoolean("diamondalerter.enable.permission");
			enableCreative = config.getBoolean("diamondalerter.enable.creative");
			
			print("# - Config loaded.");
		}
	}
	
	public void onEnable() {
		config = getConfig();
		getDataFolder().mkdir();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
		try {
			loadConfig();
		} catch (FileNotFoundException e) {
			print("@ - Error on loading config!");
			e.printStackTrace();
		} catch (IOException e) {
			print("@ - Error on loading config!");
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			print("@ - Error on loading config!");
			e.printStackTrace();
		}
		print("Enabled DiamondAlerter plugin by CXdur!");
	}
	
	public void onDisable() {
		print("Disabled DiamondAlerter plugin by CXdur!");
	}
	
	public void print(String message) {
		System.out.println("[DiamondAlerter] " + message);
	}
	
}
