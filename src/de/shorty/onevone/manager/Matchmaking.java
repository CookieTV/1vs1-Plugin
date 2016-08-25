package de.shorty.onevone.manager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;


public class Matchmaking {

	//TODO: Language
	
	public ArrayList<String> searching = new ArrayList<>();
	public ArrayList<String> searchingown = new ArrayList<>();

	OneVOne onevone;

	public Matchmaking(OneVOne onevone) {
		this.onevone = onevone;
	}

	public void addPlayer(Player player, String type) {

		if (contains(player)) {
			if(onevone.isGerman()){
				player.sendMessage(onevone.getPrefix() + "§cDu bist bereits in einer Warteschlange!");
			}else{
				player.sendMessage(onevone.getPrefix() + "§cYou are already waiting for a Match!");
			}
			return;
		}
		onevone.requestmanager.cancel(player);
		if (type.equalsIgnoreCase("own")) {

			if (searching.size() == 0) {

				searchingown.add(player.getName());
				if(onevone.isGerman()){
					player.sendMessage(onevone.getPrefix() + "§aDu bist nun in der Warteschlange!");
				}else{
					player.sendMessage(onevone.getPrefix() + "§cYou have been added to the Queue.");
				}

			} else {
				boolean found = false;
				searchingown.add(player.getName());
				for (String s : searching) {
					Player player2 = Bukkit.getPlayer(s);
					Double neededkd = onevone.stats.getKD(player2);
					if (Double.parseDouble(onevone.api.getKD(onevone.stats.getK(player), onevone.stats.getD(player), 0)) >= neededkd
							&& Double.parseDouble(onevone.api.getKD(onevone.stats.getK(player2), onevone.stats.getD(player2), 0)) >= onevone.stats.getKD(player)) {

						Match match = new Match(player, Bukkit.getPlayer(s), onevone.stats.getCurrentKit(player));

						match.setup();
						match.start();
						searchingown.remove(player.getName());
						searching.remove(s);
						found = true;
						break;
					}

				}
				if(found == false){
					searchingown.add(player.getName());
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§aDu bist nun in der Warteschlange!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§aYou have been added to the Queue.");
					}
				}
			}

		} else {

			if (searching.size() == 0 && searchingown.size() == 0) {

				searchingown.add(player.getName());
				if(onevone.isGerman()){
					player.sendMessage(onevone.getPrefix() + "§aDu bist nun in der Warteschlange!");
				}else{
					player.sendMessage(onevone.getPrefix() + "§aYou have been added to the Queue.");
				}

			} else {
				boolean found = false;
					if (searchingown.size() != 0) {
	
						for (String s : searchingown) {
							Player player2 = Bukkit.getPlayer(s);
							Double neededkd = onevone.stats.getKD(player2);
	
							if (Double.parseDouble(onevone.api.getKD(onevone.stats.getK(player), onevone.stats.getD(player), 0)) >= neededkd
									&& Double.parseDouble(onevone.api.getKD(onevone.stats.getK(player2), onevone.stats.getD(player2), 0)) >= onevone.stats.getKD(player)) {
	
								Match m = new Match(player2, player, onevone.stats.getCurrentKit(player2));
	
								m.setup();
								m.start();
								searchingown.remove(s);
								searching.remove(player.getName());
								found = true;
								break;
							}
						}
					} 
				
					if (found == false) {

						for (String s : searching) {
							Player player2 = Bukkit.getPlayer(s);
							Double neededkd = onevone.stats.getKD(player2);
							
							if (Double.parseDouble(onevone.api.getKD(onevone.stats.getK(player), onevone.stats.getD(player), 0)) >= neededkd
									&& Double.parseDouble(onevone.api.getKD(onevone.stats.getK(player2), onevone.stats.getD(player2), 0)) >= onevone.stats.getKD(player)) {

								Match m = new Match(player, player2, onevone.stats.getCurrentKit(player));

								m.setup();
								m.start();
								searching.remove(player.getName());
								searching.remove(s);
								found = true;
								break;
							}
						}

					}
					
					if(found == false){
						searchingown.add(player.getName());
						if(onevone.isGerman()){
							player.sendMessage(onevone.getPrefix() + "§aDu bist nun in der Warteschlange!");
						}else{
							player.sendMessage(onevone.getPrefix() + "§aYou have been added to the Queue.");
						}
					}
				}

			}
			onevone.boardmanager.updateScoreboard(player);
		}

	

	public void removePlayer(Player player) {
		searching.remove(player.getName());
		searchingown.remove(player.getName());
		if(onevone.isGerman()){
			player.sendMessage(onevone.getPrefix() + "§cDu bist nun nicht mehr in der Warteschlange!");
		}else{
			player.sendMessage(onevone.getPrefix() + "§cYou have been removed from the Queue.");
		}
		onevone.boardmanager.updateScoreboard(player);
	}

	public boolean contains(Player player) {
		if (searching.contains(player.getName()))
			return true;
		if (searchingown.contains(player.getName()))
			return true;
		else
			return false;
	}

}
