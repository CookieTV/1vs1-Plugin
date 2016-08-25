package de.shorty.onevone.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import de.shorty.onevone.OneVOne;
import de.shorty.onevone.kit.Kit;

public class InventoryClickListener implements Listener {

	public OneVOne onevone;

	public InventoryClickListener(OneVOne onevone) {
		this.onevone = onevone;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();
		
		if(event.getCurrentItem() == null || event.getClickedInventory() == null){
			return;
		}
		
		if (event.getInventory().getName().equalsIgnoreCase("§8► §2§lKit-Einstellungen §8◄") || event.getInventory().getName().equalsIgnoreCase("§8► §2§lKit-Settings §8◄")) {
			event.setCancelled(true);

			Kit kit = onevone.stats.getCurrentKit(player);
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §a✔")) {

				if(onevone.isGerman()){
					List<String> list = event.getCurrentItem().getItemMeta().getLore();
					if (list.get(0).equalsIgnoreCase("§8➥ §7aktiviere '§eSuppenheilung§7', um dich")) {
						kit.removeRule("Suppenheilung");
						player.sendMessage(onevone.getPrefix() + "§7'§eSuppenheilung§7' wurde §cdeaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eBauen§7'")) {
						kit.removeRule("Building");
						player.sendMessage(onevone.getPrefix() + "§7'§eBauen§7' wurde §cdeaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eHunger§7'")) {
						kit.removeRule("Hunger");
						player.sendMessage(onevone.getPrefix() + "§7'§eHunger§7' wurde §cdeaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eInstant TNT§7'")) {
						kit.removeRule("InstantTnT");
						player.sendMessage(onevone.getPrefix() + "§7'§eInstantTNT§7' wurde §cdeaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eSafe Sword§7'")) {
						kit.removeRule("SafeSword");
						player.sendMessage(onevone.getPrefix() + "§7'§eSafeSword§7' wurde §cdeaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§ePfeile§7'")) {
						kit.removeRule("arrow");
						player.sendMessage(onevone.getPrefix() + "§7'§eArrow pickup§7' wurde §cdeaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eFallschaden§7'")) {
						kit.removeRule("Fallschaden");
						player.sendMessage(onevone.getPrefix() + "§7'§eFallschaden§7' wurde §cdeaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					}
				}else{
					List<String> list = event.getCurrentItem().getItemMeta().getLore();
					if (list.get(0).equalsIgnoreCase("§8➥ §7Activate '§eSoup Heal§7' to")) {
						kit.removeRule("Suppenheilung");
						player.sendMessage(onevone.getPrefix() + "§7'§eSoup heal§7' was §cdisabled§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7Activate '§eBuild§7' to")) {
						kit.removeRule("Building");
						player.sendMessage(onevone.getPrefix() + "§7'§eBuild§7' was §cdisabled§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7Activate '§eNatural Food§7' to")) {
						kit.removeRule("Hunger");
						player.sendMessage(onevone.getPrefix() + "§7'§eNatural Food§7' was §cdisabled§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7Activate '§eInstant TNT§7' to")) {
						kit.removeRule("InstantTnT");
						player.sendMessage(onevone.getPrefix() + "§7'§eInstantTNT§7' was §cdisabled§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7Activate '§eSafe Sword§7' to make sure")) {
						kit.removeRule("SafeSword");
						player.sendMessage(onevone.getPrefix() + "§7'§eSafeSword§7' was §cdisabled§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7Activate '§eArrows§7' to")) {
						kit.removeRule("arrow");
						player.sendMessage(onevone.getPrefix() + "§7'§eArrows§7' was §cdisabled§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7Activate '§eFall-Damage§7' to")) {
						kit.removeRule("Fallschaden");
						player.sendMessage(onevone.getPrefix() + "§7'§eFall-Damage§7' was §cdisabled§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					}
				}
				
			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §c✖")) {

				if(onevone.isGerman()){
					List<String> list = event.getCurrentItem().getItemMeta().getLore();
					if (list.get(0).equalsIgnoreCase("§8➥ §7aktiviere '§eSuppenheilung§7', um dich")) {
						kit.addRule("Suppenheilung");
						player.sendMessage(onevone.getPrefix() + "§7'§eSuppenheilung§7' wurde §aaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eBauen§7'")) {
						kit.addRule("Building");
						player.sendMessage(onevone.getPrefix() + "§7'§eBauen§7' wurde §aaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eHunger§7'")) {
						kit.addRule("Hunger");
						player.sendMessage(onevone.getPrefix() + "§7'§eHunger§7' wurde §aaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eInstant TNT§7'")) {
						kit.addRule("InstantTnT");
						player.sendMessage(onevone.getPrefix() + "§7'§eInstantTNT§7' wurde §aaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eSafe Sword§7'")) {
						kit.addRule("SafeSword");
						player.sendMessage(onevone.getPrefix() + "§7'§eSafeSword§7' wurde §aaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§ePfeile§7'")) {
						kit.addRule("arrow");
						player.sendMessage(onevone.getPrefix() + "§7'§eArrow pickup§7' wurde §aaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					} else if (list.get(0).startsWith("§8➥ §7aktiviere '§eFallschaden§7'")) {
						kit.addRule("Fallschaden");
						player.sendMessage(onevone.getPrefix() + "§7'§eFallschaden§7' wurde §aaktiviert§7!");
						onevone.inventorys.getKitSettingsInventory(player);
					}
					
				}else{
						List<String> list = event.getCurrentItem().getItemMeta().getLore();
						if (list.get(0).equalsIgnoreCase("§8➥ §7Activate '§eSoup Heal§7' to")) {
							kit.addRule("Suppenheilung");
							player.sendMessage(onevone.getPrefix() + "§7'§eSoup heal§7' was §aactivated§7!");
							onevone.inventorys.getKitSettingsInventory(player);
						} else if (list.get(0).startsWith("§8➥ §7Activate '§eBuild§7' to")) {
							kit.addRule("Building");
							player.sendMessage(onevone.getPrefix() + "§7'§eBuild§7' was §aactivated§7!");
							onevone.inventorys.getKitSettingsInventory(player);
						} else if (list.get(0).startsWith("§8➥ §7Activate '§eNatural Food§7' to")) {
							kit.addRule("Hunger");
							player.sendMessage(onevone.getPrefix() + "§7'§eNatural Food§7' was §aactivated§7!");
							onevone.inventorys.getKitSettingsInventory(player);
						} else if (list.get(0).startsWith("§8➥ §7Activate '§eInstant TNT§7' to")) {
							kit.addRule("InstantTnT");
							player.sendMessage(onevone.getPrefix() + "§7'§eInstantTNT§7' was §aactivated§7!");
							onevone.inventorys.getKitSettingsInventory(player);
						} else if (list.get(0).startsWith("§8➥ §7Activate '§eSafe Sword§7' to make sure")) {
							kit.addRule("SafeSword");
							player.sendMessage(onevone.getPrefix() + "§7'§eSafeSword§7' was §aactivated§7!");
							onevone.inventorys.getKitSettingsInventory(player);
						} else if (list.get(0).startsWith("§8➥ §7Activate '§eArrows§7' to")) {
							kit.addRule("arrow");
							player.sendMessage(onevone.getPrefix() + "§7'§eArrows§7' was §aactivated§7!");
							onevone.inventorys.getKitSettingsInventory(player);
						} else if (list.get(0).startsWith("§8➥ §7Activate '§eFall-Damage§7' to")) {
							kit.addRule("Fallschaden");
							player.sendMessage(onevone.getPrefix() + "§7'§eFall-Damage§7' was §aactivated§7!");
							onevone.inventorys.getKitSettingsInventory(player);
						}
				}
				
				} else if (event.getCurrentItem().getType() == Material.GOLDEN_APPLE) {
	
					if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Type§8» §eFirst to 3!")) {
						onevone.kitsettings.setType(kit.getName(), "Bo3");
						onevone.inventorys.getKitSettingsInventory(player);
						String gamemode = onevone.getLanguageString("messages.changedGamemode", player);
						gamemode = gamemode.replace("%KIT%", kit.getName());
						gamemode = gamemode.replace("%GAMEMODE%", "Best Of 3");
						player.sendMessage(gamemode);
					} else if (event.getCurrentItem().getItemMeta().getDisplayName()
							.equalsIgnoreCase("§3Type§8» §eBest of 3!")) {
						onevone.kitsettings.setType(kit.getName(), "Bo5");
						onevone.inventorys.getKitSettingsInventory(player);
						String gamemode = onevone.getLanguageString("messages.changedGamemode", player);
						gamemode = gamemode.replace("%KIT%", kit.getName());
						gamemode = gamemode.replace("%GAMEMODE%", "Best Of 5");
						player.sendMessage(gamemode);
					} else if (event.getCurrentItem().getAmount() == 5) {
						onevone.kitsettings.setType(kit.getName(), "FW");
						onevone.inventorys.getKitSettingsInventory(player);
						String gamemode = onevone.getLanguageString("messages.changedGamemode", player);
						gamemode = gamemode.replace("%KIT%", kit.getName());
						gamemode = gamemode.replace("%GAMEMODE%", "First Wins");
						player.sendMessage(gamemode);
					} else if (event.getCurrentItem().getItemMeta().getDisplayName()
							.equalsIgnoreCase("§3Type§8» §eFirst Wins!")) {
						onevone.kitsettings.setType(kit.getName(), "Ft3");
						onevone.inventorys.getKitSettingsInventory(player);
						String gamemode = onevone.getLanguageString("messages.changedGamemode", player);
						gamemode = gamemode.replace("%KIT%", kit.getName());
						gamemode = gamemode.replace("%GAMEMODE%", "First to 3");
						player.sendMessage(gamemode);
					}
					
			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Type§8» §eERROR")) {
				onevone.kitsettings.setType(kit.getName(), "Ft3");
				String gamemode = onevone.getLanguageString("messages.changedGamemode", player);
				gamemode = gamemode.replace("%KIT%", kit.getName());
				gamemode = gamemode.replace("%GAMEMODE%", "First to 3");
				player.sendMessage(gamemode);
				onevone.inventorys.getKitSettingsInventory(player);
			}

		} else if (event.getInventory().getName().equalsIgnoreCase("§8► §2§lWarteschlange §8◄") | event.getInventory().getName().equalsIgnoreCase("§8► §2§lMatchmaking §8◄")) {
			event.setCancelled(true);
			
			if(event.getCurrentItem() == null)return;
			
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aeigenes Kit")) {

				onevone.stats.setSearchType(player, "own");
				player.sendMessage(onevone.getPrefix() + "Du spielst nun mit deinem eigenen Kit!");
				Inventory inv = event.getInventory();
				ArrayList<ItemStack> item3 = new ArrayList<>();
				item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item3.add(new ItemStack(Material.AIR));

				item3.add(onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1, "§3Kit-Auswahl:", null));
				item3.add(new ItemStack(Material.AIR));
				if (onevone.stats.getSearchType(player).equalsIgnoreCase("own")) {
					ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §aeigenes Kit", null);
					i.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					item3.add(i);
					ItemStack i2 = onevone.api.CreateItemwithID(351, 14, 1, "§8» §6Kit egal", null);
					item3.add(i2);
				} else {
					ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §aeigenes Kit", null);
					item3.add(i);
					ItemStack i2 = onevone.api.CreateItemwithID(351, 14, 1, "§8» §6Kit egal", null);
					i2.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					item3.add(i2);
				}
				item3.add(new ItemStack(Material.AIR));
				item3.add(new ItemStack(Material.AIR));
				item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				for (int i = 0; i < item3.size(); i++) {

					inv.setItem(i + 18, item3.get(i));

				}
			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §6Kit egal")) {
				onevone.stats.setSearchType(player, "cares");
				player.sendMessage(onevone.getPrefix() + "Dir ist es egal, mit welchem Kit zu spielst!");
				Inventory inv = event.getInventory();
				ArrayList<ItemStack> item3 = new ArrayList<>();
				item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item3.add(new ItemStack(Material.AIR));

				item3.add(onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1, "§3Kit-Auswahl:", null));
				item3.add(new ItemStack(Material.AIR));
				if (onevone.stats.getSearchType(player).equalsIgnoreCase("own")) {
					ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §aeigenes Kit", null);
					i.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					item3.add(i);
					ItemStack i2 = onevone.api.CreateItemwithID(351, 14, 1, "§8» §6Kit egal", null);
					item3.add(i2);
				} else {
					ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §aeigenes Kit", null);
					item3.add(i);
					ItemStack i2 = onevone.api.CreateItemwithID(351, 14, 1, "§8» §6Kit egal", null);
					i2.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					item3.add(i2);
				}
				item3.add(new ItemStack(Material.AIR));
				item3.add(new ItemStack(Material.AIR));
				item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				for (int i = 0; i < item3.size(); i++) {

					inv.setItem(i + 18, item3.get(i));

				}
			}else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aown Kit")) {

				onevone.stats.setSearchType(player, "own");
				player.sendMessage(onevone.getPrefix() + "You will be playing with your own kit!");
				Inventory inv = event.getInventory();
				ArrayList<ItemStack> item3 = new ArrayList<>();
				item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item3.add(new ItemStack(Material.AIR));

				item3.add(onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1, "§3Kit-Auswahl:", null));
				item3.add(new ItemStack(Material.AIR));
				if (onevone.stats.getSearchType(player).equalsIgnoreCase("own")) {
					ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §aown kit", null);
					i.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					item3.add(i);
					ItemStack i2 = onevone.api.CreateItemwithID(351, 14, 1, "§8» §6does not matter", null);
					item3.add(i2);
				} else {
					ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §aown kit", null);
					item3.add(i);
					ItemStack i2 = onevone.api.CreateItemwithID(351, 14, 1, "§8» §6does not matter", null);
					i2.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					item3.add(i2);
				}
				item3.add(new ItemStack(Material.AIR));
				item3.add(new ItemStack(Material.AIR));
				item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				for (int i = 0; i < item3.size(); i++) {

					inv.setItem(i + 18, item3.get(i));

				}
			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §6does not matter")) {
				onevone.stats.setSearchType(player, "cares");
				player.sendMessage(onevone.getPrefix() + "The kit you will play with does not matter!");
				Inventory inv = event.getInventory();
				ArrayList<ItemStack> item3 = new ArrayList<>();
				item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item3.add(new ItemStack(Material.AIR));
				
				if (onevone.isGerman()) {
					item3.add(onevone.api.CreateItemwithMaterial(Material.SIGN, 0, 1, "§3Kit-Auswahl", null));
				} else {
					item3.add(onevone.api.CreateItemwithMaterial(Material.SIGN, 0, 1, "§3Kit", null));
				}
				item3.add(new ItemStack(Material.AIR));
				if (onevone.stats.getSearchType(player).equalsIgnoreCase("own")) {
					ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §aown kit", null);
					i.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					item3.add(i);
					ItemStack i2 = onevone.api.CreateItemwithID(351, 14, 1, "§8» §6does not matter", null);
					item3.add(i2);
				} else {
					ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §aown kit", null);
					item3.add(i);
					ItemStack i2 = onevone.api.CreateItemwithID(351, 14, 1, "§8» §6does not matter", null);
					i2.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					item3.add(i2);
				}
				item3.add(new ItemStack(Material.AIR));
				item3.add(new ItemStack(Material.AIR));
				item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				for (int i = 0; i < item3.size(); i++) {

					inv.setItem(i + 18, item3.get(i));

				}
			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §a+0.5")) {
				double kd = onevone.stats.getKD(player);
				kd += 0.5;
				Inventory inv = event.getInventory();
				ArrayList<ItemStack> item4 = new ArrayList<>();
				item4.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item4.add(new ItemStack(Material.AIR));
				item4.add(
						onevone.api.CreateItemwithMaterial(Material.ANVIL, 0, 1, "§3K/D §8(§c" + kd + "+§8)§3:", null));
				item4.add(new ItemStack(Material.AIR));
				ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §a+0.5", null);
				item4.add(i);
				ItemStack i2 = onevone.api.CreateItemwithID(351, 1, 1, "§8» §c-0.5", null);
				item4.add(i2);
				item4.add(new ItemStack(Material.AIR));
				item4.add(new ItemStack(Material.AIR));
				item4.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				for (int count = 0; count < item4.size(); count++) {

					inv.setItem(count + 27, item4.get(count));

				}
				onevone.stats.setKD(player, kd);
				player.sendMessage(onevone.getLanguageString("messages.kdchanged", player).replace("%KD%", "" + kd));


			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §c-0.5")) {
				double kd = onevone.stats.getKD(player);
				if (kd <= 0.0) {
					player.sendMessage(onevone.getPrefix() + "§cDie K/D kann nicht unter 0.0 liegen!");
					return;
				}
				kd -= 0.5;
				Inventory inv = event.getInventory();
				ArrayList<ItemStack> item4 = new ArrayList<>();
				item4.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item4.add(new ItemStack(Material.AIR));
				item4.add(
						onevone.api.CreateItemwithMaterial(Material.ANVIL, 0, 1, "§3K/D §8(§c" + kd + "+§8)§3:", null));
				item4.add(new ItemStack(Material.AIR));
				ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §a+0.5", null);
				item4.add(i);
				ItemStack i2 = onevone.api.CreateItemwithID(351, 1, 1, "§8» §c-0.5", null);
				item4.add(i2);
				item4.add(new ItemStack(Material.AIR));
				item4.add(new ItemStack(Material.AIR));
				item4.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				for (int count = 0; count < item4.size(); count++) {

					inv.setItem(count + 27, item4.get(count));

				}
				onevone.stats.setKD(player, kd);
				player.sendMessage(onevone.getLanguageString("messages.kdchanged", player).replace("%KD%", "" + kd));

			} else if (event.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§8» §2Warteschlange beitreten")) {
				if (onevone.stats.getSearchType(player).equalsIgnoreCase("own")) {
					onevone.matchmaking.addPlayer(player, "own");
				} else {
					onevone.matchmaking.addPlayer(player, "egal");
				}
				Inventory inv = event.getInventory();
				ItemStack head = onevone.api.createHead("MHF_Question", null, "§8» §2Warteschlange beitreten");
				ArrayList<ItemStack> item5 = new ArrayList<>();
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "'", null));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item5.add(new ItemStack(Material.AIR));
				item5.add(new ItemStack(Material.AIR));
				if (onevone.matchmaking.contains(player) == false) {
					item5.add(head);
				} else {
					item5.add(onevone.api.CreateItemwithMaterial(Material.BARRIER, 0, 1,
							"§8» §cWarteschlange verlassen", null));
				}
				item5.add(new ItemStack(Material.AIR));
				item5.add(new ItemStack(Material.AIR));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "'", null));
				for (int i = 0; i < item5.size(); i++) {
					inv.setItem(i + 36, item5.get(i));
				}
			} else if (event.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§8» §cWarteschlange verlassen")) {
				onevone.matchmaking.removePlayer(player);
				ItemStack head = onevone.api.createHead("MHF_Question", null, "§8» §2Warteschlange beitreten");
				Inventory inv = event.getInventory();

				ArrayList<ItemStack> item5 = new ArrayList<>();
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "'", null));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item5.add(new ItemStack(Material.AIR));
				item5.add(new ItemStack(Material.AIR));
				if (onevone.matchmaking.contains(player) == false) {
					item5.add(head);
				} else {
					item5.add(onevone.api.CreateItemwithMaterial(Material.BARRIER, 0, 1,
							"§8» §cWarteschlange verlassen", null));
				}
				item5.add(new ItemStack(Material.AIR));
				item5.add(new ItemStack(Material.AIR));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "'", null));
				for (int i = 0; i < item5.size(); i++) {
					inv.setItem(i + 36, item5.get(i));
				}
			} else if (event.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§8» §2Join Queue")) {
				if (onevone.stats.getSearchType(player).equalsIgnoreCase("own")) {
					onevone.matchmaking.addPlayer(player, "own");
				} else {
					onevone.matchmaking.addPlayer(player, "egal");
				}
				Inventory inv = event.getInventory();
				ItemStack head = onevone.api.createHead("MHF_Question", null, "§8» §2Join Queue");
				ArrayList<ItemStack> item5 = new ArrayList<>();
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "'", null));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item5.add(new ItemStack(Material.AIR));
				item5.add(new ItemStack(Material.AIR));
				if (onevone.matchmaking.contains(player) == false) {
					item5.add(head);
				} else {
					item5.add(onevone.api.CreateItemwithMaterial(Material.BARRIER, 0, 1,
							"§8» §cLeave Queue", null));
				} 
				item5.add(new ItemStack(Material.AIR));
				item5.add(new ItemStack(Material.AIR));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "'", null));
				for (int i = 0; i < item5.size(); i++) {
					inv.setItem(i + 36, item5.get(i));
				}
			} else if (event.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§8» §cLeave Queue")) {
				onevone.matchmaking.removePlayer(player);
				ItemStack head = onevone.api.createHead("MHF_Question", null, "§8» §2Join Queue");
				Inventory inv = event.getInventory();

				ArrayList<ItemStack> item5 = new ArrayList<>();
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "'", null));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item5.add(new ItemStack(Material.AIR));
				item5.add(new ItemStack(Material.AIR));
				if (onevone.matchmaking.contains(player) == false) {
					item5.add(head);
				} else {
					item5.add(onevone.api.CreateItemwithMaterial(Material.BARRIER, 0, 1,
							"§8» §cLeave Queue", null));
				}
				item5.add(new ItemStack(Material.AIR));
				item5.add(new ItemStack(Material.AIR));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "'", null));
				item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "'", null));
				for (int i = 0; i < item5.size(); i++) {
					inv.setItem(i + 36, item5.get(i));
				}
			}

		}
		try{
		if(event.getCurrentItem().getType() == Material.GLASS || event.getCurrentItem().getTypeId() == 95 || event.getCursor().getType() == Material.GLASS || event.getCursor().getTypeId() == 95){
			event.setCancelled(true);
			return;
		}
		}catch(Exception e){
			
		}
		if(onevone.matchmanager.isIngame(player)){
			event.setCancelled(false);
		}else if(onevone.kitmanager.isEditing(player) && !event.getInventory().getName().startsWith("§8►")){
			event.setCancelled(false);
		}else if(onevone.build.contains(player)){
			event.setCancelled(false);
		}else{
			event.setCancelled(true);
		}
	}

}
