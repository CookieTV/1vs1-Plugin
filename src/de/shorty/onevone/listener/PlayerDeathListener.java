package de.shorty.onevone.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.manager.Match;

public class PlayerDeathListener implements Listener{

	OneVOne onevone;
	
	public PlayerDeathListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		event.setDeathMessage(null);
		event.getDrops().clear();
		if(event.getEntity() instanceof Player){
			final Player player = event.getEntity();
			final Player killer = onevone.matchmanager.getOpponnent(player);
			
			onevone.stats.addDeath(player);
			onevone.stats.addKill(killer);
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					player.spigot().respawn();
					Match match = onevone.matchmanager.getMatch(player);
					match.nextRound(killer, player);
					
				}
			}.runTaskLater(onevone, 5);
			
		}
	}
	
}
