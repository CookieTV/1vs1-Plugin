package de.shorty.onevone.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.shorty.onevone.ClassManager;
import de.shorty.onevone.kit.Kit;

public class Match {

	Player player;
	Player player2;

	Map map;
	Kit kit;

	String type;

	int cd;
	int time;
	int neededwins;
	int games;
	
	boolean move;
	boolean stopcd;
	boolean titlecd;
	boolean shootfw;
	
	ClassManager classmanager;

	public Match(Player player, Player player2, Kit kit) {
		this.classmanager = ClassManager.getClassManager();
		
		this.player = player;
		this.player2 = player2;
		this.kit = kit;
		this.type = this.kit.getType();
		
		this.titlecd = classmanager.getLanguageBoolean("messages.titlecountdown");
		this.shootfw = classmanager.getLanguageBoolean("messages.shootfireworkaftermatch");
	}

	/* */
	
	public boolean canMove(){
		return move;
	}
	public boolean canDamage(){
		return move == false;
	}
	
	public Kit getRealKit() {
		return kit;
	}
	
	/* Setup */

	public String getKit() {
		return kit.getName();
	}

	public int getTime() {
		return time;
	}

	public String getTL() {
		int t = time;
		int min = t / 60;

		for (int i = 0; i < min; i++) {

			t -= 60;

		}
		if (t < 10) {
			return "§9" + min + "§7:§90" + t;
		} else {
			return "§9" + min + "§7:§9" + t;
		}
	}

	public String getPlaying() {

		if (type.equalsIgnoreCase("Fw")) {
			return "Best of 1!";
		} else if (type.equalsIgnoreCase("Bo3")) {
			return "Best of 3!";
		} else if (type.equalsIgnoreCase("Bo5")) {
			return "Best of 5!";
		} else if (type.equalsIgnoreCase("Ft3")) {
			return "First to 3!";
		} else {
			return "Best of 1!";
		}

	}

