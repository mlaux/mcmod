package com.mcmod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.mcmod.api.InventoryAPI;

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
				try {
					int id = Integer.parseInt(item);
					InventoryAPI.addItem(Loader.getMinecraft().getPlayer().getInventory(), id, 64);
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Enter an item ID.", "Item Spawn Error", JOptionPane.ERROR_MESSAGE);
				}
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
