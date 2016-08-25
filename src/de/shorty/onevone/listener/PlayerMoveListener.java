package de.shorty.onevone.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.manager.Match;

public class PlayerMoveListener implements Listener{

	OneVOne onevone;
	
	public PlayerMoveListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event){
		
		Player player = event.getPlayer();
		
		if(onevone.matchmanager.isIngame(player)){
			Vector v = onevone.matchmanager.getVector(player);
			Location location = player.getLocation();
			Match match = onevone.matchmanager.getMatch(player);
			if(match.canMove() == false){
			if(location.getBlockX() != v.getBlockX() || location.getBlockZ() != v.getBlockZ()){
				location.setX(v.getBlockX()+0.5);
				location.setZ(v.getBlockZ()+0.5);
				location.setYaw(player.getLocation().getYaw());
				location.setPitch(player.getLocation().getPitch());
				player.teleport(location);
			}
			}
		}else if(onevone.kitmanager.isViewing(player)){
			onevone.kitmanager.removeViewing(player);
			onevone.matchmanager.clearPlayer(player);
			player.setFoodLevel(20);
			player.setHealth(20.0);
			player.getInventory().setItem(0, onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1,
					"§8» §3Challenger", null));
			player.getInventory().setItem(8,
					onevone.api.CreateItemwithMaterial(Material.COOKIE, 0, 1, "§8» §3Lobby", null));
			player.updateInventory();
		}
		
	}
	
}
