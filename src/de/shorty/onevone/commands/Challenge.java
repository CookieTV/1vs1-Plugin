package de.shorty.onevone.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import de.shorty.onevone.OneVOne;

public class Challenge implements CommandExecutor {

	OneVOne onevone;

	public Challenge(OneVOne onevone) {
		this.onevone = onevone;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		if(sender instanceof Player){
			Player player = (Player) sender;
			if(args.length == 0){
				if (onevone.isGerman()) {
					player.sendMessage(onevone.getPrefix() + "§cBitte gebe einen Spielernamen an!");
				} else {
					player.sendMessage(onevone.getPrefix() + "§cPlease enter a playername!");
				}			
				}else if(args.length == 1){
				
				Player target = Bukkit.getPlayer(args[0]);
				if(target == null){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDer Spieler ist nicht mehr online!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cThat player currently not online!");
					}
					return false;
				}
				if(target.getName().equals(player.getName())){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDu kannst dich nicht selber herausfordern!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cYou cannot challenge yourself!");
					}
					return false;
				}
				if(onevone.matchmanager.isIngame(target)){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDer Spieler ist bereits in einem Spiel!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cThat player is already ingame!");
					}
				}else if(onevone.matchmanager.isIngame(player)){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDu bist bereits Ingame!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cYou are already ingame!");
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
				}else if(onevone.requestmanager.Requested(target).contains(player.getName())){
					player.performCommand("accept " + target.getName());
				}else{
				
					onevone.requestmanager.sendRequest(player, target);
				}
				
			}else{
				if (onevone.isGerman()) {
					player.sendMessage(onevone.getPrefix() + "§8» §7/challenge <§bSpieler§7>");
				} else {
					player.sendMessage(onevone.getPrefix() + "§8» §7/challenge <§bPlayer§7>");
				}
			}
			
		}
		
		return false;
	}

}
