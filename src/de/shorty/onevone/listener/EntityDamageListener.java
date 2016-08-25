package de.shorty.onevone.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.kit.Kit;

public class EntityDamageListener implements Listener{

	OneVOne onevone;
	
	public EntityDamageListener(OneVOne onevone) {
		this.onevone = onevone;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		
		if(event.getEntity() instanceof Player){
			
			Player player = (Player) event.getEntity();
			
			if(onevone.matchmanager.isIngame(player)){
				if(event.getCause().equals(DamageCause.FALL)){
					Kit kit = onevone.matchmanager.getMatch(player).getRealKit();
					event.setCancelled(getOpposite(kit.containsSetting("Fallschaden")));
				}
			}else{
				event.setCancelled(true);
			}
			
		}else if(event.getEntity() instanceof Player){
			
		}
		
	}
	
	private boolean getOpposite(boolean bool){
		if(bool)return false;
		else return true;
	}

}
