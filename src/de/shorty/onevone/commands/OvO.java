package de.shorty.onevone.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.manager.Map;

public class OvO implements CommandExecutor {

	OneVOne onevone;

	public OvO(OneVOne onevone) {
		this.onevone = onevone;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(onevone.getPrefix() + "§cOnly Players can interact with that command!");
			return false;
		}

		Player player = (Player) sender;

		if (player.hasPermission("1vs1.modify")) {

			if (args.length == 0) {
				sendHelp(sender);
			} else if (args[0].equalsIgnoreCase("arena")) {
 
				if (args.length == 1) {
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena create <§bname§7>");
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena delete <§bname§7>");
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena info <§bname§7>");
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setSpawn <§bname§7> <§b1§7|§b2§7>");
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setArena <§bname§7> <§b1§7|§b2§7>");
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setMiddle <§bname§7>");
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena list");
				} else if (args[1].equalsIgnoreCase("create")) {

					if(args.length == 2){
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena create <§bname§7>");
					}else if(args.length == 3){
						
						Map map = new Map(args[2]);
						map.create();
						if (onevone.isGerman()) {
							sender.sendMessage(onevone.getPrefix() + "§7Die Arena §b" + map.getName() + " §7wurde erfolgreich erstellt!");
						} else {
							sender.sendMessage(onevone.getPrefix() + "§7Arena §b" + map.getName() + " §7successfully created!");
						}
						
					}else{
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena create <§bname§7>");
					}
					
				} else if (args[1].equalsIgnoreCase("delete")) {
					
					if(args.length == 2){
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena delete <§bname§7>");
					}else if(args.length == 3){
						
						Map map = new Map(args[2]);
						map.delete();
						if (onevone.isGerman()) {
							sender.sendMessage(onevone.getPrefix() + "§7Die Arena §b" + map.getName() + " §7wurde erfolgreich gelöscht!");
						} else {
							sender.sendMessage(onevone.getPrefix() + "§7Arena §b" + map.getName() + " §7successfully deleted!");
						}
						
					}else{
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena delete <§bname§7>");
					}
					
				} else if (args[1].equalsIgnoreCase("info")) {

					if (args.length <= 2) {
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena info <§bname§7>");
					} else if (args.length == 3) {

						Map map = new Map(args[2]);

						if (map.exists()) {
							sender.sendMessage("§8§m---------------------------------");
							sender.sendMessage("                §eArena §7" + map.getName());
							sender.sendMessage("             §7§m------------------");
							sender.sendMessage(
									"§8» §cStart 1: " + (onevone.S1.containsKey(map.getName().toLowerCase()) ? "§2✔" : "§c✖"));
							sender.sendMessage(
									"§8» §cStart 2: " + (onevone.S2.containsKey(map.getName().toLowerCase()) ? "§2✔" : "§c✖"));
							sender.sendMessage(
									"§8» §cPosition 1: " + (onevone.P1.containsKey(map.getName().toLowerCase()) ? "§2✔" : "§c✖"));
							sender.sendMessage(
									"§8» §cPosition 2: " + (onevone.P2.containsKey(map.getName().toLowerCase()) ? "§2✔" : "§c✖"));
							sender.sendMessage(
									"§8» §cMiddle: " + (onevone.Middle.containsKey(map.getName().toLowerCase()) ? "§2✔" : "§c✖"));
							sender.sendMessage("§8§m---------------------------------");
						} else {

						}

					} else {
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena info <§bname§7>");
					}
				} else if (args[1].equalsIgnoreCase("setspawn")) {

					if (args.length <= 3) {
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setSpawn <§bname§7> <§b1§7|§b2§7>");
					} else if (args.length == 4) {

						Map map = new Map(args[2]);
						
						if(map.exists()){
							
							if(args[3].equalsIgnoreCase("1")){
								
								onevone.api.saveLocation(player, "arena.yml", map.getName().toLowerCase() + ".start1", "1vs1");
								if (onevone.isGerman()) {
									sender.sendMessage(onevone.getPrefix() + "§7Der 1 Spawn der Arena §b" + map.getName() + " §7wurde erfolgreich gesetzt!");
								} else {
									sender.sendMessage(onevone.getPrefix() + "§7The 1st Spawn of Arena §b" + map.getName() + " §7was successfully set!");
								}
								
							}else{
								
								onevone.api.saveLocation(player, "arena.yml", map.getName().toLowerCase() + ".start2", "1vs1");
								if (onevone.isGerman()) {
									sender.sendMessage(onevone.getPrefix() + "§7Der 2 Spawn der Arena §b" + map.getName() + " §7wurde erfolgreich gesetzt!");
								} else {
									sender.sendMessage(onevone.getPrefix() + "§7The 2nd Spawn of Arena §b" + map.getName() + " §7was successfully set!");
								}
								
							}
							
						}
						
					} else {
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setSpawn <§bname§7> <§b1§7|§b2§7>");
					}
					
				} else if (args[1].equalsIgnoreCase("setarena")) {

					if (args.length <= 3) {
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setArena <§bname§7> <§b1§7|§b2§7>");
					} else if (args.length == 4) {

						Map map = new Map(args[2]);
						
						if(map.exists()){
							
							if(args[3].equalsIgnoreCase("1")){
								
								onevone.api.saveLocation(player, "arena.yml", map.getName().toLowerCase() + ".zone1", "1vs1");
								if (onevone.isGerman()) {
									sender.sendMessage(onevone.getPrefix() + "§7Der 1 Position der Arena §b" + map.getName() + " §7wurde erfolgreich gesetzt!");
								} else {
									sender.sendMessage(onevone.getPrefix() + "§7The 1st Position of the Arena §b" + map.getName() + " §7was successfully set!");
								}
								
							}else{
								
								onevone.api.saveLocation(player, "arena.yml", map.getName().toLowerCase() + ".zone2", "1vs1");
								if (onevone.isGerman()) {
									sender.sendMessage(onevone.getPrefix() + "§7Der 2 Position der Arena §b" + map.getName() + " §7wurde erfolgreich gesetzt!");
								} else {
									sender.sendMessage(onevone.getPrefix() + "§7The 2nd Position of the Arena §b" + map.getName() + " §7was successfully set!");
								}
								
							}
							
						}
						
					} else {
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setArena <§bname§7> <§b1§7|§b2§7>");
					}
					
				} else if (args[1].equalsIgnoreCase("setmiddle")) {

					if (args.length <= 2) {
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setMiddle <§bname§7>");
					} else if (args.length == 3) {

						Map map = new Map(args[2]);
						
						if(map.exists()){
							
								onevone.api.saveLocation(player, "arena.yml", map.getName().toLowerCase() + ".middle", "1vs1");
								if (onevone.isGerman()) {
									sender.sendMessage(onevone.getPrefix() + "§7Der 1 Position der Arena §b" + map.getName() + " §7wurde erfolgreich gesetzt!");
								} else {
									sender.sendMessage(onevone.getPrefix() + "§7The Middle of Arena §b" + map.getName() + " §7was successfully set!");
								}
							
						}
						
					} else {
						sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 arena setMiddle <§bname§7>");
					}
					
				} else if (args[1].equalsIgnoreCase("list")) {

					List<String> list = onevone.api.getConfiguration("config.yml", "1vs1").getStringList("1vs1.Maps");

					sender.sendMessage("§8§m---------------------------------");
					sender.sendMessage("             §eArena §7| §eStatus");
					sender.sendMessage("          §7§m------------------");
					if (list.isEmpty()) {
						sender.sendMessage("             §8» §cNo Arenas");
					} else {
						for (String arena : list) {
							sender.sendMessage(
									"§8» §e" + arena + " §8| " + (OneVOne.canbeused.contains(arena.toLowerCase()) ? "§2✔" : "§c✖"));
						}
					}
					sender.sendMessage("§8§m---------------------------------");

				}

			} else if (args[0].equalsIgnoreCase("resetstats")) {

				if (args.length == 1) {
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 resetStats <§bPlayer§7>");
				} else if (args.length == 2) {

					String t = args[1];
					if (Bukkit.getPlayer(t) != null) {
						if (onevone.isGerman()) {
							sender.sendMessage(
									onevone.getPrefix() + "§b" + t + "'s §7Statistiken wurden zurückgesetzt!");
						} else {
							sender.sendMessage(onevone.getPrefix() + "§b" + t + "'s §7Statistics have been reseted!");
						}
						Player target = Bukkit.getPlayer(t);
						onevone.stats.resetStats(target);
					} else {
						if (onevone.stats.existPlayerName(t)) {

							if (onevone.isGerman()) {
								sender.sendMessage(
										onevone.getPrefix() + "§b" + t + "'s §7Statistiken wurden zurückgesetzt!");
							} else {
								sender.sendMessage(
										onevone.getPrefix() + "§b" + t + "'s §7Statistics have been reseted!");
							}

							onevone.stats.resetStats(t);

						} else {
							if (onevone.isGerman()) {
								sender.sendMessage(onevone.getPrefix()
										+ "§cDer angegebene Spieler existiert nicht in unserer Datenbank!");
							} else {
								sender.sendMessage(
										onevone.getPrefix() + "§cThe specified player does not exist in our database!");
							}
						}
					}

				} else {
					sender.sendMessage(onevone.getPrefix() + "§8» §7/1vs1 resetStats <§bPlayer§7>");
				}

			} else if (args[0].equalsIgnoreCase("setstats")) {
				sender.sendMessage(onevone.getPrefix() + "§cThis command will be available soon!");
			} else if (args[0].equalsIgnoreCase("setlanguage")) {

				if (args.length == 1) {
					sender.sendMessage(
							onevone.getPrefix() + "§8» §7/1vs1 setLanguage <§fEn§4gli§fsh§7|§0Ge§4rm§6an§7>");
				} else if (args.length == 2) {

					if (args[1].equalsIgnoreCase("english")) {
						onevone.getConfig().set("Language.file", "lang_EN.yml");
						onevone.saveConfig();
						sender.sendMessage(onevone.getPrefix()
								+ "Your language has been successfully updated to §fEn§4gli§fsh§7!");
					} else if (args[1].equalsIgnoreCase("german") || args[1].equalsIgnoreCase("deutsch")) {
						onevone.getConfig().set("Language.file", "lang_DE.yml");
						onevone.saveConfig();
						sender.sendMessage(
								onevone.getPrefix() + "Die Sprache wurde erfolgreich auf §0De§4ut§6sch §7umgestellt!");
					} else {
						sender.sendMessage(onevone.getPrefix()
								+ "§cI'm sorry, this language isnt supported yet! Help me via Skype to add more languages.");
					}

				} else {
					sender.sendMessage(
							onevone.getPrefix() + "§8» §7/1vs1 setLanguage <§fEn§4gli§fsh§7|§0Ge§4rm§6an§7>");
				}

			} else if (args[0].equalsIgnoreCase("setlobby")) {

				onevone.api.saveLocation(player, "locations.yml", "Location.spawn", "1vs1");
				if (onevone.isGerman()) {
					sender.sendMessage(onevone.getPrefix() + "§7Die Location der Lobby wurde erfolgreich gesetzt!");
				} else {
					sender.sendMessage(onevone.getPrefix() + "§7Successfully setted location for lobby!");
				}

			} else if (args[0].equalsIgnoreCase("sethologram")) {

				onevone.api.saveLocation(player, "locations.yml", "Location.hologram", "1vs1");
				if (onevone.isGerman()) {
					sender.sendMessage(onevone.getPrefix() + "§7Die Location des Holograms wurde erfolgreich gesetzt!");
				} else {
					sender.sendMessage(onevone.getPrefix() + "§7Successfully setted location for hologram!");
				}

			} else if (args[0].equalsIgnoreCase("setzombie")) {

				onevone.api.saveLocation(player, "locations.yml", "Location.zombie", "1vs1");
				if (onevone.isGerman()) {
					sender.sendMessage(onevone.getPrefix() + "§7Die Location des Zombies wurde erfolgreich gesetzt!");
				} else {
					sender.sendMessage(onevone.getPrefix() + "§7Successfully setted location for zombie!");
				}

			} else if (args[0].equalsIgnoreCase("setsheep")) {

				onevone.api.saveLocation(player, "locations.yml", "Location.sheep", "1vs1");
				if (onevone.isGerman()) {
					sender.sendMessage(onevone.getPrefix() + "§7Die Location des Schafes wurde erfolgreich gesetzt!");
				} else {
					sender.sendMessage(onevone.getPrefix() + "§7Successfully setted location for Sheep!");
				}

			} else if (args[0].equalsIgnoreCase("setprefix")) {
				sender.sendMessage(onevone.getPrefix() + "§cThis command will be available soon!");
			} else if (args[0].equalsIgnoreCase("checkmysql")) {

				if (onevone.mysql.isConnected()) {
					sender.sendMessage("§8§m---------------------------------");
					sender.sendMessage("                      §3MySQL");
					sender.sendMessage("          §7§m------------------");
					sender.sendMessage("             §8» §2Connected ✔");
					sender.sendMessage("§8§m---------------------------------");
				} else {
					sender.sendMessage("§8§m---------------------------------");
					sender.sendMessage("                      §3MySQL");
					sender.sendMessage("        §7§m------------------");
					sender.sendMessage("             §8» §cNot Connected ✖");
					sender.sendMessage("§8§m---------------------------------");
				}

			} else if (args[0].equalsIgnoreCase("checkupdates")) {
				sender.sendMessage(onevone.getPrefix() + "§b1vs1 §7ist up to date §8| §2✔");
			} else if (args[0].equalsIgnoreCase("build")) {
				if(onevone.build.contains(player)){
					onevone.build.remove(player);
					sender.sendMessage(onevone.getPrefix() + "§cBuild mode updated.");
				}else{
					onevone.build.add(player);
					sender.sendMessage(onevone.getPrefix() + "§aBuild mode updated.");
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage("§8§m---------------------------------");
				sender.sendMessage("                   §3c§e1vs1");
				sender.sendMessage("          §7§m------------------");
				sender.sendMessage("§8» §eArenas: §7"
						+ onevone.api.getConfiguration("config.yml", "1vs1").getStringList("1vs1.Maps").size());
				if (onevone.isGerman()) {
					sender.sendMessage("           §8➥ §aAktiviert: §7" + OneVOne.canbeused.size());
					sender.sendMessage("§8» §eSpieler registriert: §7" + onevone.stats.getTotalPlayers());
					sender.sendMessage("§8» §eDeveloper: §7CookieTV");
				} else {
					sender.sendMessage("           §8➥ §aactivated: §7" + OneVOne.canbeused.size());
					sender.sendMessage("§8» §ePlayers registered: §7" + onevone.stats.getTotalPlayers());
					sender.sendMessage("§8» §eDeveloper: §7CookieTV");
				}
				sender.sendMessage("§8§m---------------------------------");
			}

		} else {
			if (onevone.getLanguageBoolean("messages.allowadvertising")) {
				player.performCommand("cookietv");
			} else {
				sender.sendMessage(onevone.getLanguageString("messages.nopermissions", player));
			}
		}

		return false;
	}

