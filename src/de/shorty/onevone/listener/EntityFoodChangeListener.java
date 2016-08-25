package de.shorty.onevone.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.shorty.onevone.OneVOne;

public class EntityFoodChangeListener implements Listener{

	OneVOne onevone;
	
	public EntityFoodChangeListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event){
		
		Player player = (Player) event.getEntity();
		
		if(onevone.matchmanager.isIngame(player)){
			event.setCancelled(getOpposite(onevone.matchmanager.getMatch(player).getRealKit().containsSetting("Hunger")));
		}else{
			event.setCancelled(true);
		}
		
	}

	private boolean getOpposite(boolean bool){
		if(bool)return false;
		else return true;
	}
	
}
