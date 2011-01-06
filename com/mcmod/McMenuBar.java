package com.mcmod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.mcmod.api.InventoryAPI;
import com.mcmod.inter.Item;

public class McMenuBar extends JMenuBar implements ActionListener {
	public McMenuBar() {
		JMenu menu = new JMenu("Cheats");
		menu.add(createItem("Item Spawner...", "spawner"));
		menu.add(createItem("Test Health", "healthtest"));
		menu.add(createItem("Hurt Test", "hurttest"));
		menu.add(createItem("Attack Time", "attacktest"));
		menu.add(createItem("Death Time", "deathtest"));
		add(menu);
	}
	
	private JMenuItem createItem(String text, String cmd) {
		JMenuItem it = new JMenuItem(text);
		it.addActionListener(this);
		it.setActionCommand(cmd);
		return it;
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("spawner")) {
			String item = JOptionPane.showInputDialog("Item ID");	
			if(item.length() > 0) {
				Item[] cache = Loader.getMinecraft().getItemCache();
				Item i = null;
				try {
					int id = Integer.parseInt(item);
					i = cache[id];
				} catch(NumberFormatException ex) {
					for(int x = 0; x < cache.length; x++) {
						if(cache[x] != null) {
							String name = cache[x].getName();
							
							if(name != null) {
								if(name.toLowerCase().contains(item.toLowerCase())) {
									i = cache[x];
									break;
								}
							}
						}
					}
				}
				

				InventoryAPI.addItem(Loader.getMinecraft().getPlayer().getInventory(), i.getID(), 64);
			}
		} else if(cmd.equals("healthtest")) {
			System.out.println(Loader.getMinecraft().getPlayer().getHealth());
		} else if(cmd.equals("hurttest")) {
	//		System.out.println(Loader.getMinecraft().getPlayer().getHurtTime());
		} else if(cmd.equals("attacktest")) {
		//	System.out.println(Loader.getMinecraft().getPlayer().getAttackTime());
		} else if(cmd.equals("deathtest")) {
		//	System.out.println(Loader.getMinecraft().getPlayer().getDeathTime());
		}
	}
}
