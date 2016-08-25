package de.shorty.onevone.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.kit.Kit;
import de.shorty.onevone.manager.Map;
import de.shorty.onevone.manager.Match;

public class PlayerQuitListener implements Listener {

	OneVOne onevone;

	public PlayerQuitListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@EventHandler
	public void nq(PlayerQuitEvent event) {

		Player player = event.getPlayer();

		if (onevone.matchmanager.isIngame(player)) {
			Player opponent = onevone.matchmanager.getOpponnent(player);

			onevone.stats.addKill(opponent);
			onevone.stats.addWin(opponent);
			onevone.stats.addDeath(player);
			Map map = onevone.matchmanager.getMap(player);
			map.regenerate();
			map.remove();
			Match match = onevone.matchmanager.getMatch(opponent);
			match.setEnd(opponent, player);

		}
		player.teleport(onevone.api.getLocation("locations.yml", "Location.spawn", "1vs1"));
		String currentkit = String.valueOf(onevone.stats.get(player.getName(), "Name", "CurrentKit", "c1vs1_Users"));
		for (String kitname : onevone.kitsettings.getKits(player)) {
			Kit kit;
			if (onevone.kitmanager.isAvailable(kitname)) {
				
				kit = new Kit(kitname, false);
				kit.upload();
				if(kit.getName().equalsIgnoreCase(currentkit)){
					onevone.stats.setCurrentKit(player, kit);
				}

			} else {
				Bukkit.getConsoleSender()
						.sendMessage(onevone.getPrefix() + "§cThis kit isn't fetched, so it can't be uploaded!");
			}
		}
		onevone.matchmaking.removePlayer(player);
		onevone.stats.uploadPlayer(player);

		event.setQuitMessage(null);
	}

}
