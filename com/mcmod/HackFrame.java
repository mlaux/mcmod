package com.mcmod;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mcmod.api.Inventory;
import com.mcmod.api.InventoryItem;
import com.mcmod.api.Minecraft;

public class HackFrame extends JFrame {
	private Minecraft minecraft;
	private JLabel label = new JLabel("Spawn: ");
	private JLabel lID = new JLabel("Get ID: ");
	private JLabel lID2 = new JLabel("");
	private JTextField field;
	private JTextField fID;
	
	class InventoryItemData {
		public int slot;
		public InventoryItem item;
	}
	
	public HackFrame(Minecraft mc) {
		super("McModVille");
		
		this.minecraft = mc;
		
		field = new JTextField(3);
		fID = new JTextField(3);
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel pID = new JPanel(new BorderLayout());
		
		pID.add(lID, BorderLayout.WEST);
		pID.add(lID2, BorderLayout.EAST);
		pID.add(fID, BorderLayout.CENTER);
		
		panel.add(label, BorderLayout.WEST);
		panel.add(field, BorderLayout.EAST);
		
		JButton button = new JButton("SPAWN");
		JButton bID = new JButton("Get ID");
		
		add(panel, BorderLayout.NORTH);
		add(pID, BorderLayout.CENTER);
		

		JPanel buttons = new JPanel(new BorderLayout());
		buttons.add(button, BorderLayout.WEST);
		final JCheckBox cbUnlimitedItems = new JCheckBox("Unlimited Items");
		buttons.add(cbUnlimitedItems, BorderLayout.EAST);
		
		add(buttons, BorderLayout.SOUTH);
		
		final Thread thread = new Thread() {
			public void run() {
				while(true) {
					if(cbUnlimitedItems.isSelected()) {
						Inventory i = minecraft.getPlayer().getInventory();
						
						for(InventoryItem item : i.getInventoryItems()) {
							if(item.getCount() < 64) {
								item.setCount(64);
							}
						}
						
						for(InventoryItem item : i.getEquippableItems()) {
							if(item.getDamage() > 0) {
								System.out.println(item.getDamage());
								item.setDamage(0);
							}
						}
					}
					
					try {
						Thread.sleep(200);
					} catch(Exception e) {}
				}
			}
		};
		thread.start();
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = field.getText().toLowerCase();
				
				if(s.length() > 0) {
					try {
						minecraft.getPlayer().getInventory().addItem(Integer.parseInt(field.getText()), 64);
						field.setText("");
						System.out.println("Tried lol...");
					} catch(Exception xe) {
						xe.printStackTrace();
					}
				}
			}
		});
	}
}
