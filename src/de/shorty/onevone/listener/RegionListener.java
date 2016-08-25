package de.shorty.onevone.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.kit.Kit;

public class RegionListener implements Listener{

	OneVOne onevone;
	
	public RegionListener(OneVOne onevone) {
		this.onevone = onevone;
	}
	
	@EventHandler
	public void onRegionEnter(RegionEnterEvent event){
		
		final Player player = event.getPlayer();
		if(event.getRegion().getId().equalsIgnoreCase("kitzone")){
			final Kit kit = onevone.stats.getCurrentKit(player);
			if(kit.getCreator().equals(player.getUniqueId().toString())){
				onevone.kitmanager.setKitEditing(player);
				onevone.matchmanager.clearPlayer(player);
				player.setGameMode(GameMode.CREATIVE);
				player.setAllowFlight(false);
				player.setFlying(false);
				new BukkitRunnable() {
					
					@Override
					public void run() {
						kit.set(player);
					}
				}.runTaskLater(onevone, 5);
			}else{
				Vector v = new Vector(player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ()).normalize().multiply(-1.5D).setY(0.5D);
				if(onevone.isGerman()){
					player.sendMessage(onevone.getPrefix() + "§cDies ist keins deiner Kits!");
				}else{
					player.sendMessage(onevone.getPrefix() + "§cYou do not own this kit!");
				}
				player.setVelocity(v);
			}
		}
		
	}
	
	@EventHandler
	public void onRegionLeave(RegionLeaveEvent event){
		
		Player player = event.getPlayer();
		if(event.getRegion().getId().equalsIgnoreCase("kitzone")){
			Kit kit = onevone.stats.getCurrentKit(player);
			if(kit.getCreator().equals(player.getUniqueId().toString())){
				onevone.kitmanager.removeFromEditing(player);
				player.setGameMode(GameMode.ADVENTURE);
				kit.save(player);
				onevone.matchmanager.clearPlayer(player);
				player.setFoodLevel(20);
				player.setHealth(20.0);
				player.getInventory().setItem(0, onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1,
						"§8» §3Challenger", null));
				player.getInventory().setItem(8,
						onevone.api.CreateItemwithMaterial(Material.COOKIE, 0, 1, "§8» §3Lobby", null));
				player.updateInventory();
			}else{
			}
		}
		
	}

}
