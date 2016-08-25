package de.shorty.onevone.stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;
import de.shorty.onevone.kit.Kit;

public class StatsManager {

	OneVOne onevone;

	HashMap<String, Integer> kills = new HashMap<>();
	HashMap<String, Integer> deaths = new HashMap<>();
	HashMap<String, Integer> games = new HashMap<>();
	HashMap<String, Integer> wins = new HashMap<>();
	HashMap<String, Integer> points = new HashMap<>();

	HashMap<String, String> type = new HashMap<>();
	HashMap<String, Kit> currentkit = new HashMap<>();
	HashMap<String, Integer> bew = new HashMap<>();
	HashMap<String, Double> kd = new HashMap<>();

	public StatsManager(OneVOne onevone) {
		this.onevone = onevone;
	}

	public void createTables() {

		onevone.mysql.update(
				"CREATE TABLE IF NOT EXISTS c1vs1_Users(Name VARCHAR(16), UUID VARCHAR(64), CurrentKit VARCHAR(50), Search VARCHAR(1000), Points int, Played int, Wins int, Kills int, Deaths int, KitNames VARCHAR(1000));");
		onevone.mysql.update(
				"CREATE TABLE IF NOT EXISTS c1vs1_Kits(KitName VARCHAR(16), KitCreator VARCHAR(64), Type VARCHAR(100), Bewertung int, Kit VARCHAR(10000), Armor VARCHAR(10000), Settings VARCHAR(10000));");

	}

	public void createPlayer(Player player) {

		if (!existPlayer(player.getUniqueId().toString())) {

			onevone.mysql
					.update("INSERT INTO c1vs1_Users(Name, UUID, CurrentKit, Search, Points, Played, Wins, Kills, Deaths, KitNames) VALUES "
							+ "('" + player.getName() + "', '" + player.getUniqueId().toString() + "', '"
							+ player.getName() + "', 'DC;0.0;100', '"
							+ onevone.getConfig().getInt("Stats.startingpoints") + "', '0', '0', '0', '0', '"
							+ player.getName() + ";');");
			onevone.kitmanager.createKit(player.getName(), player.getUniqueId().toString());

		} else {
			onevone.mysql.update("UPDATE c1vs1_Users SET Name='" + player.getName() + "' WHERE UUID='"
					+ player.getUniqueId().toString() + "'");
		}

	}

	public void loadPlayer(Player player) {

		kills.put(player.getName(), (int) get(player.getUniqueId().toString(), "UUID", "Kills", "c1vs1_Users"));
		deaths.put(player.getName(), (int) get(player.getUniqueId().toString(), "UUID", "Deaths", "c1vs1_Users"));
		games.put(player.getName(), (int) get(player.getUniqueId().toString(), "UUID", "Played", "c1vs1_Users"));
		wins.put(player.getName(), (int) get(player.getUniqueId().toString(), "UUID", "Wins", "c1vs1_Users"));
		points.put(player.getName(), (int) get(player.getUniqueId().toString(), "UUID", "Points", "c1vs1_Users"));
		
		Object searchraw = get(player.getUniqueId().toString(), "UUID", "Search", "c1vs1_Users");
		String search = String.valueOf(searchraw);
		String[] s = search.split(";");

		type.put(player.getName(), s[0]);
		bew.put(player.getName(), Integer.parseInt(s[2]));
		kd.put(player.getName(), Double.parseDouble(s[1]));
		
		/* TODO: Kit loading! */

		
		if(onevone.getConfig().getBoolean("Console.debug")){
			Bukkit.getConsoleSender().sendMessage(onevone.getPrefix() + "§aSuccessfully loaded Stats from user §7'§e" + player.getName() + "§7'§a!");
		}
		
	}

	public void uploadPlayer(Player player) {
		if (!existData(player))
			return;
		onevone.mysql.update("UPDATE c1vs1_Users SET Kills= '" + kills.get(player.getName()) + "' WHERE UUID='"
				+ player.getUniqueId().toString() + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET Deaths= '" + deaths.get(player.getName()) + "' WHERE UUID='"
				+ player.getUniqueId().toString() + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET Played= '" + games.get(player.getName()) + "' WHERE UUID='"
				+ player.getUniqueId().toString() + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET Wins= '" + wins.get(player.getName()) + "' WHERE UUID='"
				+ player.getUniqueId().toString() + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET Points= '" + points.get(player.getName()) + "' WHERE UUID='"
				+ player.getUniqueId().toString() + "';");

		String search = "";
		search = search + type.get(player.getName()) + ";";
		search = search + kd.get(player.getName()) + ";";
		search = search + bew.get(player.getName()) + ";";
		onevone.mysql.update(
				"UPDATE c1vs1_Users SET Search='" + search + "' WHERE UUID='" + player.getUniqueId().toString() + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET CurrentKit='" + currentkit.get(player.getName()).getName() + "' WHERE UUID='"
				+ player.getUniqueId().toString() + "';");

		if(onevone.getConfig().getBoolean("Console.debug")){
			Bukkit.getConsoleSender().sendMessage(onevone.getPrefix() + "§aSuccessfully uploaded Stats from user §7'§e" + player.getName() + "§7'§a!");
		}

	}

	/* Stats Methods */

