package com.mcmod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class McMenuBar extends JMenuBar implements ActionListener {
	public McMenuBar() {
		JMenu menu = new JMenu("Cheats");
		menu.add(createItem("Item Spawner...", "spawner"));
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
					Loader.getMinecraft().getPlayer().getInventory().addItem(id, 64);
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Enter an item ID.", "Item Spawn Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