	public void setup() {
		//Removing lobby hologram for no duplicates
		classmanager.getCookieAPI().removeHolo(player);
		classmanager.getCookieAPI().removeHolo(player2);
		if (type.equalsIgnoreCase("Bo3")) {

			this.neededwins = 2;

		} else if (type.equalsIgnoreCase("FW")) {

			neededwins = 1;

		} else if (type.equalsIgnoreCase("Bo5")) {

			neededwins = 3;

		} else if (type.equalsIgnoreCase("Ft3")) {

			neededwins = 3;

		} else {

			neededwins = 1;

		}
		Map map = new Map(true);
		if (map.getName().equalsIgnoreCase("NoMap")) {
			player.sendMessage("§cLeider wurde keine Map gefunden!");
			player2.sendMessage("§cLeider wurde keine Map gefunden!");
			return;
		}
		Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix() + "§7Starting Match on §b" + map.getName() + "§7...");
		map.save();
		map.add();
		move = false;
		player.teleport(map.getStart1());
		player2.teleport(map.getStart2());
		classmanager.getMatchManager().setVector(player, map.getStart1().toVector());
		classmanager.getMatchManager().setVector(player2, map.getStart2().toVector());
		player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 1F);
		player2.playSound(player2.getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 1F);
		classmanager.getMatchManager().set(player, player2);
		classmanager.getMatchManager().clearPlayer(player);
		classmanager.getMatchManager().clearPlayer(player2);
		classmanager.getMatchManager().setMap(player, map);
		classmanager.getMatchManager().setMap(player2, map);
		classmanager.getMatchManager().setCurrentWin(player, 0);
		classmanager.getMatchManager().setCurrentWin(player2, 0);
		
		time = 210;
		cd = 6;
		this.map = map;
		/* To make sure the right person is displayed in green and the opponent in red ;) */
		String rules = kit.getRule();
		String name = kit.getName();
		player.sendMessage("§8§m-------------------------------------------------");
		player.sendMessage("");
		player.sendMessage("           §2" + player.getName() + " §8vs §4" + player2.getName());
		player.sendMessage("           §3Type§7» §6" + getPlaying());
		player.sendMessage("           §3Kit§7» §6" + name);
		if(classmanager.getInstance().isGerman()){
				player.sendMessage("           §3Einstellungen§7» §b" + rules);
		}else{
				player.sendMessage("           §3Settings§7» §b" + rules);
		}
		player.sendMessage("");
		player.sendMessage("§8§m-------------------------------------------------");
		player2.sendMessage("§8§m-------------------------------------------------");
		player2.sendMessage("");
		player2.sendMessage("           §2" + player2.getName() + " §8vs §4" + player.getName());
		player2.sendMessage("           §3Type§7» §6" + getPlaying());
		player2.sendMessage("           §3Kit§7» §6" + kit.getName());
		if(classmanager.getInstance().isGerman()){
				player2.sendMessage("           §3Einstellungen§7» §6" + rules);
		}else{
				player2.sendMessage("           §3Settings§7» §6" + rules);
		}
		player2.sendMessage("");
		player2.sendMessage("§8§m-------------------------------------------------");

		classmanager.getStatsManager().addGame(player);
		classmanager.getStatsManager().addGame(player2);
		
		classmanager.getRequestManager().cancel(player);
		
		classmanager.getMatchManager().setMatch(player, this);
		classmanager.getMatchManager().setMatch(player2, this);
		
		classmanager.getMatchManager().set(player, player2);
		classmanager.getBoardManager().setScoreboard(player);
		classmanager.getBoardManager().setScoreboard(player2);
		classmanager.getBoardManager().updateIngame(player, true);
		classmanager.getBoardManager().updateIngame(player2, true);
	}
	
	public void nextRound(Player winner, Player loser){
		stopcd = true;
		double heart = winner.getHealth()/2;
		Math.round(heart);
		String s = "" + heart;
		s = s.substring(0, 3);
		classmanager.getMatchManager().clearPlayer(player);
		classmanager.getMatchManager().clearPlayer(player2);
		player.teleport(map.getStart1());
		player2.teleport(map.getStart2());
		classmanager.getBoardManager().updateIngame(player, true);
		classmanager.getBoardManager().updateIngame(player2, true);
		classmanager.getMatchManager().setCurrentWin(winner, classmanager.getMatchManager().getCurrentWins(winner) + 1);
		cd = 6;
		if(type.equalsIgnoreCase("FW")){
			classmanager.getStatsManager().addWin(winner);
			Map m = map;
			m.clearDrops(winner);
			m.clearDrops(loser);
			m.regenerate();
			m.remove();
			setEnd(winner, loser);
			classmanager.getMatchManager().remove(player, player2);
			String gamewon = classmanager.getLanguageString("messages.gamewon", null);
			gamewon = gamewon.replace("%WINNER%", winner.getName());
			gamewon = gamewon.replace("%WINS%", "" + classmanager.getMatchManager().getCurrentWins(winner));
			gamewon = gamewon.replace("%GAMES%", "" + games);
			sendMessage(gamewon);
		}else{	
			if(classmanager.getMatchManager().getCurrentWins(winner) >= this.neededwins){
				classmanager.getStatsManager().addWin(winner);
				Map m = map;
				m.clearDrops(winner);
				m.clearDrops(loser);
				m.regenerate();
				m.remove();
				String gamewon = classmanager.getLanguageString("messages.gamewon", null);
				gamewon = gamewon.replace("%WINNER%", winner.getName());
				gamewon = gamewon.replace("%WINS%", "" + classmanager.getMatchManager().getCurrentWins(winner));
				gamewon = gamewon.replace("%GAMES%", "" + games);
				sendMessage(gamewon);
				setEnd(winner, loser);
				classmanager.getMatchManager().remove(player, player2);
			}else{
				String won = classmanager.getLanguageString("messages.matchwon", null);
				won = won.replace("%WINNER%", winner.getName());
				won = won.replace("%HEARTS%", s);
				won = won.replace("%HEARTSYMBOL%", "❤");
				won = won.replace("%GAMES%", "" + games);
				sendMessage(won);
				games++;
				time = 215;
				kit.set(winner);
				kit.set(loser);
				player.updateInventory();
				player2.updateInventory();
				Map m = map;
				m.clearDrops(winner);
				m.clearDrops(loser);
				m.regenerate();
				move = false;
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if(map.isRegen()){
							sendActionBar(classmanager.getLanguageString("messages.resetarena", null));
							return;
						}
						player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
						player2.playSound(player2.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
						cd--;
						if(cd >= 1){
							if(titlecd){
								sendTitles(getTitle(cd), "");
							}
							sendMessage(classmanager.getLanguageString("messages.countdown", null).replace("%TIME%", "" + cd));
						}else if(cd == 0){
							cancel();
							sendMessage(classmanager.getLanguageString("messages.countdowngo", null).replace("%TIME%", "" + cd));
							sendTitles("§2Go!", "");
							move = true;
							stopcd = false;
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
							player2.playSound(player2.getLocation(), Sound.LEVEL_UP, 1F, 1F);
							startMiddleTimer();
						}
					}
				}.runTaskTimer(classmanager.getInstance(), 20, 20);
			}
			
		}
		
	}
	public void setEnd(final Player winner, final Player loser){
		
		if(this.shootfw){
		classmanager.getCookieAPI().SpawnFW(winner);
		classmanager.getCookieAPI().SpawnFW(winner);
		classmanager.getCookieAPI().SpawnFW(winner);
		classmanager.getCookieAPI().SpawnFW(loser);
		classmanager.getCookieAPI().SpawnFW(loser);
		classmanager.getCookieAPI().SpawnFW(loser);
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				try{
				classmanager.getMatchManager().remove(winner, loser);
				classmanager.getMatchManager().setLobby(winner);
				classmanager.getMatchManager().setLobby(loser);
				classmanager.getBoardManager().updateScoreboard(winner);
				classmanager.getBoardManager().updateScoreboard(loser);
				sendMessage(classmanager.getLanguageString("messages.sendlobbyaftergame", null));
				cancel();
				}catch(Exception e){
					
				}
			}
		}.runTaskLater(classmanager.getInstance(), 5*20);
		
	}
	public void startMiddleTimer(){
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				time--;
				if(stopcd){
						cancel();
				}
			if(time == 30){			
				sendTitles("", classmanager.getLanguageString("messages.fightends", player).replace("%TIME%", "" + time));
				sendMessage(classmanager.getLanguageString("messages.gettothemiddle", null));
			}else if(time == 20){
				sendTitles("", classmanager.getLanguageString("messages.fightends", player).replace("%TIME%", "" + time));
			}else if(time == 10){
				sendTitles("", classmanager.getLanguageString("messages.fightends", player).replace("%TIME%", "" + time));
			}else if(time == 5){
				sendTitles("", classmanager.getLanguageString("messages.fightends", player).replace("%TIME%", "" + time));
			}else if(time == 4){
				sendTitles("", classmanager.getLanguageString("messages.fightends", player).replace("%TIME%", "" + time));
			}else if(time == 3){
				sendTitles("", classmanager.getLanguageString("messages.fightends", player).replace("%TIME%", "" + time));
				sendMessage(classmanager.getLanguageString("messages.gettothemiddle", null));
			}else if(time == 2){
				sendTitles("", classmanager.getLanguageString("messages.fightends", player).replace("%TIME%", "" + time));
			}else if(time == 1){
				sendTitles("", classmanager.getLanguageString("messages.fightends", player).replace("%TIME%", "" + time));
			}else if(time == 0){
				move = true;
				sendTitles(classmanager.getLanguageString("messages.timeisover", null), "");
				Location middle = classmanager.getCookieAPI().getLocation("arena.yml", map.getName() + ".middle", "1vs1");
				if(middle.distance(player.getLocation()) <= middle.distance(player2.getLocation())){
					nextRound(player, player2);
				}else{
					nextRound(player2, player);
				}
			}
				
			}
		}.runTaskTimer(classmanager.getInstance(), 20, 20);
		
	}
	public void start(){
		if(classmanager.getMatchManager().getVector(player) == null)return;
		kit.set(player);
		kit.set(player2);
		player.updateInventory();
		player2.updateInventory();
		games++;
		player.setHealth(20.0);
		player2.setHealth(20.0);
		player.setFoodLevel(20);
		player2.setFoodLevel(20);
		move = false;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(map.isRegen()){
					sendActionBar(classmanager.getLanguageString("messages.resetarena", null));
					return;
				}
				player.setFireTicks(0);
				player2.setFireTicks(0);
				player.setHealth(20.0);
				player2.setHealth(20.0);
				cd--;
				if(cd >= 1){
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					player2.playSound(player2.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					if(titlecd){
						sendTitles(getTitle(cd), "");
					}
					sendMessage(classmanager.getLanguageString("messages.countdown", null).replace("%TIME%", "" + cd));
				}else if(cd == 0){
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
					player2.playSound(player2.getLocation(), Sound.LEVEL_UP, 1F, 1F);
					cancel();
					sendMessage(classmanager.getLanguageString("messages.countdowngo", null).replace("%TIME%", "" + cd));
					sendTitles("§2Go!", "");
					move = true;
					startMiddleTimer();
				}
				
			}
		}.runTaskTimer(classmanager.getInstance(), 20, 20);
		
	}

	/* For easier use */
	
	private String getTitle(int cd){
		switch (cd) {
		case 1:
			return "§21";

		case 2:
			return "§a2";
			
		case 3:
			return "§e3";
			
		case 4:
			return "§c4";
			
		case 5:
			return "§45";
			
		default:
			return "§cERROR";
		}
	}
	private void sendMessage(String msg){
		player.sendMessage(msg);
		player2.sendMessage(msg);
	}
	@SuppressWarnings("deprecation")
	private void sendTitles(String title, String subtitle){
		player.sendTitle(title, subtitle);
		player2.sendTitle(title, subtitle);
	}
	private void sendActionBar(String msg){
		classmanager.getCookieAPI().sendActionbar(msg, player);
		classmanager.getCookieAPI().sendActionbar(msg, player2);
	}
	
}
