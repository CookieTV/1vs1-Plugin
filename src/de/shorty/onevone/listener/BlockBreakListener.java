package de.shorty.onevone.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.shorty.onevone.OneVOne;

public class BlockBreakListener implements Listener{

	OneVOne onevone;
	
	public BlockBreakListener(OneVOne onevone) {
		this.onevone = onevone;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		
		Player player = event.getPlayer();
		
		if(onevone.matchmanager.isIngame(player)){
			if(event.getBlock().getTypeId() == 95 || event.getBlock().getType() == Material.GLASS){
				event.setCancelled(true);
			}else{
				event.setCancelled((!onevone.matchmanager.getMatch(player).getRealKit().containsSetting("Building")));
			}
		}else if(onevone.build.contains(player)){
			event.setCancelled(false);
		}else{
			event.setCancelled(true);
		}
		
	}

}
