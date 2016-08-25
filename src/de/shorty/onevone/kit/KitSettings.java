package de.shorty.onevone.kit;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;


public class KitSettings {

	OneVOne onevone;
	
	public KitSettings(OneVOne onevone){
		this.onevone = onevone;
	}
	
	public void addRule(String name, String kitname) {

		if (containsInRules(name)) {

			String s = getRules(kitname);
			s = s + name + ",";
			onevone.mysql.update("UPDATE c1vs1_Kits SET Settings='" + s + "' WHERE UUID= '" + kitname + "';");

		} else {
			return;
		}

	}

	public void removeRule(String name, String kitname) {

		if (containsInRules(name)) {

			String s = getRules(kitname);
			s = s.replace(name + ",", "");
			onevone.mysql.update("UPDATE c1vs1_Kits SET Settings='" + s + "' WHERE UUID= '" + kitname + "';");

		} else {
			return;
		}

	}

	public void addKit(Player p, String kitname) {

		String s = getNames(p);
		s = s + kitname + ",";
		onevone.mysql.update("UPDATE c1vs1_Users SET KitNames='" + s + "' WHERE UUID= '" + p.getUniqueId().toString() + "';");

	}

	public void removeKit(Player p, String kitname) {

		String s = getNames(p);
		s = s.replace(kitname + ",", "");
		onevone.mysql.update("UPDATE c1vs1_Users SET KitNames='" + s + "' WHERE UUID= '" + p.getUniqueId().toString() + "';");

	}

	public void saveKits(Player p) {

		for (String kit : getKits(p)) {

			Kit k = new Kit(kit, false);
			k.save(p);

		}

	}

	public List<String> getKits(Player p) {

		String s = getNames(p);
		String[] arr = s.split(";");
		List<String> list = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}

	public List<String> getRuleList(String kitname) {

		String s = getRules(kitname);
		String[] arr = s.split(",");
		List<String> list = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}

	public List<String> getRuleListLC(String kitname) {

		String s = getRules(kitname);
		String[] arr = s.split(",");
		List<String> list = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i].toLowerCase());
		}
		return list;
	}

	public String getType(String kitname) {

		String s = "";

		try {

			ResultSet rs = onevone.mysql.getResult("SELECT Type FROM c1vs1_Kits WHERE KitName= '" + kitname + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("Type")) == null)) {
			} else {
				s = rs.getString("Type");
			}

		} catch (Exception e1) {

		}

		return s;
	}

	public String getNames(Player p) {

		String s = "";

		try {

			ResultSet rs = onevone.mysql
					.getResult("SELECT KitNames FROM c1vs1_Users WHERE UUID= '" + p.getUniqueId().toString() + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("KitNames")) == null)) {
			} else {
				s = rs.getString("KitNames");
			}

		} catch (Exception e1) {

		}

		return s;
	}

	public String getRules(String kitname) {

		String s = "";

		try {

			ResultSet rs = onevone.mysql.getResult("SELECT Settings FROM c1vs1_Kits WHERE KitName= '" + kitname + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("Settings")) == null)) {
			} else {
				s = rs.getString("Settings");
			}

		} catch (Exception e1) {

		}

		return s;
	}

	public String getKit(String kitname) {

		String s = "";

		try {

			ResultSet rs = onevone.mysql.getResult("SELECT Kit FROM c1vs1_Kits WHERE KitName= '" + kitname + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("Kit")) == null)) {
			} else {
				s = rs.getString("Kit");
			}

		} catch (Exception e1) {

		}

		return s;
	}

	public void setType(String kitname, String type) {

		onevone.mysql.update("UPDATE c1vs1_Kits SET Type='" + type + "' WHERE KitName='" + kitname + "'");

	}

	public void setSettings(String kitname, String sett) {

		onevone.mysql.update("UPDATE c1vs1_Kits SET Settings='" + sett + "' WHERE KitName='" + kitname + "'");

	}

	public void setKit(String kitname, String kit) {

		onevone.mysql.update("UPDATE c1vs1_Kits SET Kit='" + kit + "' WHERE KitName='" + kitname + "'");

	}

	public void setArmor(String kitname, String armor) {

		onevone.mysql.update("UPDATE c1vs1_Kits SET Armor='" + armor + "' WHERE KitName='" + kitname + "'");

	}

	public String getArmor(String kitname) {

		String s = "";

		try {

			ResultSet rs = onevone.mysql.getResult("SELECT Armor FROM c1vs1_Kits WHERE KitName= '" + kitname + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("Armor")) == null)) {
			} else {
				s = rs.getString("Armor");
			}

		} catch (Exception e1) {

		}

		return s;
	}

	public boolean containsRule(String name, String kitname) {
		return getRuleListLC(kitname).contains(name.toLowerCase());
	}

	public boolean containsInRules(String name) {

		if (name.equalsIgnoreCase("Suppenheilung")) {// 1
			return true;
		} else if (name.equalsIgnoreCase("Hunger")) {// 2
			return true;
		} else if (name.equalsIgnoreCase("ItemDrops")) {
			return true;
		} else if (name.equalsIgnoreCase("SafeSword")) {// 3
			return true;
		} else if (name.equalsIgnoreCase("FriendlyFire")) {
			return true;
		} else if (name.equalsIgnoreCase("InstantTnT")) {// 4
			return true;
		} else if (name.equalsIgnoreCase("Fallschaden")) {// 5
			return true;
		} else if (name.equalsIgnoreCase("NoRegeneration")) {
			return true;
		} else if (name.equalsIgnoreCase("arrow")) {
			return true;
		} else if (name.equalsIgnoreCase("Crafting")) {
			return true;
		} else if (name.equalsIgnoreCase("Building")) {// 6
			return true;
		} else {
			return false;
		}

	}

}
