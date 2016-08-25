package de.shorty.onevone;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.shorty.onevone.api.CookieAPI;
import de.shorty.onevone.kit.KitManager;
import de.shorty.onevone.kit.KitSettings;
import de.shorty.onevone.manager.BoardManager;
import de.shorty.onevone.manager.MatchManager;
import de.shorty.onevone.manager.Matchmaking;
import de.shorty.onevone.manager.RequestManager;
import de.shorty.onevone.mysql.MySQL;
import de.shorty.onevone.stats.StatsManager;

public class ClassManager {

	private OneVOne onevone;
	private static ClassManager classmanager;
	
	public ClassManager(OneVOne onevone) {
		this.onevone = onevone;
		ClassManager.classmanager = this;
	}
	
	public static ClassManager getClassManager(){
		return classmanager;
	}
	
	public OneVOne getInstance(){
		return onevone.instance;
	}
	
	public String getPrefix(){
		return onevone.getPrefix();
	}
	
	public StatsManager getStatsManager(){
		return onevone.stats;
	}
	
	public BoardManager getBoardManager(){
		return onevone.boardmanager;
	}
	
	public RequestManager getRequestManager(){
		return onevone.requestmanager;
	}
	
	public Matchmaking getMatchmaking(){
		return onevone.matchmaking;
	}
	
	public MatchManager getMatchManager(){
		return onevone.matchmanager;
	}

	public KitSettings getKitSettings(){
		return onevone.kitsettings;
	}
	
	public KitManager getKitManager(){
		return onevone.kitmanager;
	}
	
	public CookieAPI getCookieAPI(){
		return onevone.api;
	}
	
	public MySQL getMySQL(){
		return onevone.mysql;
	}
	
	public String getLanguageString(String path, Player player){
		return onevone.getLanguageString(path, player);
	}
	public boolean getLanguageBoolean(String path){
		return onevone.getLanguageBoolean(path);
	}
	
	public HashMap<String, Location> getS1(){
		return onevone.S1;
	}
	public HashMap<String, Location> getS2(){
		return onevone.S2;
	}
	public HashMap<String, Location> getP1(){
		return onevone.P1;
	}
	public HashMap<String, Location> getP2(){
		return onevone.P2;
	}
	public HashMap<String, Location> getMiddle(){
		return onevone.Middle;
	}
	public boolean exist(String map){
		return classmanager.getCookieAPI().getConfiguration("config.yml", "1vs1").getStringList("1vs1.Maps").contains(map);
	}
}
