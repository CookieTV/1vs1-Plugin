package de.shorty.onevone.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.manager.Match;

public class EntityDamageByEntityListener implements Listener{

	OneVOne onevone;
	
	public EntityDamageByEntityListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@EventHandler 
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		
		if(event.getDamager() instanceof Player){
		
			Player damager = (Player) event.getDamager();
			
			if(onevone.matchmanager.isIngame(damager)){
			
				event.setCancelled(onevone.matchmanager.getMatch(damager).canDamage());
				
			}else{
				event.setCancelled(true);
				if(event.getEntity() instanceof Zombie){
					event.setCancelled(true);
					if(damager.getItemInHand().getType() == Material.DIAMOND_SWORD){
						if(onevone.matchmaking.contains(damager)){
							onevone.matchmaking.removePlayer(damager);
						}else{
							onevone.matchmaking.addPlayer(damager, onevone.stats.getSearchType(damager));
						}
					}
				}else if(event.getEntity() instanceof Sheep){
					event.setCancelled(true);
					onevone.inventorys.getKitSettingsInventory(damager);
				}else if(event.getEntity() instanceof Player){
					if(damager.getItemInHand().getType() == Material.DIAMOND_SWORD){
						Player player = (Player) event.getEntity();
						if(onevone.requestmanager.contains(player) == false ){
							onevone.requestmanager.add(player);
						}
						if(onevone.requestmanager.contains(damager) == false){
							onevone.requestmanager.add(damager);
						}
						if(onevone.requestmanager.Requests(damager).contains(player.getName()) && onevone.requestmanager.Requested(player).contains(damager.getName())){
							
							Match m = new Match(player, damager, onevone.stats.getCurrentKit(player));
							
							m.setup();
							m.start();
							
						}else{
							onevone.requestmanager.sendRequest(damager, player);
							onevone.boardmanager.updateScoreboard(player);
							onevone.boardmanager.updateScoreboard(damager);
						
					}
				}
			}
			
			
		}
		}
	}
}
