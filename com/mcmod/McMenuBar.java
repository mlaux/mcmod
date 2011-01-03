package com.mcmod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class McMenuBar extends JMenuBar {
	public McMenuBar() {
		setupMenuBar();
	}
	
	public void setupMenuBar() {
		super.add(createCheatMenu());
	}
	
	public JMenu createCheatMenu() {
		JMenu menu = new JMenu("Cheats");
		
		menu.add(createItemSpawner());
		
		return menu;
	}
	
	public JMenuItem createItemSpawner() {
		JMenuItem spawner = new JMenuItem("Item Spawner");
		
		spawner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = JOptionPane.showInputDialog("Item ID");
				
				if(item.length() > 0) {
					try {
						int id = Integer.parseInt(item);
						
						Loader.getMinecraft().getPlayer().getInventory().addItem(id, 64);
					} catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid number format.", "Item Spawn Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		return spawner;
	}
}
