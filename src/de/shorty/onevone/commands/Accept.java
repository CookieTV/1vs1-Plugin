package de.shorty.onevone.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.manager.Match;

public class Accept implements CommandExecutor {

	OneVOne onevone;

	public Accept(OneVOne onevone) {
		this.onevone = onevone;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				if(onevone.isGerman()){
					player.sendMessage(onevone.getPrefix() + "§cBitte gebe einen Spielernamen an!");
				}else{
					player.sendMessage(onevone.getPrefix() + "§cPlease enter a playername!");
				}
			} else if (args.length == 1) {

				Player target = Bukkit.getPlayer(args[0]);
				if (target == null){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDer Spieler ist nicht mehr online!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cThat player currently not online!");
					}
				}else if (onevone.requestmanager.Requests(player).contains(target.getName()) == false){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDu hast von diesem Spieler keine Anfrage bekommen!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cYou didnt receive any requests of this player!");
					}
				}else if(onevone.kitmanager.isEditing(target)){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDieser Spieler editiert gerade sein Kit!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cThat player is editing his kit!");
					}
				}else if(onevone.kitmanager.isEditing(player)){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDu editierst gerade ein Kit!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cYou are editing a kit!");
					}
				}else if(onevone.matchmanager.isIngame(target)){
				
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDer Spieler ist bereits Ingame");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cThat Player is alread ingame!");
					}
					
				}else {
				
					Match m = new Match(target, player, onevone.stats.getCurrentKit(target));
					m.setup();
					m.start();
				}

			} else {
				if(onevone.isGerman()){
					player.sendMessage(onevone.getPrefix() + "§8» §7/accept <§bSpieler§7>");
				}else{
					player.sendMessage(onevone.getPrefix() + "§8» §7/accept <§bPlayer§7>");
				}
			}
		}

		return false;
	}

}
