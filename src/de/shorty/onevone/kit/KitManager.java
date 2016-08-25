package de.shorty.onevone.kit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.shorty.onevone.OneVOne;

public class KitManager {

	OneVOne onevone;
	List<Player> kitedit = new ArrayList<>();
	List<Player> kitview = new ArrayList<>();
	
	public HashMap<String, List<ItemStack>> kit = new HashMap<>();
	public HashMap<String, ItemStack> Helmet = new HashMap<>();
	public HashMap<String, ItemStack> Chestplate = new HashMap<>();
	public HashMap<String, ItemStack> Leggings = new HashMap<>();
	public HashMap<String, ItemStack> Boots = new HashMap<>();
	public HashMap<String, String> sett = new HashMap<>();
	public HashMap<String, String> type = new HashMap<>();
	public HashMap<String, String> kits = new HashMap<>();
	public HashMap<String, String> armor = new HashMap<>();
	
	public KitManager(OneVOne onevone) {		
		this.onevone = onevone;
	}

	public void createKit(String name, String kitcreator){
		if(onevone.mysql.isConnected()){
			onevone.mysql.update("INSERT INTO c1vs1_Kits(KitName, KitCreator, Type, Bewertung, Kit, Armor, Settings) VALUES " + "('"
				+ name + "', '" + kitcreator + "', 'fw', '100', '', '', '');");
		}else{
			Bukkit.getConsoleSender().sendMessage(onevone.getPrefix() + "§cMySQL is not connected, cannot create kits!");
		}
	}
	
	public boolean isAvailable(String kitname){
		return kits.containsKey(kitname.toLowerCase());
	}
	
	public boolean exists(String kitname){
		try {
			ResultSet rs = onevone.mysql.getResult("SELECT * FROM c1vs1_Kits WHERE KitName= '" + kitname + "'");

			if (rs.next()) {
				return rs.getString("KitName") != null;
			}
			rs.close();
			return false;
		} catch (SQLException e) {

		}

		return false;
	}
	
	public void setKitEditing(Player player){
		kitedit.add(player);
	}
	public boolean isEditing(Player player){
		return kitedit.contains(player);
	}
	public void removeFromEditing(Player player){
		kitedit.remove(player);
	}
	public void setViewing(Player player){
		kitview.add(player);
	}
	public boolean isViewing(Player player){
		return kitview.contains(player);
	}
	public void removeViewing(Player player){
		kitview.remove(player);
	}
}
