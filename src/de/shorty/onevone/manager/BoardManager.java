package de.shorty.onevone.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import de.shorty.onevone.OneVOne;

public class BoardManager {

	OneVOne onevone;

	public BoardManager(OneVOne onevone) {
		this.onevone = onevone;
	}

	public void setScoreboard(Player player) {

		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard s = sm.getNewScoreboard();
		s.registerNewObjective("board", "dummy");
		Objective o = s.getObjective("board");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(onevone.getScoreboardString("Scoreboard.header", player));
		registerTeams(s);
		registerPlayers(s);
		player.setScoreboard(s);

	}

	public void updateScoreboard(Player player) {

		setScoreboard(player);
		Scoreboard s = player.getScoreboard();
		Objective o = s.getObjective("board");
		onevone.requestmanager.add(player);
		if (onevone.matchmaking.contains(player)) {

			if (onevone.isGerman()) {
				o.getScore(onevone.getScoreboardString("Scoreboard.kitdisplay", player).replace("%KIT%", onevone.stats.getCurrentKit(player).getName())).setScore(11);
				o.getScore("    ").setScore(10);
				o.getScore("§8§m----------------- ").setScore(9);
				o.getScore("   ").setScore(8);
				o.getScore("§7➜ §6Suche Gegner...").setScore(7);
				o.getScore("  ").setScore(6);
				if (onevone.stats.getSearchType(player).equalsIgnoreCase("own")) {
					o.getScore("§7» §3Kit: §7eigenes").setScore(5);
				} else {
					o.getScore("§7» §3Kit: §7egal").setScore(5);
				}
				o.getScore("§7» §cK/D: §7" + onevone.stats.getKD(player) + "+").setScore(4);
				o.getScore("§7» §2Bewertung: §7" + onevone.stats.getBewertung(player) + "+").setScore(3);
				o.getScore(" ").setScore(2);
				o.getScore("§8§m-----------------").setScore(1);
				o.getScore("").setScore(0);
			} else {
				o.getScore(onevone.getScoreboardString("Scoreboard.kitdisplay", player).replace("%KIT%", onevone.stats.getCurrentKit(player).getName())).setScore(11);
				o.getScore("    ").setScore(10);
				o.getScore("§8§m----------------- ").setScore(9);
				o.getScore("   ").setScore(8);
				o.getScore("§7➜ §6Search opponent...").setScore(7);
				o.getScore("  ").setScore(6);
				if (onevone.stats.getSearchType(player).equalsIgnoreCase("own")) {
					o.getScore("§7» §3Kit: §7own").setScore(5);
				} else {
					o.getScore("§7» §3Kit: §7doesn't matter").setScore(5);
				}
				o.getScore("§7» §cK/D: §7" + onevone.stats.getKD(player) + "+").setScore(4);
				o.getScore("§7» §2Bewertung: §7" + onevone.stats.getBewertung(player) + "+").setScore(3);
				o.getScore(" ").setScore(2);
				o.getScore("§8§m-----------------").setScore(1);
				o.getScore("").setScore(0);
			}

		} else if (onevone.requestmanager.Requests(player).size() != 0
				&& onevone.requestmanager.Requested(player).size() != 0) {

			o.getScore(onevone.getScoreboardString("Scoreboard.kitdisplay", player).replace("%KIT%", onevone.stats.getCurrentKit(player).getName())).setScore(17);
			o.getScore("    ").setScore(16);
			o.getScore("§8§m-----------------  ").setScore(15);
			if(onevone.isGerman()){
				o.getScore("§7➜ §9Anfragen §7").setScore(14);
			}else{
				o.getScore("§7➜ §9Requests §7").setScore(14);
			}
			o.getScore("   ").setScore(13);
			if (onevone.requestmanager.Requests(player).size() == 1) {
				o.getScore("§8➥ §2" + onevone.requestmanager.Requests(player).get(0)).setScore(12);
			}
			if (onevone.requestmanager.Requests(player).size() == 2) {
				o.getScore("§8➥ §2" + onevone.requestmanager.Requests(player).get(1)).setScore(11);
			}
			if (onevone.requestmanager.Requests(player).size() == 3) {
				o.getScore("§8➥ §2" + onevone.requestmanager.Requests(player).get(2)).setScore(10);
			}
			o.getScore("  ").setScore(9);
			o.getScore("§8§m----------------- ").setScore(8);
			if(onevone.isGerman()){
				o.getScore("§7➜ §9Angefragt §7").setScore(7);
			}else{
				o.getScore("§7➜ §9Requested §7").setScore(7);
			}
			o.getScore(" ").setScore(6);
			if (onevone.requestmanager.Requested(player).size() == 1) {
				o.getScore("§8➥ §c" + onevone.requestmanager.Requested(player).get(0)).setScore(5);
			}
			if (onevone.requestmanager.Requested(player).size() == 2) {
				o.getScore("§8➥ §c" + onevone.requestmanager.Requested(player).get(1)).setScore(4);
			}
			if (onevone.requestmanager.Requested(player).size() == 3) {
				o.getScore("§8➥ §c" + onevone.requestmanager.Requested(player).get(2)).setScore(3);
			}
			o.getScore("").setScore(2);
			o.getScore("§8§m-----------------").setScore(1);
			o.getScore("     ").setScore(0);
		} else if (onevone.requestmanager.Requests(player).size() != 0 && onevone.requestmanager.Requested(player).size() == 0) {

			o.getScore(onevone.getScoreboardString("Scoreboard.kitdisplay", player).replace("%KIT%", onevone.stats.getCurrentKit(player).getName())).setScore(10);
			o.getScore("    ").setScore(9);
			o.getScore("§8§m----------------- ").setScore(8);
			if(onevone.isGerman()){
				o.getScore("§7➜ §9Anfragen").setScore(7);
			}else{
				o.getScore("§7➜ §9Requests").setScore(7);
			}
			o.getScore("   ").setScore(6);
			if (onevone.requestmanager.Requests(player).size() == 1) {
				o.getScore("§8➥ §2" + onevone.requestmanager.Requests(player).get(0)).setScore(5);
			}
			if (onevone.requestmanager.Requests(player).size() == 2) {
				o.getScore("§8➥ §2" + onevone.requestmanager.Requests(player).get(1)).setScore(4);
			}
			if (onevone.requestmanager.Requests(player).size() == 3) {
				o.getScore("§8➥ §2" + onevone.requestmanager.Requests(player).get(2)).setScore(3);
			}
			o.getScore("  ").setScore(2);
			o.getScore("§8§m-----------------").setScore(1);
			o.getScore("").setScore(0);

		} else if (onevone.requestmanager.Requested(player).size() != 0 && onevone.requestmanager.Requests(player).size() == 0) {

			o.getScore(onevone.getScoreboardString("Scoreboard.kitdisplay", player).replace("%KIT%", onevone.stats.getCurrentKit(player).getName())).setScore(11);
			o.getScore("    ").setScore(10);
			o.getScore("§8§m----------------- ").setScore(8);
			if(onevone.isGerman()){
				o.getScore("§7➜ §9Angefragt").setScore(7);
			}else{
				o.getScore("§7➜ §9Requested").setScore(7);
			}
			o.getScore(" ").setScore(6);
			if (onevone.requestmanager.Requested(player).size() == 1) {
				o.getScore("§8➥ §c" + onevone.requestmanager.Requested(player).get(0)).setScore(5);
			}
			if (onevone.requestmanager.Requested(player).size() == 2) {
				o.getScore("§8➥ §c" + onevone.requestmanager.Requested(player).get(1)).setScore(4);
			}
			if (onevone.requestmanager.Requested(player).size() == 3) {
				o.getScore("§8➥ §c" + onevone.requestmanager.Requested(player).get(2)).setScore(3);
			}
			o.getScore("").setScore(2);
			o.getScore("§8§m-----------------").setScore(1);
			o.getScore("     ").setScore(0);

		} else {

		}

		player.setScoreboard(s);
		
	}

