package de.shorty.onevone.manager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Layout {

	Player p;
	String title;
	int size;
	Inventory inv;
	
	public Layout(Player p, String title, int size) {

		this.p = p;
		this.title = title;
		this.size = size;
		this.inv = Bukkit.createInventory(null, size, title);

	}
	public void setFirstLine(List<ItemStack> items){
		
		for(int i = 0; i < items.size(); i++){
			
			this.inv.setItem(i, items.get(i));
			
		}
		
	}
	public void setSecondLine(List<ItemStack> items){
		
		for(int i = 0; i < items.size(); i++){
			
			this.inv.setItem(i+9, items.get(i));
			
		}
		
	}
	public void setThirdLine(List<ItemStack> items){
		
		for(int i = 0; i < items.size(); i++){
			
			this.inv.setItem(i+18, items.get(i));
			
		}
		
	}
	public void setFourthLine(List<ItemStack> items){
		
		for(int i = 0; i < items.size(); i++){
			
			this.inv.setItem(i+27, items.get(i));
			
		}
		
	}
	public void setFifthLine(List<ItemStack> items){
		
		for(int i = 0; i < items.size(); i++){
			
			this.inv.setItem(i+36, items.get(i));
			
		}
		
	}
	public void setSixthLine(List<ItemStack> items){
		
		for(int i = 0; i < items.size(); i++){
			
			this.inv.setItem(i+45, items.get(i));
			
		}
		
	}
	public void open(Player p){
		p.openInventory(inv);
	}
}
