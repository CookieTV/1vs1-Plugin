package de.shorty.onevone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.shorty.onevone.api.CookieAPI;
import de.shorty.onevone.commands.Accept;
import de.shorty.onevone.commands.Challenge;
import de.shorty.onevone.commands.CookieTV;
import de.shorty.onevone.commands.Deny;
import de.shorty.onevone.commands.OvO;
import de.shorty.onevone.commands.Stats;
import de.shorty.onevone.kit.Kit;
import de.shorty.onevone.kit.KitManager;
import de.shorty.onevone.kit.KitSettings;
import de.shorty.onevone.listener.BlockBreakListener;
import de.shorty.onevone.listener.BlockPlaceListener;
import de.shorty.onevone.listener.EntityDamageByEntityListener;
import de.shorty.onevone.listener.EntityDamageListener;
import de.shorty.onevone.listener.EntityFoodChangeListener;
import de.shorty.onevone.listener.InventoryClickListener;
import de.shorty.onevone.listener.PlayerDeathListener;
import de.shorty.onevone.listener.PlayerInteractEntityListener;
import de.shorty.onevone.listener.PlayerInteractListener;
import de.shorty.onevone.listener.PlayerItemDropListener;
import de.shorty.onevone.listener.PlayerItemPickupListener;
import de.shorty.onevone.listener.PlayerJoinListener;
import de.shorty.onevone.listener.PlayerMoveListener;
import de.shorty.onevone.listener.PlayerQuitListener;
import de.shorty.onevone.listener.RegionListener;
import de.shorty.onevone.listener.ServerListener;
import de.shorty.onevone.manager.BoardManager;
import de.shorty.onevone.manager.MatchManager;
import de.shorty.onevone.manager.Matchmaking;
import de.shorty.onevone.manager.OvOInventory;
import de.shorty.onevone.manager.RequestManager;
import de.shorty.onevone.mysql.MySQL;
import de.shorty.onevone.stats.StatsManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class OneVOne extends JavaPlugin {

	public CookieAPI api;
	public MySQL mysql;

	public KitManager kitmanager;
	public KitSettings kitsettings;
	public Kit kit;

	public StatsManager stats;
	public MatchManager matchmanager;
	public BoardManager boardmanager;
	public RequestManager requestmanager;
	
	public Matchmaking matchmaking;
	public OvOInventory inventorys;
	
	public ClassManager classmanager;
	
	public OneVOne instance;
	
	public ArrayList<Player> build = new ArrayList<>();
	
	//Arenas
	
	public HashMap<String, Location> S1 = new HashMap<>();
	public HashMap<String, Location> S2 = new HashMap<>();
	public HashMap<String, Location> P1 = new HashMap<>();
	public HashMap<String, Location> P2 = new HashMap<>();
	public HashMap<String, Location> Middle = new HashMap<>();
	public static List<String> canbeused = new ArrayList<>();
	
	int broadcaster;

	@Override
	public void onEnable() {
		for(World w : Bukkit.getWorlds()){
			w.setTime(150);
			w.setGameRuleValue("doDaylightCycle", "false");
			w.setDifficulty(Difficulty.EASY);
		}
		instance = this;
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		/* Loading configuration */

		loadConfig();

		/* Fetching classes */

		fetchClasses();

		/* Loading language files */

		loadLanguageFiles();
		
		/* Loading scoreboard files */
		
		loadScoreboardFiles();

		/* starting with mysql section */

		mysql.CreateMySQLFile("1vs1");

		/* Important to not occur some errors */
		if (mysql.isConnected()) {

			stats.createTables();
			
		}

		/* Loading maps */
		
		loadMaps();
		
		/* Register Commands & Listener */
		
		this.getCommand("cookietv").setExecutor(new CookieTV(this));
		this.getCommand("1vs1").setExecutor(new OvO(this));
		this.getCommand("stats").setExecutor(new Stats(this));
		this.getCommand("kit").setExecutor(new de.shorty.onevone.commands.Kit(this));
		
		this.getCommand("accept").setExecutor(new Accept(this));
		this.getCommand("deny").setExecutor(new Deny(this));
		this.getCommand("challenge").setExecutor(new Challenge(this));
		
		
		this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
		this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
		this.getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
		this.getServer().getPluginManager().registerEvents(new EntityFoodChangeListener(this), this);
		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerItemDropListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerItemPickupListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		this.getServer().getPluginManager().registerEvents(new ServerListener(this), this);
		this.getServer().getPluginManager().registerEvents(new RegionListener(this), this);
		
		/* Sending main Plugin Message */
		Bukkit.getConsoleSender()
				.sendMessage("§8§l§m------------------------------------------------------------------------");
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("§7Plugin by §3§lShorty/CookieTV§7!");
		Bukkit.getConsoleSender().sendMessage("§7This Plugin is just allowed to be used for while accepting terms of service[§bCookieTV.de§7]");
		Bukkit.getConsoleSender().sendMessage("§c© 2016 CookieTV §3- §cAll Rights reserved");
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("§7More Plugins are available on CookieTV.de | §4not yet ;/");
		Bukkit.getConsoleSender().sendMessage("§7This System is supposed to:");
		Bukkit.getConsoleSender().sendMessage("§e" + this.getDescription().getDescription());
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender()
				.sendMessage("§8§l§m------------------------------------------------------------------------");
		
		try{
			Location wait = api.getLocation("locations.yml", "Location.zombie", "1vs1");
			@SuppressWarnings("deprecation")
			Zombie s = (Zombie) wait.getWorld().spawnCreature(wait, EntityType.ZOMBIE);
			if(isGerman()){
				s.setCustomName("§8► §2§lWarteschlange §8◄");
			}else{
				s.setCustomName("§8► §2§lMatchmaking §8◄");
			}
			s.setCanPickupItems(false);
			s.setCustomNameVisible(true);
			freezeEntity(s);
			}catch(Exception e1){
				Bukkit.getConsoleSender().sendMessage(getPrefix() + "§cCouldnt spawn Matchmaking Zombie. Please set the location!");
			}
			try{
			Location sb = api.getLocation("locations.yml", "Location.sheep", "1vs1");
			@SuppressWarnings("deprecation")
			Sheep sh = (Sheep) sb.getWorld().spawnCreature(sb, EntityType.SHEEP);
			sh.setColor(DyeColor.LIME);
			if(isGerman()){
				sh.setCustomName("§8► §2§lKit-Einstellungen §8◄");
			}else{
				sh.setCustomName("§8► §2§lKit-Settings §8◄");
			}
			sh.setCanPickupItems(false);
			sh.setCustomNameVisible(true);
			sh.setBaby();
			sh.setAgeLock(true);
			freezeEntity(sh);
			}catch(Exception e1){
			Bukkit.getConsoleSender().sendMessage(getPrefix() + "§cCouldnt spawn Kit-Settings Sheep. Please set the location!");
			}
		
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					broadcaster++;
					for(Player player : Bukkit.getOnlinePlayers()){
						
						if(matchmanager.isIngame(player)){
							
							if(matchmanager.getMatch(player).canMove()){
								api.sendActionbar("§3"+player.getName()+" §e§l"+matchmanager.getCurrentWins(player)+" §8| §e§l"+matchmanager.getCurrentWins(matchmanager.getOpponnent(player))+" §3"+matchmanager.getOpponnent(player).getName(), player);
							}else{			
							}
							boardmanager.updateIngame(player, false);
							
							
						}else{
							/*if(broadcaster == 4){
								Match.sendActionbar("§3ausgewähltes Kit§7» §6"+ckit.get(p.getName()).getName(), p);
							}else if(broadcaster == 8){
								Match.sendActionbar("§3Platz§7» §6"+Stats.getPlace(p), p);
							}else if(broadcaster == 12){
								Match.sendActionbar("§3Server§7» §6"+Bukkit.getServerName(), p);
							}else if(broadcaster == 16){
								Match.sendActionbar("§3Punkte§7» §6"+Stats.getP(p), p);
							}else if(broadcaster == 20){
								if(Matchmaking.contains(p)){
									Match.sendActionbar("§7» Suche §6Gegner§7...", p);
								}else{
									Match.sendActionbar("§cFordere Spieler heraus oder trete der Warteschlange bei!", p, p);
								}
								broadcaster = 0;
							}*/
						}
						
					}
					
				}
			}.runTaskTimer(this, 20, 20);
			
	}
	
	@Override
	public void onDisable() {
		for(Player player : Bukkit.getOnlinePlayers()){
			for (String kitname : kitsettings.getKits(player)) {
				Kit kit;
				if (kitmanager.isAvailable(kitname)) {
					
					kit = new Kit(kitname, false);
					kit.upload();
				} else {
					Bukkit.getConsoleSender()
							.sendMessage(getPrefix() + "§cThis kit isn't fetched, so it can't be uploaded!");
				}
			}
			
			stats.uploadPlayer(player);
			player.kickPlayer("Restart");
		}
		for(World world : Bukkit.getWorlds()){
			
			for(Entity entity : world.getEntities()){
				entity.remove();
			}
			
		}
		mysql.close();
	}

	public void fetchClasses() {

		api = new CookieAPI(this, "§e");
		mysql = new MySQL(this);

		kitmanager = new KitManager(this);
		kitsettings = new KitSettings(this);

		stats = new StatsManager(this);
		matchmanager = new MatchManager(this);
		boardmanager = new BoardManager(this);
		requestmanager = new RequestManager(this);
		
		matchmaking = new Matchmaking(this);
		inventorys = new OvOInventory(this);
		
		classmanager = new ClassManager(this);
	}

	public void loadConfig() {
		FileConfiguration cfg = this.getConfig();

		cfg.options().copyDefaults(true);

		cfg.addDefault("Console.debug", true);
		cfg.addDefault("Console.lobbyservername", "lobby");
		cfg.addDefault("Language.file", "lang_EN.yml");
		
		cfg.addDefault("1vs1.Maps", new ArrayList<>());

		cfg.addDefault("Stats.startingpoints", 100);
		cfg.addDefault("Stats.pointsforkill", 5);
		cfg.addDefault("Stats.pointsforwin", 20);
		cfg.addDefault("Stats.pointsforgame", 5);
		cfg.addDefault("Stats.pointslostondeath", 0);
		cfg.addDefault("Stats.pointslostonlose", 0);

		saveConfig();
	}
	
	public void loadScoreboardFiles(){
	
		if (!api.getFile("scoreboard.yml", "1vs1").exists()) {

			api.createNewFile("scoreboard.yml", "1vs1");
			
			FileConfiguration cfg = api.getConfiguration("scoreboard.yml", "1vs1");

			cfg.options().copyDefaults(true);
			cfg.options().header(
					"You may use following %_% actions -> %PLAYER%, %SERVERNAME%, %KIT%. Please pay to attention to not use more than 16 characters per line!");

			cfg.addDefault("Scoreboard.header", "&7[&3%PLAYER%&7]");
			
			cfg.addDefault("Scoreboard.kitdisplay", "&3Kit: &c%KIT%");
			
			cfg.addDefault("Scoreboard.prefix.Admin", "&4");
			cfg.addDefault("Scoreboard.prefix.CoAdmin", "&c");
			cfg.addDefault("Scoreboard.prefix.Developer", "&b");
			cfg.addDefault("Scoreboard.prefix.Moderator", "&3");
			cfg.addDefault("Scoreboard.prefix.Builder", "&2");
			cfg.addDefault("Scoreboard.prefix.Youtuber", "&5");
			cfg.addDefault("Scoreboard.prefix.Premium", "&6");
			cfg.addDefault("Scoreboard.prefix.Player", "&a");
			cfg.addDefault("Scoreboard.prefix.Ingame", "&8");
		
			try {
				cfg.save(api.getFile("scoreboard.yml", "1vs1"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}

	public void loadLanguageFiles() {

		new File("plugins/1vs1/language").mkdir();

		if (!api.getFile("lang_EN.yml", "1vs1/language").exists()) {

			api.createNewFile("lang_EN.yml", "1vs1/language");
			api.createNewFile("lang_DE.yml", "1vs1/language");

			FileConfiguration en = api.getConfiguration("lang_EN.yml", "1vs1/language");
			FileConfiguration de = api.getConfiguration("lang_DE.yml", "1vs1/language");

			en.options().copyDefaults(true);
			en.options().header(
					"Use %PLAYER% to send the message receiver a name [ATTENTION: This function is not available in every message!]");
			de.options().copyDefaults(true);
			de.options().header(
					"Benutze %PLAYER% um jeweils den Spieler anzeigen zu lassen. [ACHTUNG: Diese Funktion ist nur bei manchen Nachrichten möglich!]");
			
			/* Some stuff */
			en.addDefault("messages.prefix", "&91vs1&8> &7");
			de.addDefault("messages.prefix", "&91vs1&8> &7");
			
			en.addDefault("messages.tabheader", "&9YourServer &8- &61vs1");
			en.addDefault("messages.tabfooter", "&7Youre Playing on a 1vs1 Server");
			de.addDefault("messages.tabheader", "&9YourServer &8- &61vs1");
			de.addDefault("messages.tabfooter", "&7Youre Playing on a 1vs1 Server");
			
			en.addDefault("messages.nopermissions", "%PREFIX%&cYou are not allowed to use that command!");
			de.addDefault("messages.nopermissions", "%PREFIX%&cDu hast auf diesen Befehl keinen Zugriff!");
			
			en.addDefault("messages.kitsaved", "%PREFIX%&7Your kit '&b%KIT%&7' was successfully saved!");
			de.addDefault("messages.kitsaved", "%PREFIX%&7Dein Kit '&b%KIT%&7' wurde erfolgreich gespeichert!");
			
			en.addDefault("messages.allowadvertising", true);
			de.addDefault("messages.allowadvertising", true);
			
			/* Challenge */
			en.addDefault("messages.challenged", "%PREFIX%You got challenged from &b%CHALLENGER% &7with kit &b%KIT%&7!");
			en.addDefault("messages.challengedsomebody", "%PREFIX%You challenged &b%CHALLENGED% &7with kit &b%KIT%&7!");
			en.addDefault("messages.sendacceptdenymessage", true);
			
			de.addDefault("messages.challenged", "%PREFIX%Du wurdest von &b%CHALLENGER% &7mit dem Kit &b%KIT% &7herausgefordert!");
			de.addDefault("messages.challengedsomebody", "%PREFIX%Du hast &b%CHALLENGED% &7mit dem Kit &b%KIT% &7herausgefordert!");
			de.addDefault("messages.sendacceptdenymessage", true);
			
			/* Matches */
			en.addDefault("messages.matchwon", "%PREFIX%&b%WINNER% &7won match &b%GAMES% &7with &4%HEARTS% %HEARTSYMBOL%&7!");
			de.addDefault("messages.matchwon", "%PREFIX%&b%WINNER% &7hat Match &b%GAMES% &7mit &4%HEARTS% %HEARTSYMBOL% &7gewonnen!");
			en.addDefault("messages.gamewon", "%PREFIX%&b%WINNER% &7won the game with &b%WINS% &7out of &b%GAMES% &7matches!");
			de.addDefault("messages.gamewon", "%PREFIX%&b%WINNER% &7hat das Spiel mit &b%WINS% &7von &b%GAMES% &7Spielen gewonnen!");
			en.addDefault("messages.resetarena", "%PREFIX%Resetting arena, please wait!");
			de.addDefault("messages.resetarena", "%PREFIX%Die Arena wird zurückgesetzt!");
			
			en.addDefault("messages.titlecountdown", true);
			de.addDefault("messages.titlecountdown", true);
			en.addDefault("messages.countdown", "%PREFIX%The battle starts in %TIME% second(s)!");
			de.addDefault("messages.countdown", "%PREFIX%Das Match startet in %TIME% Sekunde(n)!");
			en.addDefault("messages.countdowngo", "%PREFIX%The battle just started! Go!");
			de.addDefault("messages.countdowngo", "%PREFIX%GO!");
			en.addDefault("messages.shootfireworkaftermatch", true);
			de.addDefault("messages.shootfireworkaftermatch", true);
			en.addDefault("messages.fightends", "%PREFIX%&7The battle ends in &b%TIME% &7second(s)!");
			de.addDefault("messages.fightends", "%PREFIX%&7Der Kampf endet &b%TIME% &7Sekunden!");
			en.addDefault("messages.gettothemiddle", "%PREFIX%&7The Person who is closer to the middle of the arena wins that battle!");
			de.addDefault("messages.gettothemiddle", "%PREFIX%&7Die Person, die dem Mittelpunkt am nähesten steht, gewinnt den Kampf!");
			en.addDefault("messages.timeisover", "%PREFIX%&7Time is over, the winner is");
			de.addDefault("messages.timeisover", "%PREFIX%&7Die Zeit ist vorbei...");

			/* KitSettigs + Matchmaking */
			
			en.addDefault("messages.kdchanged", "%PREFIX%&7The requested K/D was set to &b%KD%&7.");
			de.addDefault("messages.kdchanged", "%PREFIX%&7Die angeforderte K/D wurde zu &b%KD% &7gesetzt.");
			
			en.addDefault("messages.changedGamemode", "%PREFIX%&7The Gamemode of &b%KIT% &7was changed to &b%GAMEMODE%&7.");
			de.addDefault("messages.changedGamemode", "%PREFIX%&7Der Spielmodus von deinem Kit wurde zu &b%GAMEMODE% &7geändert.");
			
			try {
				en.save(api.getFile("lang_EN.yml", "1vs1/language"));
				de.save(api.getFile("lang_DE.yml", "1vs1/language"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

	public boolean isGerman(){
		return this.getConfig().getString("Language.file").equalsIgnoreCase("lang_DE.yml");
	}
	
	public String getLanguageString(String path, Player player) {
		if (this.getConfig().getString("Language.file").equalsIgnoreCase("lang_EN.yml")) {
			FileConfiguration cfg = api.getConfiguration("lang_EN.yml", "1vs1/language");
			String str = cfg.getString(path);
			str = str.replace("&", "§");
			str = str.replace("%PREFIX%", getPrefix());
			str = str.replace("%SERVERNAME%", Bukkit.getServerName());
			if (player != null) {
				str = str.replace("%PLAYER%", player.getName());
				str = str.replace("%UUID%", player.getUniqueId().toString());
			}
			return str;
		} else {
			FileConfiguration cfg = api.getConfiguration("lang_DE.yml", "1vs1/language");
			String str = cfg.getString(path);
			if (player != null) {
				str = str.replace("%PLAYER%", player.getName());
				str = str.replace("%UUID%", player.getUniqueId().toString());
			}
			str = str.replace("&", "§");
			str = str.replace("%SERVERNAME%", Bukkit.getServerName());
			str = str.replace("%PREFIX%", getPrefix());
			return str;
		}
	}
	public boolean getLanguageBoolean(String path) {
		if (this.getConfig().getString("Language.file").equalsIgnoreCase("lang_EN.yml")) {
			FileConfiguration cfg = api.getConfiguration("lang_EN.yml", "1vs1/language");
			boolean b = cfg.getBoolean(path);
			return b;
		} else {
			FileConfiguration cfg = api.getConfiguration("lang_DE.yml", "1vs1/language");
			boolean b = cfg.getBoolean(path);
			return b;
		}
	}
	
	public String getScoreboardString(String path, Player player) {
			FileConfiguration cfg = api.getConfiguration("scoreboard.yml", "1vs1");
			String str = cfg.getString(path);
			str = str.replace("&", "§");
			str = str.replace("%SERVERNAME%", Bukkit.getServerName());
			if (player != null) {
				str = str.replace("%PLAYER%", player.getName());
				str = str.replace("%UUID%", player.getUniqueId().toString());
			}
			return str;
	}

	public String getPrefix() {
		if (this.getConfig().getString("Language.file").equalsIgnoreCase("lang_EN.yml")) {
			FileConfiguration cfg = api.getConfiguration("lang_EN.yml", "1vs1/language");
			String str = cfg.getString("messages.prefix");
			str = str.replace("&", "§");
			return str;
		} else {
			FileConfiguration cfg = api.getConfiguration("lang_DE.yml", "1vs1/language");
			String str = cfg.getString("messages.prefix");
			str = str.replace("&", "§");
			return str;
		}
	}
	
	 public void sendAcceptDenyMessage(Player player, Player from) {

		   TextComponent accept = new TextComponent("§2Accept");
		   accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/accept " + from.getName()));
		   TextComponent deny = new TextComponent("§cDeny");
		   deny.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/deny " + from.getName()));
		   TextComponent txt = new TextComponent(" §8|§r ");
		   TextComponent msg = new TextComponent(getPrefix());
		   msg.addExtra(accept);
		   msg.addExtra(txt);
		   msg.addExtra(deny);

		   player.spigot().sendMessage(msg);
		  
	 }

	public void loadMaps() {

		for (String s : api.getConfiguration("config.yml", "1vs1").getStringList("1vs1.Maps")) {
			try {
				S1.put(s.toLowerCase(), api.getLocation("arena.yml", s.toLowerCase() + ".start1", "1vs1"));
				S2.put(s.toLowerCase(), api.getLocation("arena.yml", s.toLowerCase() + ".start2", "1vs1"));
				P1.put(s.toLowerCase(), api.getLocation("arena.yml", s.toLowerCase() + ".zone1", "1vs1"));
				P2.put(s.toLowerCase(), api.getLocation("arena.yml", s.toLowerCase() + ".zone2", "1vs1"));
				Middle.put(s.toLowerCase(), api.getLocation("arena.yml", s.toLowerCase() + ".middle", "1vs1"));
				canbeused.add(s);
			} catch (Exception e1) {

				Bukkit.getConsoleSender().sendMessage(
						getPrefix() + "§cOups! A Location for Arena '§b" + s + "§c' couldn't be safed or does not exists! Disabling arena...");
			}

		}

	}
	public void freezeEntity(Entity en){
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) en).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte) 1);
        compound.setByte("Silent", (byte) 1);
        nmsEn.f(compound);
    }
}
