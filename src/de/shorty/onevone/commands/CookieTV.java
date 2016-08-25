package de.shorty.onevone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CookieTV implements CommandExecutor{

	OneVOne onevone;
	
	public CookieTV(OneVOne onevone) {
		this.onevone = onevone;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		if(!(sender instanceof Player))return false;
		if(onevone.getLanguageBoolean("messages.allowadvertising")){
			sendMessage((Player)sender);
		}else{
			sender.sendMessage("§cType /1vs1 for a list of commands!");
		}
		return false;
	}

	 public void sendMessage(Player player) {

		   TextComponent cp = new TextComponent("§3c§e1vs1");
		   cp.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://cookietv.de/"));
			   cp.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Gelange auf die Website des §bDevelopers§7.\n§7Get to the Website of the §bDeveloper§7.").create()));
		   TextComponent ctv = new TextComponent("§cCookieTV");
		   ctv.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://www.youtube.com/channel/UCiFLa7xenQSCxiK_GL9bhLw"));
		   ctv.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Gelange auf den §4Youtube-Kanal§7 des §bDevelopers\n§7Get to the §4Youtube-Channel §7of the §bDeveloper§7.").create()));
		   TextComponent txt = new TextComponent(" §7v." + onevone.getDescription().getVersion() + " §8| §7by ");
		   TextComponent msg = new TextComponent("                      ");
		   msg.addExtra(cp);
		   msg.addExtra(txt);
		   msg.addExtra(ctv);

		   player.spigot().sendMessage(msg);
		  
	 }

}
