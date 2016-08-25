package de.shorty.onevone.manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.shorty.onevone.OneVOne;

public class RequestManager {

	// TODO: Language

	public HashMap<String, String> requests = new HashMap<>();
	public HashMap<String, String> requested = new HashMap<>();

	OneVOne onevone;

	public RequestManager(OneVOne onevone) {
		this.onevone = onevone;
	}

	public void sendRequest(Player from, Player to) {

		add(from);
		add(to);
		if (Requested(from).contains(to.getName())) {
			if(onevone.isGerman()){
				from.sendMessage(onevone.getPrefix() + "§cDu hast diesen Spieler bereits herausgefordert!");
			}else{
				from.sendMessage(onevone.getPrefix() + "§cYou already send that player a request!");
			}
			from.playSound(to.getLocation(), Sound.ITEM_BREAK, 5000000, 5000000);
			return;
		}
		if (Requests(to).size() >= 3) {
			if(onevone.isGerman()){
				from.sendMessage(onevone.getPrefix() + "§cDer Spieler hat zu viele Anfragen, bitte gedulde dich!");
			}else{
				from.sendMessage(onevone.getPrefix() + "§cThat player has too much requests!");
			}
			from.playSound(to.getLocation(), Sound.ITEM_BREAK, 5000000, 5000000);
			return;
		}
		if (onevone.matchmaking.contains(to)) {
			if(onevone.isGerman()){
				from.sendMessage(onevone.getPrefix() + "§cDieser Spieler ist in der Warteschlange!");
			}else{
				from.sendMessage(onevone.getPrefix() + "§cThat player is in the Match-Queue!");
			}
			from.playSound(to.getLocation(), Sound.ITEM_BREAK, 5000000, 5000000);
			return;
		}
		if (onevone.matchmaking.contains(from)) {
			if(onevone.isGerman()){
				from.sendMessage(onevone.getPrefix() + "§cDu bist bereits in einer Warteschlange!");
			}else{
				from.sendMessage(onevone.getPrefix() + "§cYou cant send a request while searching for a match!");
			}
			from.playSound(to.getLocation(), Sound.ITEM_BREAK, 5000000, 5000000);
			return;
		}
		addRequestD(from, to);
		addRequestS(to, from);
		String challenged = onevone.getLanguageString("messages.challenged", null);
		challenged = challenged.replace("%CHALLENGER%", from.getName());
		challenged = challenged.replace("%KIT%", onevone.stats.getCurrentKit(from).getName());
		to.sendMessage(challenged);
		if (onevone.getLanguageBoolean("messages.sendacceptdenymessage")) {
			onevone.sendAcceptDenyMessage(to, from);
		}
		String challenger = onevone.getLanguageString("messages.challengedsomebody", null);
		challenger = challenger.replace("%CHALLENGED%", to.getName());
		challenger = challenger.replace("%KIT%", onevone.stats.getCurrentKit(from).getName());
		from.sendMessage(challenger);

		to.playSound(to.getLocation(), Sound.CAT_MEOW, 5000000, 5000000);
		from.playSound(from.getLocation(), Sound.LEVEL_UP, 5000000, 5000000);
		
		onevone.boardmanager.updateScoreboard(from);
		onevone.boardmanager.updateScoreboard(to);
	}

	public void addRequestD(Player player, Player player2) {
		String list = STRRequested(player);
		list = list + player2.getName() + ",";
		requested.put(player.getName(), list);
	}

	public void addRequestS(Player player, Player player2) {
		String list = STRRequests(player);
		list = list + player2.getName() + ",";
		requests.put(player.getName(), list);
	}

	public void removeRequest(Player player, Player from) {
		String list = STRRequests(player);
		list = list.replace(from.getName() + ",", "");
		requests.put(player.getName(), list);
		onevone.boardmanager.updateScoreboard(player);
	}

	public void removeRequester(Player from, Player player) {
		String list = STRRequested(from);
		list = list.replace(player.getName() + ",", "");
		requested.put(from.getName(), list);
		onevone.boardmanager.updateScoreboard(from);
	}

	public ArrayList<String> Requests(Player player) {

		String list = STRRequests(player);
		if (list.equalsIgnoreCase("")) {
			ArrayList<String> l = new ArrayList<>();
			return l;
		}
		String[] arr = list.split(",");
		ArrayList<String> l = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			l.add(arr[i]);
		}

		return l;
	}

	public ArrayList<String> Requested(Player player) {
		String list = STRRequested(player);
		if (list.equalsIgnoreCase("")) {
			ArrayList<String> l = new ArrayList<>();
			return l;
		}
		String[] arr = list.split(",");
		ArrayList<String> l = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			l.add(arr[i]);
		}
		return l;
	}

	public String STRRequests(Player player) {
		return requests.get(player.getName());
	}

	public String STRRequested(Player player) {
		return requested.get(player.getName());
	}

	public void add(Player player) {
		if (requests.containsKey(player.getName()) == false) {
			requests.put(player.getName(), "");
		}
		if (requested.containsKey(player.getName()) == false) {
			requested.put(player.getName(), "");
		}
	}

	public boolean contains(Player player) {
		return requests.containsKey(player.getName());
	}

	public boolean containser(Player player) {
		return requested.containsKey(player.getName());
	}

	public void cancel(Player player) {

		if (requested.containsKey(player.getName())) {

			for (String s : Requested(player)) {

				Player player2 = Bukkit.getPlayer(s);
				removeRequest(player2, player);
				removeRequester(player, player2);
				onevone.boardmanager.updateScoreboard(player2);
			}

		}
		if (requests.containsKey(player.getName())) {

			for (String s : Requests(player)) {

				Player player2 = Bukkit.getPlayer(s);
				removeRequest(player, player2);
				removeRequester(player2, player);
				onevone.boardmanager.updateScoreboard(player2);
			}

		}
		onevone.boardmanager.updateScoreboard(player);
	}
}