	public void updateIngame(Player player, boolean complete) {
		if (complete) {
			setScoreboard(player);
			Scoreboard s = player.getScoreboard();
			Objective o = s.getObjective("board");
			o.getScore("§3Kit: §c" + onevone.matchmanager.getMatch(player).getKit()).setScore(11);
			o.getScore("").setScore(10);
			o.getScore("§8§m----------------------- ").setScore(9);
			o.getScore(" ").setScore(8);
			o.getScore("§2" + player.getName()).setScore(7);
			o.getScore("  ").setScore(6);
			o.getScore("§8vs").setScore(5);
			o.getScore("   ").setScore(4);
			o.getScore("§4" + onevone.matchmanager.getOpponnent(player).getName()).setScore(3);
			o.getScore("    ").setScore(2);
			o.getScore("§8§m-----------------------").setScore(1);
			o.getScore("     ").setScore(0);
		} else {
			Scoreboard s = player.getScoreboard();
			Objective o = s.getObjective("board");
			o.setDisplayName("§91vs1 §7| " + onevone.matchmanager.getMatch(player).getTL());
		}
	}

	public void registerTeams(Scoreboard s) {

		s.registerNewTeam("00Admin");
		s.registerNewTeam("01CoAdmin");
		s.registerNewTeam("02Developer");
		s.registerNewTeam("03Moderator");
		s.registerNewTeam("04Builder");
		s.registerNewTeam("05Youtuber");
		s.registerNewTeam("06Premium");
		s.registerNewTeam("07Spieler");
		s.registerNewTeam("08Ingame");
		s.getTeam("00Admin").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.Admin", null));
		s.getTeam("01CoAdmin").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.CoAdmin", null));
		s.getTeam("02Developer").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.Developer", null));
		s.getTeam("03Moderator").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.Moderator", null));
		s.getTeam("04Builder").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.Builder", null));
		s.getTeam("05Youtuber").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.Youtuber", null));
		s.getTeam("06Premium").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.Premium", null));
		s.getTeam("07Spieler").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.Player", null));
		s.getTeam("08Ingame").setPrefix(onevone.getScoreboardString("Scoreboard.prefix.Ingame", null));
		
	}

	@SuppressWarnings("deprecation")
	public void registerPlayers(Scoreboard s) {

		for (Player player : Bukkit.getOnlinePlayers()) {

			if(onevone.matchmanager.isIngame(player)){
				s.getTeam("08Ingame").addPlayer(player);
			}else if(player.hasPermission("1vs1.Admin")){
				s.getTeam("00Admin").addPlayer(player);
			}else if(player.hasPermission("1vs1.CoAdmin")){
				s.getTeam("01CoAdmin").addPlayer(player);
			}else if(player.hasPermission("1vs1.Developer")){
				s.getTeam("02Developer").addPlayer(player);
			}else if(player.hasPermission("1vs1.Moderator")){
				s.getTeam("03Moderator").addPlayer(player);
			}else if(player.hasPermission("1vs1.Builder")){
				s.getTeam("04Builder").addPlayer(player);
			}else if(player.hasPermission("1vs1.Youtuber")){
				s.getTeam("05Youtuber").addPlayer(player);
			}else if(player.hasPermission("1vs1.Premium")){
				s.getTeam("06Premium").addPlayer(player);
			}else{
				s.getTeam("07Spieler").addPlayer(player);
			}

		}

	}
}
