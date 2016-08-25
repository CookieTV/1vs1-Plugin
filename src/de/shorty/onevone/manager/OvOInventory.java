package de.shorty.onevone.manager;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.kit.Kit;

public class OvOInventory {

	OneVOne onevone;

	public OvOInventory(OneVOne onevone) {
		this.onevone = onevone;
	}

	public void getKitSettingsInventory(Player player) {
		Layout l;
		if (onevone.isGerman()) {
			l = new Layout(player, "§8► §2§lKit-Einstellungen §8◄", 54);
		} else {
			l = new Layout(player, "§8► §2§lKit-Settings §8◄", 54);
		}
		Kit kit = onevone.stats.getCurrentKit(player);
		ArrayList<ItemStack> item1 = new ArrayList<>();
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		ArrayList<ItemStack> item2 = new ArrayList<>();
		item2.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item2.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		ArrayList<ItemStack> item3 = new ArrayList<>();
		item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item3.add(new ItemStack(Material.AIR));
		ArrayList<String> soup = new ArrayList<>();
		ArrayList<String> hunger = new ArrayList<>();
		ArrayList<String> tnt = new ArrayList<>();
		ArrayList<String> safe = new ArrayList<>();
		ArrayList<String> arrow = new ArrayList<>();
		if (onevone.isGerman()) {
			item2.add(onevone.api.CreateItemwithMaterial(Material.MUSHROOM_SOUP, 0, 1, "§eSuppenheilung:", null));// item
			item2.add(onevone.api.CreateItemwithMaterial(Material.COOKED_BEEF, 0, 1, "§eHunger:", null));// item
			item2.add(onevone.api.CreateItemwithMaterial(Material.TNT, 0, 1, "§eInstant TnT:", null));// item
			item2.add(onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1, "§eSafeSword:", null));// item
			item2.add(onevone.api.CreateItemwithMaterial(Material.ARROW, 0, 1, "§ePfeile aufheben:", null));// item
			soup.add("§8➥ §7aktiviere '§eSuppenheilung§7', um dich");
			soup.add("§7mit Suppen um §e3.5 Herzen §7zu heilen!");
			hunger.add("§8➥ §7aktiviere '§eHunger§7', um natürlich");
			hunger.add("§7Hunger zu verlieren!");
			tnt.add("§8➥ §7aktiviere '§eInstant TNT§7', um TNT");
			tnt.add("§7nach dem platzieren, direkt explodieren zu lassen!");
			safe.add("§8➥ §7aktiviere '§eSafe Sword§7', um dein");
			safe.add("§7Schwert nicht ausversehen droppen zu können!");
			arrow.add("§8➥ §7aktiviere '§ePfeile§7', um ");
			arrow.add("§7In-Game, keine Pfeile aufheben zu können!");
		} else {
			item2.add(onevone.api.CreateItemwithMaterial(Material.MUSHROOM_SOUP, 0, 1, "§eSoup Heal:", null));// item
			item2.add(onevone.api.CreateItemwithMaterial(Material.COOKED_BEEF, 0, 1, "§eNatural Food:", null));// item
			item2.add(onevone.api.CreateItemwithMaterial(Material.TNT, 0, 1, "§eInstant TnT:", null));// item
			item2.add(onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1, "§eSafeSword:", null));// item
			item2.add(onevone.api.CreateItemwithMaterial(Material.ARROW, 0, 1, "§eArrows:", null));// item
			soup.add("§8➥ §7Activate '§eSoup Heal§7' to");
			soup.add("§7heal yourself for §e3.5 §7hearts!");
			hunger.add("§8➥ §7Activate '§eNatural Food§7' to");
			hunger.add("§7lose naturally food!");
			tnt.add("§8➥ §7Activate '§eInstant TNT§7' to");
			tnt.add("§7let tnt explode immediately!");
			safe.add("§8➥ §7Activate '§eSafe Sword§7' to make sure");
			safe.add("§7your sword does not drop while fighting!");
			arrow.add("§8➥ §7Activate '§eArrows§7' to");
			arrow.add("§7pick up arrows ingame!");
		}
		item2.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item2.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		if (kit.containsSetting("Suppenheilung")) {
			item3.add(onevone.api.CreateItemwithID(351, 10, 1, "§8» §a✔", soup));
		} else {
			item3.add(onevone.api.CreateItemwithID(351, 8, 1, "§8» §c✖", soup));
		}
		if (kit.containsSetting("Hunger")) {
			item3.add(onevone.api.CreateItemwithID(351, 10, 1, "§8» §a✔", hunger));
		} else {
			item3.add(onevone.api.CreateItemwithID(351, 8, 1, "§8» §c✖", hunger));
		}
		if (kit.containsSetting("InstantTnT")) {
			item3.add(onevone.api.CreateItemwithID(351, 10, 1, "§8» §a✔", tnt));
		} else {
			item3.add(onevone.api.CreateItemwithID(351, 8, 1, "§8» §c✖", tnt));
		}
		if (kit.containsSetting("SafeSword")) {
			item3.add(onevone.api.CreateItemwithID(351, 10, 1, "§8» §a✔", safe));
		} else {
			item3.add(onevone.api.CreateItemwithID(351, 8, 1, "§8» §c✖", safe));
		}
		if (kit.containsSetting("arrow")) {
			item3.add(onevone.api.CreateItemwithID(351, 10, 1, "§8» §a✔", arrow));
		} else {
			item3.add(onevone.api.CreateItemwithID(351, 8, 1, "§8» §c✖", arrow));
		}
		item3.add(new ItemStack(Material.AIR));
		item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		ArrayList<ItemStack> item4 = new ArrayList<>();

		ArrayList<ItemStack> item5 = new ArrayList<>();
		item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		ArrayList<String> dmg = new ArrayList<>();
		ArrayList<String> build = new ArrayList<>();
		item5.add(new ItemStack(Material.AIR));
		item4.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item4.add(new ItemStack(Material.AIR));
		item4.add(new ItemStack(Material.AIR));
		if (onevone.isGerman()) {
			item4.add(onevone.api.CreateItemwithMaterial(Material.DIAMOND_BOOTS, 0, 1, "§eFallschaden:", null));
			item4.add(onevone.api.CreateItemwithMaterial(Material.ANVIL, 0, 1, "§eSpiel-Typ:", null));
			item4.add(onevone.api.CreateItemwithMaterial(Material.BRICK, 0, 1, "§eBauen:", null));
			dmg.add("§8➥ §7aktiviere '§eFallschaden§7', um ");
			dmg.add("§7Fallschaden In-Game zu bekommen!");
			build.add("§8➥ §7aktiviere '§eBauen§7', um ");
			build.add("§7In-Game, Blöcke zu platzieren und abzubauen.");
		} else {
			item4.add(onevone.api.CreateItemwithMaterial(Material.DIAMOND_BOOTS, 0, 1, "§eFall-Damage:", null));
			item4.add(onevone.api.CreateItemwithMaterial(Material.ANVIL, 0, 1, "§eGamemode:", null));
			item4.add(onevone.api.CreateItemwithMaterial(Material.BRICK, 0, 1, "§eBuild:", null));
			dmg.add("§8➥ §7Activate '§eFall-Damage§7' to");
			dmg.add("§7to get fall damage.");
			build.add("§8➥ §7Activate '§eBuild§7' to");
			build.add("§7be able to place blocks, destroy blocks etc.!");
		}
		item4.add(new ItemStack(Material.AIR));
		item4.add(new ItemStack(Material.AIR));
		item4.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		if (kit.containsSetting("Fallschaden")) {
			item5.add(onevone.api.CreateItemwithID(351, 10, 1, "§8» §a✔", dmg));
		} else {
			item5.add(onevone.api.CreateItemwithID(351, 8, 1, "§8» §c✖", dmg));
		}
		item5.add(getTypeItem(onevone.kitsettings.getType(kit.getName())));
		if (kit.containsSetting("Building")) {
			item5.add(onevone.api.CreateItemwithID(351, 10, 1, "§8» §a✔", build));
		} else {
			item5.add(onevone.api.CreateItemwithID(351, 8, 1, "§8» §c✖", build));
		}

		item5.add(new ItemStack(Material.AIR));
		item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		l.setFirstLine(item1);
		l.setSecondLine(item2);
		l.setThirdLine(item3);
		l.setFourthLine(item4);
		l.setFifthLine(item5);
		l.setSixthLine(item1);
		l.open(player);
	}

	public void getMatchmakingInventory(Player player) {
		Layout l;
		if (onevone.isGerman()) {
			l = new Layout(player, "§8► §2§lWarteschlange §8◄", 54);
		} else {
			l = new Layout(player, "§8► §2§lMatchmaking §8◄", 54);
		}
		ArrayList<ItemStack> item1 = new ArrayList<>();
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item1.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		ArrayList<ItemStack> item2 = new ArrayList<>();
		item2.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item2.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item2.add(new ItemStack(Material.AIR));
		item2.add(new ItemStack(Material.AIR));
		if (onevone.isGerman()) {
			item2.add(onevone.api.CreateItemwithMaterial(Material.SIGN, 0, 1, "§8» §2Suche nach...", null));
		} else {
			item2.add(onevone.api.CreateItemwithMaterial(Material.SIGN, 0, 1, "§8» §2Search for...", null));
		}
		item2.add(new ItemStack(Material.AIR));
		item2.add(new ItemStack(Material.AIR));
		item2.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item2.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		ArrayList<ItemStack> item3 = new ArrayList<>();
		item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item3.add(new ItemStack(Material.AIR));

		if (onevone.isGerman()) {
			item3.add(onevone.api.CreateItemwithMaterial(Material.DIAMOND_SWORD, 0, 1, "§3Kit-Auswahl", null));
		} else {
			item3.add(onevone.api.CreateItemwithMaterial(Material.SIGN, 0, 1, "§3Kit", null));
		}
		item3.add(new ItemStack(Material.AIR));
		if (onevone.isGerman()) {
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
		} else {
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
		}
		item3.add(new ItemStack(Material.AIR));
		item3.add(new ItemStack(Material.AIR));
		item3.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		ArrayList<ItemStack> item4 = new ArrayList<>();
		item4.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item4.add(new ItemStack(Material.AIR));
		item4.add(onevone.api.CreateItemwithMaterial(Material.ANVIL, 0, 1,
				"§3K/D §8(§c" + onevone.stats.getKD(player) + "+§8)§3:", null));
		item4.add(new ItemStack(Material.AIR));

		ItemStack i = onevone.api.CreateItemwithID(351, 10, 1, "§8» §a+0.5", null);
		item4.add(i);
		ItemStack i2 = onevone.api.CreateItemwithID(351, 1, 1, "§8» §c-0.5", null);
		item4.add(i2);

		item4.add(new ItemStack(Material.AIR));
		item4.add(new ItemStack(Material.AIR));
		item4.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		ItemStack head;
		if (onevone.isGerman()) {
			head = onevone.api.createHead("MHF_Question", null, "§8» §2Warteschlange beitreten");
		} else {
			head = onevone.api.createHead("MHF_Question", null, "§8» §2Join Queue");
		}
		ArrayList<ItemStack> item5 = new ArrayList<>();
		item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item5.add(new ItemStack(Material.AIR));
		item5.add(new ItemStack(Material.AIR));
		if (onevone.matchmaking.contains(player) == false) {
			item5.add(head);
		} else {
			if (onevone.isGerman()) {
				item5.add(onevone.api.CreateItemwithMaterial(Material.BARRIER, 0, 1, "§8» §cWarteschlange verlassen",
						null));
			} else {
				item5.add(onevone.api.CreateItemwithMaterial(Material.BARRIER, 0, 1, "§8» §cLeave Queue", null));
			}
		}
		item5.add(new ItemStack(Material.AIR));
		item5.add(new ItemStack(Material.AIR));
		item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 8, 1, "§a", null));
		item5.add(onevone.api.CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 5, 1, "§a", null));
		l.setFirstLine(item1);
		l.setSecondLine(item2);
		l.setThirdLine(item3);
		l.setFourthLine(item4);
		l.setFifthLine(item5);
		l.setSixthLine(item1);
		l.open(player);
	}

	public ItemStack getTypeItem(String type) {
		ArrayList<String> list = new ArrayList<>();
		list.add("§cKlicke, um den Spielmodus zu ändern!");
		if (type.equalsIgnoreCase("FW")) {
			return onevone.api.CreateItemwithMaterial(Material.GOLDEN_APPLE, 0, 1, "§3Type§8» §eFirst Wins!", null);
		} else if (type.equalsIgnoreCase("Bo3")) {
			return onevone.api.CreateItemwithMaterial(Material.GOLDEN_APPLE, 0, 3, "§3Type§8» §eBest of 3!", null);
		} else if (type.equalsIgnoreCase("Bo5")) {
			return onevone.api.CreateItemwithMaterial(Material.GOLDEN_APPLE, 0, 5, "§3Type§8» §eBest of 5!", null);
		} else if (type.equalsIgnoreCase("Ft3")) {
			return onevone.api.CreateItemwithMaterial(Material.GOLDEN_APPLE, 1, 3, "§3Type§8» §eFirst to 3!", null);
		} else {
			return onevone.api.CreateItemwithMaterial(Material.BARRIER, 0, 1, "§3Type§8» §eERROR", null);
		}
	}

}
