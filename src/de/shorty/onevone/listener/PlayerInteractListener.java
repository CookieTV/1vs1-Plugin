package de.shorty.onevone.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import de.shorty.onevone.OneVOne;

public class PlayerInteractListener implements Listener {

	OneVOne onevone;

	public PlayerInteractListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		try {

			if (onevone.matchmanager.isIngame(player)) {
				if (!onevone.matchmanager.getMatch(player).canMove()) {
					event.setCancelled(true);
				} else {
					if(onevone.matchmanager.getMatch(player).getRealKit().containsSetting("Suppenheilung")){
						if(event.getItem().getType() == Material.MUSHROOM_SOUP){
							if(player.getHealth() > 17.5){
								return;
							}else{
								player.setHealth(player.getHealth() + 7.0);
								event.getItem().setType(Material.BOWL);
								event.setCancelled(true);
							}
						}
					}
				}
			} else if (event.getItem().getType() == Material.COOKIE) {

				onevone.api.connect(player, onevone.getConfig().getString("Console.lobbyservername"));

			} else if (onevone.kitmanager.isEditing(player)) {
				if (event.getItem().getType() == Material.LAVA_BUCKET
						|| event.getItem().getType() == Material.WATER_BUCKET
						|| event.getItem().getType() == Material.BOW
						|| event.getItem().getType() == Material.FISHING_ROD || event.getItem().getTypeId() == 373
						|| event.getItem().getType() == Material.GOLDEN_APPLE
						|| event.getItem().getType() == Material.TNT || event.getItem().getTypeId() == 383
						|| event.getItem().getType() == Material.FIREWORK_CHARGE
						|| event.getItem().getType() == Material.FLINT_AND_STEEL
						|| event.getItem().getType() == Material.EGG || event.getItem().getType() == Material.SNOW_BALL
						|| event.getItem().getType() == Material.EXP_BOTTLE
						|| event.getItem().getType() == Material.FLINT_AND_STEEL
						|| event.getItem().getType() == Material.EYE_OF_ENDER) {
					event.setCancelled(true);
				}
			}

		} catch (Exception e) {

		}

	}

}