	private void sendHelp(CommandSender sender) {
		sender.sendMessage("§8§m-------------------------------------------------------");
		sender.sendMessage(
				"                                   §3c§e1vs1 §7v." + onevone.getDescription().getVersion() + " §8| §7by §cCookieTV");
		sender.sendMessage("                          §7§m---------------------------");
		sender.sendMessage("§8» §7/1vs1 arena create <§bname§7>");
		sender.sendMessage("§8» §7/1vs1 arena delete <§bname§7>");
		sender.sendMessage("§8» §7/1vs1 arena info <§bname§7>");
		sender.sendMessage("§8» §7/1vs1 arena setSpawn <§bname§7> <§b1§7|§b2§7>");
		sender.sendMessage("§8» §7/1vs1 arena setArena <§bname§7> <§b1§7|§b2§7>");
		sender.sendMessage("§8» §7/1vs1 arena setMiddle <§bname§7>");
		sender.sendMessage("§8» §7/1vs1 arena list");
		sender.sendMessage("                          §7§m---------------------------");
		sender.sendMessage("§8» §7/1vs1 resetStats <§bPlayer§7>");
		sender.sendMessage("§8» §7/1vs1 setStats <§bPlayer§7> <§bStatistic§7> <§bnumber§7> §8| §c✖");
		sender.sendMessage("§8» §7/1vs1 setLanguage <§fEn§4gli§fsh§7|§0▉§4▉§6▉§7>");
		sender.sendMessage("§8» §7/1vs1 setHologram");
		sender.sendMessage("§8» §7/1vs1 setLobby");
		sender.sendMessage("§8» §7/1vs1 setZombie §8➤ §2Matchmaking");
		sender.sendMessage("§8» §7/1vs1 setSheep §8➤ §2Kit-Settings");
		sender.sendMessage("                         §7§m---------------------------");
		sender.sendMessage("§8» §7/1vs1 setPrefix <§bprefix§7> §8| §c✖");
		sender.sendMessage("§8» §7/1vs1 checkmysql");
		sender.sendMessage("§8» §7/1vs1 checkupdates §8| §c✖");
		sender.sendMessage("§8» §7/1vs1 build");
		sender.sendMessage("§8» §7/1vs1 info");
		sender.sendMessage("");
		sender.sendMessage("§8§m-------------------------------------------------------");
	}

}
