package de.shorty.onevone.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.kit.Kit;

public class PlayerJoinListener implements Listener {

	OneVOne onevone;

	public PlayerJoinListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();

		onevone.requestmanager.add(player);
		
		onevone.boardmanager.setScoreboard(player);
		onevone.boardmanager.updateScoreboard(player);
		
		onevone.stats.createPlayer(player);
		onevone.stats.loadPlayer(player);

		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		String currentkit = String.valueOf(onevone.stats.get(player.getName(), "Name", "CurrentKit", "c1vs1_Users"));
		try {
			for (String s : onevone.kitsettings.getKits(player)) {
				Kit kit = new Kit(s, true);
				kit.fetchData();
				if(kit.getName().equalsIgnoreCase(currentkit)){
					onevone.stats.setCurrentKit(player, kit);
				}
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(onevone.getPrefix() + "§cCould not fetch Kit data!");
		}

		onevone.matchmanager.setLobby(player);

		event.setJoinMessage(null);
		for(Player target : Bukkit.getOnlinePlayers()){
			onevone.boardmanager.updateScoreboard(target);
		}
	}

}
