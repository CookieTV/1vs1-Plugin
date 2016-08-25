package de.shorty.onevone.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.shorty.onevone.OneVOne;

public class PlayerItemPickupListener implements Listener{

	OneVOne onevone;
	
	public PlayerItemPickupListener(OneVOne onevone) {
		this.onevone = onevone;
	}
	
	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent event){
		
		Player player = event.getPlayer();
		
		if(onevone.matchmanager.isIngame(player)){
			if(onevone.matchmanager.getMatch(player).getRealKit().containsSetting("arrow")){
				if(event.getItem().getItemStack().getType() == Material.ARROW){
					event.setCancelled(true);
				}
			}else{
				event.setCancelled(false);
			}
		}else{
			event.setCancelled(true);
		}
		
	}

}
