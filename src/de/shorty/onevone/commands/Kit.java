package de.shorty.onevone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;

public class Kit implements CommandExecutor{

	OneVOne onevone;
	
	public Kit(OneVOne onevone) {
		this.onevone = onevone;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		if(sender instanceof Player){
			Player player = (Player) sender;
			if(onevone.matchmanager.isIngame(player) || onevone.kitmanager.isEditing(player))return false;
			if(args.length == 0){
				
				de.shorty.onevone.kit.Kit kit = onevone.stats.getCurrentKit(player);
				kit.set(player);
				onevone.kitmanager.setViewing(player);
				
			}else if(args.length == 1){
				String targetkit = args[0];
				if(!onevone.kitmanager.exists(targetkit)){
					if(onevone.isGerman()){
						player.sendMessage(onevone.getPrefix() + "§cDieses Kit existiert in unserer Datenbank nicht!");
					}else{
						player.sendMessage(onevone.getPrefix() + "§cThat kit does not exist!");
					}
				}else{
					if(onevone.kitmanager.isAvailable(targetkit)){
						de.shorty.onevone.kit.Kit kit = new de.shorty.onevone.kit.Kit(targetkit, false);
						kit.set(player);
						onevone.stats.setCurrentKit(player, kit);
					}else{
						de.shorty.onevone.kit.Kit kit = new de.shorty.onevone.kit.Kit(targetkit, true);
						kit.fetchData();
						kit.set(player);
						onevone.stats.setCurrentKit(player, kit);
					}
					onevone.kitmanager.setViewing(player);
				}
				
			}
		}
		return false;
	}

}
