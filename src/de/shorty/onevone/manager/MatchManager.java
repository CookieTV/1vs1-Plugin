package de.shorty.onevone.manager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.shorty.onevone.OneVOne;

public class MatchManager {

	OneVOne onevone;

	private HashMap<Player, Match> Match = new HashMap<>();

	public HashMap<Player, Player> opponent = new HashMap<>();
	private HashMap<Player, Map> playedmap = new HashMap<>();
	private HashMap<Player, Integer> currentwins = new HashMap<>();

	private HashMap<Player, Vector> startvector = new HashMap<>();

	public MatchManager(OneVOne onevone) {
		this.onevone = onevone;
	}

	public void set(Player player, Player player2) {
		opponent.put(player, player2);
		opponent.put(player2, player);
	}

	public void remove(Player player, Player player2) {
		if (isIngame(player)) {
			opponent.remove(player);
		}
		if (isIngame(player2)) {
			opponent.remove(player2);
		}
	}

	public boolean isIngame(Player player) {
		return opponent.containsKey(player);
	}

	public Player getOpponnent(Player player) {
		return opponent.get(player);
	}

	/* Make players ready for battles */

	public void clearPlayer(Player player) {
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		player.setMaximumNoDamageTicks(20);
		player.getInventory().setArmorContents(null);
		player.getInventory().clear();
		player.updateInventory();
		for (PotionEffect e : player.getActivePotionEffects()) {
			player.removePotionEffect(e.getType());
		}
		player.setGameMode(GameMode.SURVIVAL);
	}

	public void setLobby(final Player player) {
		clearPlayer(player);
		player.setFoodLevel(20);
		player.setHealth(20.0);
		for (PotionEffect e : player.getActivePotionEffects()) {
			player.removePotionEffect(e.getType());
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				player.getInventory().setItem(0, onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1,
						"§8» §3Challenger", null));
				player.getInventory().setItem(8,
						onevone.api.CreateItemwithMaterial(Material.COOKIE, 0, 1, "§8» §3Lobby", null));
				player.updateInventory();
				player.setGameMode(GameMode.ADVENTURE);
				try{
				player.teleport(onevone.api.getLocation("locations.yml", "Location.spawn", "1vs1"));
				}catch(Exception e){
					Bukkit.getConsoleSender().sendMessage(onevone.getPrefix() + "§cCould not teleport player to spawn location!");
				}
				try{
				onevone.api.sendTabTitle(player, onevone.getLanguageString("messages.tabheader", player),
						onevone.getLanguageString("messages.tabfooter", player));
				}catch(Exception e){
					Bukkit.getConsoleSender().sendMessage(onevone.getPrefix() + "§cCould not set Players Tablist");
					e.printStackTrace();
				}
			}
		}.runTaskLater(onevone.instance, 5);
		try{
			if(onevone.isGerman()){
				String[] text = { "§9§l1vs1", 
					"§7§mRang§8: §e#",
					"§7Kills§8: §e" + onevone.stats.getK(player) + " §7Tode§8: §e"
							+ onevone.stats.getD(player),
					"§7Spiele§8: §e" + onevone.stats.getG(player) + " §7Wins§8: §e"
							+ onevone.stats.getW(player),
					"§7K/D§8: §e" + onevone.api.getKD(onevone.stats.getK(player), onevone.stats.getD(player), 4) + " §7W/L§8: §e"
							+ onevone.api.getPercentof(onevone.stats.getG(player), onevone.stats.getW(player), 4),
					"§7Punkte§8: §e" + onevone.stats.getP(player)};
				onevone.api.setHolo(player, text, onevone.api.getLocation("locations.yml", "Location.hologram", "1vs1"), true);
			}else{
				String[] text = { "§9§l1vs1", 
						"§7§mRang§8: §e#",
						"§7Kills§8: §e" + onevone.stats.getK(player) + " §7Deaths§8: §e"
								+ onevone.stats.getD(player),
						"§7Games§8: §e" + onevone.stats.getG(player) + " §7Wins§8: §e"
								+ onevone.stats.getW(player),
						"§7K/D§8: §e" + onevone.api.getKD(onevone.stats.getK(player), onevone.stats.getD(player), 4) + " §7W/L§8: §e"
								+ onevone.api.getPercentof(onevone.stats.getG(player), onevone.stats.getW(player), 5),
						"§7Points§8: §e" + onevone.stats.getP(player)};
					onevone.api.setHolo(player, text, onevone.api.getLocation("locations.yml", "Location.hologram", "1vs1"), true);
			}
		}catch(Exception e){
			Bukkit.getConsoleSender().sendMessage(onevone.getPrefix() + "§cCould not show players hologram!");
			e.printStackTrace();
		}

	}

	/* Easier to handle matches */

	public Map getMap(Player player) {
		return playedmap.get(player);
	}

	public void setMap(Player player, Map map) {
		playedmap.put(player, map);
	}

	public int getCurrentWins(Player player) {
		return currentwins.get(player);
	}

	public void setCurrentWin(Player player, int wins) {
		currentwins.put(player, wins);
	}

	public Vector getVector(Player player) {
		return startvector.get(player);
	}

	public void setVector(Player player, Vector vector) {
		startvector.put(player, vector);
	}

	public void setMatch(Player player, Match match) {
		Match.put(player, match);
	}

	public Match getMatch(Player player) {
		return Match.get(player);
	}

}
