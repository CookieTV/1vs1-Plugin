package de.shorty.onevone.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.shorty.onevone.OneVOne;

public class ServerListener implements Listener{

	OneVOne onevone;
	
	public ServerListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@EventHandler
	public void onCreature(CreatureSpawnEvent event) {
		if(event.getSpawnReason().equals(SpawnReason.NATURAL)){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onBurn(BlockBurnEvent event) {
		event.setCancelled(true);
	}
	@EventHandler
	public void onWeather(WeatherChangeEvent event) {
		event.setCancelled(true);
	}
	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		event.blockList().clear();
	}
	@EventHandler
	public void onSpread(BlockIgniteEvent event) {
		event.setCancelled(true);
	}
	@EventHandler
	public void onCraft(CraftItemEvent event) {
		event.setCancelled(true);
	}
	@EventHandler
	public void onArch(PlayerAchievementAwardedEvent event) {
		event.setCancelled(true);
	}
	@EventHandler
	public void ontoggle(PlayerToggleFlightEvent event) {
		if(onevone.build.contains(event.getPlayer())){
			event.setCancelled(false);
		}else if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE) && onevone.kitmanager.isEditing(event.getPlayer())){
			event.setCancelled(true);
		}else{
			event.setCancelled(true);
		}
		
	}
}