	public boolean existData(Player player) {
		return kills.containsKey(player.getName()) && deaths.containsKey(player.getName())
				&& games.containsKey(player.getName()) && wins.containsKey(player.getName())
				&& points.containsKey(player.getName());
	}

	public Kit getCurrentKit(Player player) {
		return currentkit.get(player.getName());
	}
	
	public double getKD(Player player) {
		return kd.get(player.getName());
	}
	
	public int getBewertung(Player player) {
		return bew.get(player.getName());
	}
	
	public String getSearchType(Player player) {
		return type.get(player.getName());
	}
	
	public void setCurrentKit(Player player, Kit kit) {
		currentkit.put(player.getName(), kit);
	}
	
	public void setKD(Player player, double kd) {
		this.kd.put(player.getName(), kd);
	}
	
	public void setBewertung(Player player, int bewertung) {
		bew.put(player.getName(), bewertung);
	}
	
	public void setSearchType(Player player, String searchtype) {
		type.put(player.getName(), searchtype);
	}
	
	public int getK(Player player) {
		return kills.get(player.getName());
	}

	public int getD(Player player) {
		return deaths.get(player.getName());
	}

	public int getW(Player player) {
		return wins.get(player.getName());
	}

	public int getG(Player player) {
		return games.get(player.getName());
	}

	public int getP(Player player) {
		return points.get(player.getName());
	}

	public void addWin(Player player) {
		points.put(player.getName(), getP(player) + onevone.getConfig().getInt("Stats.pointsforwin"));
		wins.put(player.getName(), getW(player) + 1);
	}

	public void addLose(Player player) {
		int points = getP(player);
		int pointstoremove = onevone.getConfig().getInt("Stats.pointslostonlose");
		if ((points - pointstoremove) > 0) {
			this.points.put(player.getName(), points - pointstoremove);
		}
	}

	public void addGame(Player player) {
		games.put(player.getName(), getG(player) + 1);
		points.put(player.getName(), getP(player) + onevone.getConfig().getInt("Stats.pointsforgame"));
	}

	public void addDeath(Player player) {
		deaths.put(player.getName(), getD(player) + 1);
		int points = getP(player);
		int pointstoremove = onevone.getConfig().getInt("Stats.pointslostondeath");
		if ((points - pointstoremove) > 0) {
			this.points.put(player.getName(), points - pointstoremove);
		}
	}

	public void addKill(Player player) {
		kills.put(player.getName(), getK(player) + 1);
		points.put(player.getName(), getP(player) + onevone.getConfig().getInt("Stats.pointsforkill"));
	}
	
	public void resetStats(Player player){
		points.put(player.getName(), onevone.getConfig().getInt("Stats.startingpoints"));
		games.put(player.getName(), 0);
		wins.put(player.getName(), 0);
		kills.put(player.getName(), 0);
		deaths.put(player.getName(), 0);
	}
	public void resetStats(String player){
		onevone.mysql.update("UPDATE c1vs1_Users SET Kills= '" + 0 + "' WHERE Name='"
				+ player + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET Deaths= '" + 0 + "' WHERE UUID='"
				+ player + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET Played= '" + 0 + "' WHERE UUID='"
				+ player + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET Wins= '" + 0 + "' WHERE UUID='"
				+ player + "';");
		onevone.mysql.update("UPDATE c1vs1_Users SET Points= '" + 0 + "' WHERE UUID='"
				+ player + "';");
	}

	/* Methods to Check */

	public int getTotalPlayers(){
		List<String> list = new ArrayList<>();
		ResultSet rs = onevone.mysql.getResult("SELECT * FROM c1vs1_Users");
		try {
			while (rs.next()) {
				list.add(rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.size();
	}
	
	public String getUUIDbyName(String playername, String database) {
		String i = "";
		try {
			ResultSet rs = onevone.mysql.getResult("SELECT * FROM " + database + " WHERE Name= '" + playername + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("UUID")) == null))
				;

			i = rs.getString("UUID");

		} catch (SQLException e) {

		}
		return i;
	}

	public String getNamebyUUID(String playername, String database) {
		String i = "";
		try {
			ResultSet rs = onevone.mysql.getResult("SELECT * FROM " + database + " WHERE UUID= '" + playername + "'");

			if ((!rs.next()) || (String.valueOf(rs.getString("Name")) == null))
				;

			i = rs.getString("Name");

		} catch (SQLException e) {

		}
		return i;
	}

	public boolean existPlayer(String uuid) {
		try {
			ResultSet rs = onevone.mysql.getResult("SELECT * FROM c1vs1_Users WHERE UUID= '" + uuid + "'");

			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			rs.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean existPlayerName(String name) {
		try {
			ResultSet rs = onevone.mysql.getResult("SELECT * FROM c1vs1_Users WHERE Name= '" + name + "'");

			if (rs.next()) {
				return rs.getString("Name") != null;
			}
			rs.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public Object get(String whereresult, String where, String select, String database) {

		ResultSet rs = onevone.mysql
				.getResult("SELECT " + select + " FROM " + database + " WHERE " + where + "='" + whereresult + "'");
		try {
			while (rs.next()) {
				Object v = rs.getObject(select);
				return v;
			}
		} catch (SQLException e) {
			return "ERROR";
		}

		Bukkit.getConsoleSender().sendMessage("§cCould not load onevonemysql-Stats Type.");
		return "ERROR";
	}

}
