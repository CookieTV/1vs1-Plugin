package de.shorty.onevone.kit;

import java.sql.ResultSet;
 import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.shorty.onevone.ClassManager;

public class Kit {
	
	String name;
	String kitdata;
	String armordata;
	String settings;
	String t;
	
	ClassManager classmanager;
	
	public Kit(String kitname, boolean loadMySQL){
		this.classmanager = ClassManager.getClassManager();
		
		this.name = kitname;
		this.name = this.name.toLowerCase();
		if(loadMySQL){
			
			settings = classmanager.getKitSettings().getRules(kitname);
			kitdata = classmanager.getKitSettings().getKit(kitname);
			armordata = classmanager.getKitSettings().getArmor(kitname);
			t = classmanager.getKitSettings().getType(kitname);
			
		}else{
			
			settings = classmanager.getKitManager().sett.get(kitname.toLowerCase());
			kitdata = classmanager.getKitManager().kits.get(kitname.toLowerCase());
			armordata = classmanager.getKitManager().armor.get(kitname);
			t = classmanager.getKitSettings().getType(kitname);
			
		}
		
	}
	public boolean isCreator(Player p){
		
		String uuid = getCreator();
		return uuid.equals(p.getUniqueId().toString());
		
	}

	public String getCreator(){
	String s = "";
		
		try{
			
			ResultSet rs = classmanager.getMySQL().getResult("SELECT KitCreator FROM c1vs1_Kits WHERE KitName= '"+name+"'");
			
			if((!rs.next()) || (String.valueOf(rs.getString("KitCreator")) == null)){
			}else{
				s = rs.getString("KitCreator");
			}
			
		}catch(Exception e1){
			
		}
		
		return s;
	}
	public String getType(){
		return classmanager.getKitSettings().getType(name);
	}
	public String getRule(){
		
		String rules = "";
		List<String> rule = getRuleList(name);
		if(rule.isEmpty()){
			return "§c✖";
		}
		int count = 0;
		for(String r : rule){
			count ++;
			if(count == 3){
				rules = rules + "\n           ";
			}else if(count == 0 && rule.size() == 1){
				rules = rules + "§6" + r;
			}else if(count == 0 && rule.size() >= 1){
				rules = rules + "§6" + r +"§8, ";
			}else if(count == rule.size()){
				rules = rules + "§6" + r +"";
			}else{
				rules = rules + "§6" + r +"§8, ";
			}
		}

		return rules;
	}
	public String getSettings(){
		return classmanager.getKitManager().sett.get(name);
	}
	public boolean containsSetting(String setting){
		return getRuleList(name).contains(setting);
	}
	public List<String> getRuleList(String kitname){
		
		String s = getSettings();
		String[] arr = s.split(",");
		List<String> list = new ArrayList<>();
		
		for(int i = 0; i < arr.length; i++){
			list.add(arr[i]);
		}
		return list;
	}
	public void addRule(String rule){
		if(classmanager.getKitSettings().containsInRules(rule)){
			
			String s = getSettings();
			s = s+rule+",";
			classmanager.getKitManager().sett.put(name, s);
		}
	}
	public void removeRule(String rule){
		
		if(classmanager.getKitSettings().containsInRules(rule)){
			
			String s = getSettings();
			s = s.replace(rule+",", "");
			classmanager.getKitManager().sett.put(name, s);
			
		}else{
			return;
		}
		
	}
	public void fetchData(){
		Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix()+"§7Try fetching kit Data §8"+name+"§7!");
		//Split to 1 Item per Entry
		if(kitdata.equalsIgnoreCase("")){
		List<ItemStack> k1 = new ArrayList<>();
		k1.add(new ItemStack(Material.STONE_SWORD));
		k1.add(new ItemStack(Material.FISHING_ROD));
		k1.add(new ItemStack(Material.BOW));
		k1.add(new ItemStack(Material.AIR));
		k1.add(new ItemStack(Material.AIR));
		k1.add(new ItemStack(Material.AIR));
		k1.add(new ItemStack(Material.ARROW, 8));
		k1.add(new ItemStack(Material.WATER_BUCKET));
		k1.add(new ItemStack(Material.LAVA_BUCKET));
		classmanager.getKitManager().kit.put(name, k1);
		}else{
		String[] k = kitdata.split(";");
		List<String> k1 = new ArrayList<>();
		for(int i = 0; i < k.length; i++){
			k1.add(k[i]);
		}
		classmanager.getKitManager().kit.put(name, ReFetch(k1));
		
			
		}
		//Fetch Armor
		if(armordata.equalsIgnoreCase("")){
			classmanager.getKitManager().Helmet.put(name, new ItemStack(Material.IRON_HELMET));
			classmanager.getKitManager().Chestplate.put(name, new ItemStack(Material.IRON_CHESTPLATE));
			classmanager.getKitManager().Leggings.put(name, new ItemStack(Material.GOLD_LEGGINGS));
			classmanager.getKitManager().Boots.put(name, new ItemStack(Material.GOLD_BOOTS));
		}else{
		String[] a = armordata.split(";");
		
		classmanager.getKitManager().Helmet.put(name, ReFetchArmor(a[0]));
		if(a.length >= 1){
			classmanager.getKitManager().Chestplate.put(name, ReFetchArmor(a[1]));
		}
		if(a.length >= 2){
			classmanager.getKitManager().Leggings.put(name, ReFetchArmor(a[2]));
		}
		if(a.length >= 3){
			classmanager.getKitManager().Boots.put(name, ReFetchArmor(a[3]));
		}
		}
		//Settings
		classmanager.getKitManager().kits.put(name, kitdata);
		classmanager.getKitManager().armor.put(name, armordata);
		classmanager.getKitManager().sett.put(name, settings);
		classmanager.getKitManager().type.put(name, t);
		Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix()+"§aSuccessfully fetched kit with name '"+name+"'!");
	}
	@SuppressWarnings("deprecation")
	private ItemStack ReFetchArmor(String s){

		if(s.equalsIgnoreCase("0")){
			return new ItemStack(Material.AIR);
		}
		//Item Format:    MATERIALID, AMOUNT, SUBID, ITEMNAME, [ENCHANTNAME.LEVEL-ENCHANTNAME.LEVEL]
		String[] item = s.split(",");
		ItemStack i = new ItemStack(Material.getMaterial(Integer.parseInt(item[0])), Integer.parseInt(item[1]));
		addEnchantments(i, item[3]);
		ItemMeta im = i.getItemMeta();
		if(item[2].equalsIgnoreCase("item") == false){
		im.setDisplayName(item[2]);
		}
		i.setItemMeta(im);
		
		return i;
	}
	@SuppressWarnings("deprecation")
	private List<ItemStack> ReFetch(List<String> l){
		List<ItemStack> list = new ArrayList<>();
		for(String s : l){
			if(s.equalsIgnoreCase("0")){
				list.add(new ItemStack(Material.AIR));
			}else{
			//Item Format:    MATERIALID, AMOUNT, SUBID, ITEMNAME, [ENCHANTNAME.LEVEL-ENCHANTNAME.LEVEL]
			String[] item = s.split(",");
			ItemStack i;
			if(Integer.parseInt(item[0]) == 373){
			i = new ItemStack(Material.getMaterial(Integer.parseInt(item[0])), Integer.parseInt(item[1]), (short) getSubID(Integer.parseInt(item[2])));
			}else{
			i = new ItemStack(Material.getMaterial(Integer.parseInt(item[0])), Integer.parseInt(item[1]), (short) Integer.parseInt(item[2]));
			}
			addEnchantments(i, item[4]);
			ItemMeta im = i.getItemMeta();
			if(item[3].equalsIgnoreCase("item") == false){
			im.setDisplayName(item[3]);
			}
			i.setItemMeta(im);
			list.add(i);
			}
		}
		return list;
	}
	@SuppressWarnings("deprecation")
	private void addEnchantments(ItemStack i, String s){
		if(s.equalsIgnoreCase("[]"))return;
		s = s.replace("[", "");
		s = s.replace("]", "");
		String[] arr = s.split("-");
		for(int b = 0; b < arr.length; b++){
			String abc = arr[b];
			String[] emt = abc.split("_");
			int level = 1;
			level = Integer.parseInt(emt[1]);
			int enchant = Integer.parseInt(emt[0]);
			i.addUnsafeEnchantment(Enchantment.getById(enchant), level);
		}
	}
	@SuppressWarnings("deprecation")
	public void upload(){
		//saving inventory
				String kit = "";
				for(int i = 0; i < classmanager.getKitManager().kit.get(name).size(); i++){
					
					ItemStack item = classmanager.getKitManager().kit.get(name).get(i);
					//Item Format:    MATERIALID, AMOUNT, SUBID, ITEMNAME, [ENCHANTNAME.LEVEL-ENCHANTNAME.LEVEL]
					if(item == null || item.getType() == Material.AIR){
						kit = kit + "0;";
					}else{
					String ai = "";
					ai = ai + item.getType().getId()+",";
					ai = ai + item.getAmount() + ",";
					ai = ai + item.getData().getData() +",";
					if(item.hasItemMeta()){
					if(item.getItemMeta().hasDisplayName()){
					ai = ai + item.getItemMeta().getDisplayName() + ",";
					}else{
						ai = ai + "Item,";
					}
					}else{
					ai = ai + "Item,";
					}
					ai = ai + "[";
					//add enchantments
					if(item.getEnchantments() != null && !item.getEnchantments().isEmpty()){
					for(Enchantment e : item.getEnchantments().keySet()){
						
						ai = ai + e.getId() + "_" +item.getEnchantments().get(e)+"-";
						
					}
					}
					ai = ai + "]";
					kit = kit + ai + ";";
					}
				}
				classmanager.getKitSettings().setKit(name, kit);
				
				String armor = "";
				ItemStack h = classmanager.getKitManager().Helmet.get(name);
				ItemStack c = classmanager.getKitManager().Chestplate.get(name);
				ItemStack l = classmanager.getKitManager().Leggings.get(name);
				ItemStack b = classmanager.getKitManager().Boots.get(name);
				
				if(h != null && h.getType() != Material.AIR){
				String helm = "";
				helm =  helm + h.getType().getId() + "," + 1 + ",item,";
				helm = helm + "[";
				if(h.getEnchantments() != null && !h.getEnchantments().isEmpty()){
				for(Enchantment e : h.getEnchantments().keySet()){
					
					helm = helm + e.getId() + "_" +h.getEnchantments().get(e)+"-";
					
				}
				}
				helm = helm + "]";
				armor = armor + helm + ";";
				}else{
					armor = armor + "0;";
				}
				if(c != null && c.getType() != Material.AIR){
				String chestplate = "";
				chestplate =  chestplate + c.getType().getId() + "," + 1 + ",item,";
				chestplate = chestplate + "[";
				if(c.getEnchantments() != null && !c.getEnchantments().isEmpty()){
				for(Enchantment e : c.getEnchantments().keySet()){
					
					chestplate = chestplate + e.getId() + "_" +c.getEnchantments().get(e)+"-";
					
				}
				}
				chestplate = chestplate + "]";
				armor = armor + chestplate + ";";
				}else{
					armor = armor + "0;";
				}
				if(l != null && l.getType() != Material.AIR){
				String leggings = "";
				leggings =  leggings + l.getType().getId() + "," + 1 + ",item,";
				leggings = leggings + "[";
				if(l.getEnchantments() != null && !l.getEnchantments().isEmpty()){
				for(Enchantment e : l.getEnchantments().keySet()){
					
					leggings = leggings + e.getId() + "_" +l.getEnchantments().get(e)+"-";
					
				}
				}
				leggings = leggings + "]";
				armor = armor + leggings + ";";
				}else{
					armor = armor + "0;";
				}
				if(b != null && b.getType() != Material.AIR){
				String boots = "";
				boots =  boots + b.getType().getId() + "," + 1 + ",item,";
				boots = boots + "[";
				
				if(b.getEnchantments() != null && !b.getEnchantments().isEmpty()){
				for(Enchantment e : b.getEnchantments().keySet()){
					
					boots = boots + e.getId() + "_" +b.getEnchantments().get(e)+"-";
					
				}
				}
				boots = boots + "]";
				armor = armor + boots + ";";
				}else{
					armor = armor + "0;";
				}
				classmanager.getKitSettings().setArmor(name, armor);
				classmanager.getKitSettings().setSettings(name, classmanager.getKitManager().sett.get(name));
				Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix()+"§aSuccessfully uploaded kit with name '"+name+"'!");
	}
	private int getSubID(int  data) {
		
		if(data == 45){
			return 16429;
		}else if(data == 77){
			return 16461;
		}else if(data == 46){
			return 16430;
		}else if(data == 78){
			return 16462;
		}else if(data == 74){
			return 16458;
		}else if(data == 41){
			return 16425;
		}else if(data == 73){
			return 16457;
		}else if(data == 42){
			return 16426;
		}else if(data == 11){
			return 16395;
		}else if(data == 43){
			return 16427;
		}else if(data == 75){
			return 16459;
		}else if(data == 76){
			return 16460;
		}else if(data == 44){
			return 16428;
		}else if(data == 37){
			return 16421;
		}else if(data == 69){
			return 16453;
		}else if(data == 36){
			return 16420;
		}else if(data == 68){
			return 16452;
		}else if(data == 38){
			return 16422;
		}else if(data == 70){
			return 16454;
		}else if(data == 40){
			return 16424;
		}else if(data == 72){
			return 16456;
		}else if(data == 9){
			return 16393;
		}else if(data == 4){
			return 16388;
		}else if(data == 35){
			return 16419;
		}else if(data == 67){
			return 16451;
		}else if(data == 66){
			return 16450;
		}else if(data == 34){
			return 16418;
		}else if(data == 65){
			return 16449;
		}else if(data == 33){
			return 16417;
		}else if(data == 74){
			return 16385;
		}else if(data == 2){
			return 16386;
		}
		
		return 0;
	}
	public void save(Player p){
		//saving inventory
		List<ItemStack> list = classmanager.getKitManager().kit.get(name);
		list.clear();
		for(int i = 0; i < p.getInventory().getContents().length; i++){
			
			ItemStack item = p.getInventory().getItem(i);
			//Item Format:    MATERIALID, AMOUNT, SUBID, ITEMNAME, [ENCHANTNAME.LEVEL-ENCHANTNAME.LEVEL]
			if(item == null){
				list.add(new ItemStack(Material.AIR));
			}else{
				list.add(item);
			}
		}

		ItemStack h = p.getInventory().getHelmet();
		ItemStack c = p.getInventory().getChestplate();
		ItemStack l = p.getInventory().getLeggings();
		ItemStack b = p.getInventory().getBoots();
		
		classmanager.getKitManager().kit.put(name, list);
		classmanager.getKitManager().Helmet.put(name, h);
		classmanager.getKitManager().Chestplate.put(name, c);
		classmanager.getKitManager().Leggings.put(name, l);
		classmanager.getKitManager().Boots.put(name, b);
		Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix()+"§aSuccessfully saved kit with name '"+name+"'!");
		p.sendMessage(classmanager.getLanguageString("messages.kitsaved", p).replace("%KIT%", getName()));
	}
	public void set(Player p){
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		int i = 0;
		if(classmanager.getKitManager().kit.get(name).size() == 0){
			p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
			p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
			p.getInventory().addItem(new ItemStack(Material.BOW));
			p.getInventory().addItem(new ItemStack(Material.AIR));
			p.getInventory().addItem(new ItemStack(Material.AIR));
			p.getInventory().addItem(new ItemStack(Material.AIR));
			p.getInventory().addItem(new ItemStack(Material.ARROW, 8));
			p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
			p.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
			p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
			p.updateInventory();
		}else{
		for(ItemStack item : classmanager.getKitManager().kit.get(name)){
			p.getInventory().setItem(i, item);
			i++;
		}
		p.updateInventory();
		p.getInventory().setHelmet(classmanager.getKitManager().Helmet.get(name));
		p.getInventory().setChestplate(classmanager.getKitManager().Chestplate.get(name));
		p.getInventory().setLeggings(classmanager.getKitManager().Leggings.get(name));
		p.getInventory().setBoots(classmanager.getKitManager().Boots.get(name));
		}


	}
	public String getName(){
	String s = "";
		
		try{
			
			ResultSet rs = classmanager.getMySQL().getResult("SELECT KitName FROM c1vs1_Kits WHERE KitName= '" + name + "'");
			
			if((!rs.next()) || (String.valueOf(rs.getString("KitName")) == null)){
			}else{
				s = rs.getString("KitName");
			}
			
		}catch(Exception e1){
			
		}
		
		return s;
	}
	
}
