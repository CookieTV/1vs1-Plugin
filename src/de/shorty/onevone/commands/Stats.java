package de.shorty.onevone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;

public class Stats implements CommandExecutor{

	OneVOne onevone;
	
	public Stats(OneVOne onevone) {
		this.onevone = onevone;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player){
			
			if(args.length == 0){
				Player player = (Player) sender;
				int kills = onevone.stats.getK(player);
				int deaths = onevone.stats.getD(player);
				int games = onevone.stats.getG(player);
				int points = onevone.stats.getP(player);
				int wins = onevone.stats.getW(player);
				String kd = onevone.api.getKD(kills, deaths, 4);
				String wl = onevone.api.getPercentof(games, wins, 4);
				int loses = games - wins;
				
				if(onevone.isGerman()){
					sender.sendMessage("§8§m---------------------------------");
					sender.sendMessage("            §3Stats §7| §6" + player.getName());
					sender.sendMessage("");
					//sender.sendMessage("§7Von §b" + onevone.stats.getTotalPlayers() + " §7Spielern bist du auf Platz §e§m#");
					sender.sendMessage("§7Du hast insgesamt §b" + games + " §7Spiele gespielt,\n §7davon §b" + wins + " §7gewonnen und §b" + loses + " §7verloren!");
					sender.sendMessage("§7Du hast momentan §b" + points + " §7Punkte.");
					sender.sendMessage("§7Du hast §b" + kills + " §7Kills und §b" + deaths + " §7Tode!" );
					sender.sendMessage("§7Deine K/D§8: §b" + kd);
					sender.sendMessage("§7Deine W/L§8: §b" + wl);
					sender.sendMessage("");
					sender.sendMessage("§8§m---------------------------------");
				}else{
					sender.sendMessage("§8§m---------------------------------");
					sender.sendMessage("            §3Stats §7| §b" + player.getName());
					sender.sendMessage("");
					//sender.sendMessage("§7From §b" + onevone.stats.getTotalPlayers() + " §7Total playersb §e§m#");
					sender.sendMessage("§7You played §b" + games + " §7Games, won §b" + wins + " §7of them and lost §b" + loses + "§7!");
					sender.sendMessage("§7You got §b" + points + " §7Points.");
					sender.sendMessage("§7You killed §b" + kills + " §7people and died §b" + deaths + " §7times!" );
					sender.sendMessage("§7Your K/D§8: §b" + kd);
					sender.sendMessage("§7Your W/L§8: §b" + wl);
					sender.sendMessage("");
					sender.sendMessage("§8§m---------------------------------");
				}
				
			}else{
				if(onevone.isGerman()){
					sender.sendMessage(onevone.getPrefix() + "§cDieses Feature ist momentan noch nicht vorhanden!");
				}else{
					sender.sendMessage(onevone.getPrefix() + "§cThis feature is not availaible yet!");
				}
			}
		
		}
		return false;
	}

}
