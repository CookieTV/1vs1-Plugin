package de.shorty.onevone.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.manager.Map;

public class BlockPlaceListener implements Listener{

	OneVOne onevone;
	
	public BlockPlaceListener(OneVOne onevone) {
		this.onevone = onevone;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		
		if(onevone.matchmanager.isIngame(player)){
			if(onevone.matchmanager.getMatch(player).getRealKit().containsSetting("InstantTnT")){
				if(event.getBlock().getType() == Material.TNT){
					
					event.getBlock().setType(Material.AIR);
					TNTPrimed tnt = (TNTPrimed) event.getBlock().getWorld().spawn(event.getBlock().getLocation(), TNTPrimed.class);
					tnt.setFuseTicks(40);
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
					
					ItemStack item = player.getItemInHand();
					if(item.getAmount() == 1){
						player.setItemInHand(new ItemStack(Material.AIR));
					}else{
						item.setAmount(item.getAmount() - 1);
					}
					
				}
			}
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

	@EventHandler
	public void onBlockto(BlockFromToEvent event){
		
		if(event.getBlock().getType() == Material.WATER || event.getBlock().getType() == Material.STATIONARY_WATER || event.getBlock().getType() == Material.LAVA || event.getBlock().getType() == Material.STATIONARY_LAVA){
		for(Player player : onevone.matchmanager.opponent.keySet()){
				
			Location location = event.getBlock().getLocation();
			
			Map map = onevone.matchmanager.getMap(player);
			if(map.isRegen()){
				if(map.containsLocation(location)){
					event.setCancelled(true);
				}
			}
			
		}
		}
	}
	
}
