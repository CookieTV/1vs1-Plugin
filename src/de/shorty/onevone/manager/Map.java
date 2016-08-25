package de.shorty.onevone.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.shorty.onevone.ClassManager;
import de.shorty.onevone.OneVOne;

public class Map {

	String map;
	private HashMap<String, HashMap<Location, String>> reseter = new HashMap<>();
	private static List<String> ingame = new ArrayList<>();
	Location l1;
	Location l2;
	boolean regen;
	int count;
	int currentcount;
	ClassManager classmanager;

	public Map(String name) {
		this.map = name;

		this.classmanager = ClassManager.getClassManager();
	}

	public Map(boolean random) {
		if(random){
			map = getRandomMap();
		}
		this.classmanager = ClassManager.getClassManager();
	}

	public void create() {
		FileConfiguration cfg = classmanager.getCookieAPI().getConfiguration("arena.yml", "1vs1");
		FileConfiguration cfg1 = classmanager.getCookieAPI().getConfiguration("config.yml", "1vs1");
		List<String> maps = cfg1.getStringList("1vs1.Maps");
		maps.add(map.toLowerCase());
		cfg1.set("1vs1.Maps", maps);
		cfg.set(map.toLowerCase() + ".displayname", map.toLowerCase());
		try {
			cfg.save(classmanager.getCookieAPI().getFile("arena.yml", "1vs1"));
			cfg1.save(classmanager.getCookieAPI().getFile("config.yml", "1vs1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(){
		
		FileConfiguration cfg = classmanager.getCookieAPI().getConfiguration("arena.yml", "1vs1");
		FileConfiguration cfg1 = classmanager.getCookieAPI().getConfiguration("config.yml", "1vs1");
		List<String> maps = OneVOne.canbeused;
		maps.remove(map.toLowerCase());
		cfg1.set("1vs1.Maps", maps);
		cfg.set(map.toLowerCase(), null);
		try {
			cfg.save(classmanager.getCookieAPI().getFile("arena.yml", "1vs1"));
			cfg1.save(classmanager.getCookieAPI().getFile("config.yml", "1vs1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean exists(){
		return classmanager.getCookieAPI().getConfiguration("config.yml", "1vs1").getStringList("1vs1.Maps").contains(map.toLowerCase());
	}
	
	public String getRandomMap() {
		try{
		boolean found = false;
		List<String> maps = OneVOne.canbeused;
		int trys = 0;
		while (found == false) {
			trys++;
			Random r = new Random();
			int rn = r.nextInt(maps.size());
			if (trys >= 15) {
				return getFirstFree();
			}
			if (maps.get(rn) == null) {

			} else {
				if (isUsed(maps.get(rn)) == false) {
					found = true;
					return maps.get(rn);
				} else if (trys >= 10) {
					found = true;
					return getFirstFree();
				}
			}
		}
		}catch(Exception e){
			Bukkit.getConsoleSender().sendMessage("§cERROR WHILE CHOOSING RANDOM MAP:");
			e.printStackTrace();
			return getFirstFree();
		}
		return "NoMap";
	}

	public String getFirstFree() {
		List<String> maps = OneVOne.canbeused;
		for (String s : maps) {

			if (isUsed(s) == false) {
				return s;
			} else {
				continue;
			}

		}
		return "NoMap";
	}

	public String getName() {
		return map;
	}

	public static boolean isUsed(String map) {
		return ingame.contains(map.toLowerCase());
	}

	public void add() {
		ingame.add(map.toLowerCase());
	}

	public void remove() {
		ingame.remove(map.toLowerCase());
	}

	public Location getStart1() {
		return classmanager.getS1().get(map.toLowerCase());
	}

	public Location getStart2() {
		return classmanager.getS2().get(map.toLowerCase());
	}

	public Location getMiddle() {
		return classmanager.getMiddle().get(map.toLowerCase());
	}

	public boolean isRegen() {
		return regen;
	}
	
	public boolean containsLocation(Location location){
		if (l1 == null & l2 == null) {
			l1 = classmanager.getP1().get(map.toLowerCase());
			l2 = classmanager.getP2().get(map.toLowerCase());
		}
		Cuboid c = new Cuboid(l1, l2);
		return c.contains(location);
	}

	@SuppressWarnings("deprecation")
	public void save() {
		if (l1 == null & l2 == null) {
			l1 = classmanager.getP1().get(map.toLowerCase());
			l2 = classmanager.getP2().get(map.toLowerCase());
		}
		Cuboid c = new Cuboid(l1, l2);
		Bukkit.getConsoleSender()
				.sendMessage(classmanager.getPrefix() + "§aSuccessfully saved " + c.getVolume() + " Blocks!");

		HashMap<Location, String> res = new HashMap<>();

		for (Block b : c.getBlocks()) {
			String blockdata = b.getTypeId() + ":" + b.getData();
			res.put(b.getLocation(), blockdata);
		}
		reseter.put(map.toLowerCase(), res);
	}

	@SuppressWarnings("deprecation")
	public void regenerate() {
		if (reseter.containsKey(map.toLowerCase()) == false) {
			Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix() + "§cNo blocks to regenerate!");
			return;
		}
		final HashMap<Location, String> res = reseter.get(map.toLowerCase());

		if (res.isEmpty()) {
			this.regen = false;
			Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix() + "§cNo blocks to regenerate!");
			return;
		}
		count = 0;
		currentcount = 0;
		final List<Location> loclist = new ArrayList<>();
		for (Location l : res.keySet()) {
			loclist.add(l);
		}
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(int i = currentcount; i < res.size(); i++){
					regen = true;
					count++;
					currentcount++;
					if(count >= 75000){
						Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix() + "§cRegenerated §3" + count + "§c Blocks! Waiting for regenerate new blocks 1 second...");
						count = 0;
						return;
					}
					Location location = loclist.get(i);
					String[] b = res.get(location).split(":");
					location.getBlock().setTypeId(Integer.parseInt(b[0]));
					location.getBlock().setData(Byte.parseByte(b[1]));
				}
				if(currentcount == res.size()){
					cancel();
					regen = false;
					Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix() + "§cRegeneration finished: §3" + currentcount + " §cBlocks regenerated");
				}
			}
		}.runTaskTimer(classmanager.getInstance(), 0, 20L);
		/*for (Location l : res.keySet()) {
			count++;
			if(count >= 50000){
				try {
					Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix() + "§cTry waiting for regenerate new blocks!");
					wait(20L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count = 0;
			}
			String[] b = res.get(l).split(":");
			l.getBlock().setTypeId(Integer.parseInt(b[0]));
			l.getBlock().setData(Byte.parseByte(b[1]));
			this.regen = true;
		}*/
		Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix() + "§aSuccessfully regenerated Blocks!");
	}

	public void clearDrops(Player p) {
		for (Entity e : p.getNearbyEntities(50, 50, 50)) {
			if (e instanceof Player == false) {
				try {
					if (e.getCustomName().equalsIgnoreCase("§8► §2§lKit-Einstellungen §8◄")
							|| e.getCustomName().equalsIgnoreCase("§8► §2§lWarteschlange §8◄")) {

						Bukkit.getConsoleSender().sendMessage(classmanager.getPrefix() + "§cThis entity cannot be removed!");
					} else {

						e.remove();
					}
				} catch (Exception e1) {
					e.remove();
				}
			}
		}
	}
}
