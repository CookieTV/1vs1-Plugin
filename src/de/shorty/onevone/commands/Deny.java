package de.shorty.onevone.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;

public class Deny implements CommandExecutor {

	OneVOne onevone;

	public Deny(OneVOne onevone) {
		this.onevone = onevone;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		Player player = (Player) sender;
		if (args.length == 0) {
			if (onevone.isGerman()) {
				player.sendMessage(onevone.getPrefix() + "§cBitte gebe einen Spielernamen an!");
			} else {
				player.sendMessage(onevone.getPrefix() + "§cPlease enter a playername!");
			}
		} else if (args.length == 1) {

			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				if(onevone.isGerman()){
					player.sendMessage(onevone.getPrefix() + "§cDer Spieler ist nicht mehr online!");
				}else{
					player.sendMessage(onevone.getPrefix() + "§cThat player currently not online!");
				}
			} else if (onevone.requestmanager.Requests(player).contains(target.getName()) == false) {
				if(onevone.isGerman()){
					player.sendMessage(onevone.getPrefix() + "§cDu hast von diesem Spieler keine Anfrage bekommen!");
				}else{
					player.sendMessage(onevone.getPrefix() + "§cYou didnt receive any requests of this player!");
				}
			} else {

				onevone.requestmanager.removeRequester(target, player);
				onevone.requestmanager.removeRequest(player, target);
				if(onevone.isGerman()){
					player.sendMessage(onevone.getPrefix() + "§aAnfrage erfolgreich abgelehnt!");
					target.sendMessage(onevone.getPrefix() + "§b" + player.getName() + " §7hat deine Anfrage §cabgelehnt!");
				}else{
					player.sendMessage(onevone.getPrefix() + "§aRequest successfully denied!");
					target.sendMessage(onevone.getPrefix() + "§b" + player.getName() + " §7has declined your request!");
				}
				onevone.boardmanager.updateScoreboard(player);
				onevone.boardmanager.updateScoreboard(target);
			}

		} else {
			if (onevone.isGerman()) {
				player.sendMessage(onevone.getPrefix() + "§8» §7/deny <§bSpieler§7>");
			} else {
				player.sendMessage(onevone.getPrefix() + "§8» §7/deny <§bPlayer§7>");
			}
		}

		return false;
	}

}
