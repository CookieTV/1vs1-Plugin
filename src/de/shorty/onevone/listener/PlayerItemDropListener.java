package de.shorty.onevone.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.shorty.onevone.OneVOne;

public class PlayerItemDropListener implements Listener{

	OneVOne onevone;
	
	public PlayerItemDropListener(OneVOne onevone) {
		this.onevone = onevone;
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		
		Player player = event.getPlayer();
		
		if(onevone.matchmanager.isIngame(player)){
			if(onevone.matchmanager.getMatch(player).getRealKit().containsSetting("SafeSword")){
				if(event.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD || event.getItemDrop().getItemStack().getType() == Material.GOLD_SWORD || event.getItemDrop().getItemStack().getType() == Material.IRON_SWORD || event.getItemDrop().getItemStack().getType() == Material.STONE_SWORD || event.getItemDrop().getItemStack().getType() == Material.WOOD_SWORD){
					event.setCancelled(true);
				}
			}
		}else{
			event.setCancelled(true);
		}
		
	}

}
