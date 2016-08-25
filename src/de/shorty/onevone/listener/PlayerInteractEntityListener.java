package de.shorty.onevone.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.manager.Match;

public class PlayerInteractEntityListener implements Listener{

	OneVOne onevone;
	
	public PlayerInteractEntityListener(OneVOne onevone) {
		this.onevone = onevone;
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event){
		
		Player player = event.getPlayer();
		if(!onevone.matchmanager.isIngame(player)){
			if(event.getRightClicked() instanceof Zombie){
				if(player.getItemInHand().getType() == Material.DIAMOND_SWORD){
					onevone.inventorys.getMatchmakingInventory(player);
				}
			}else if(event.getRightClicked() instanceof Sheep){
				onevone.inventorys.getKitSettingsInventory(player);
			}else if(event.getRightClicked() instanceof Player){
				if(player.getItemInHand().getType() == Material.DIAMOND_SWORD && !(onevone.matchmanager.isIngame(player)) && !(onevone.kitmanager.isEditing(player)) && !(onevone.matchmaking.contains(player))){
					
					Player player2 = (Player) event.getRightClicked();
					
					if(onevone.requestmanager.contains(player) == false ){
						onevone.requestmanager.add(player);
					}
					if(onevone.requestmanager.contains(player2) == false){
						onevone.requestmanager.add(player2);
					}
					if(onevone.requestmanager.Requests(player).contains(player2.getName()) && onevone.requestmanager.Requested(player2).contains(player.getName())){
						
						Match m = new Match(player2, player, onevone.stats.getCurrentKit(player2));
						
						m.setup();
						m.start();
						
					}else{
						onevone.requestmanager.sendRequest(player, player2);
						onevone.boardmanager.updateScoreboard(player);
						onevone.boardmanager.updateScoreboard(player2);
					}
					
				}
			}
		}
	}
}
