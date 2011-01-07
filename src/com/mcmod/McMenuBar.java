package com.mcmod;

import java.awt.AWTException;
import java.awt.AWTKeyStroke;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;

import com.mcmod.api.InventoryAPI;
import com.mcmod.debug.LocationDebug;
import com.mcmod.debug.McDebug;
import com.mcmod.debug.WorldDebug;
import com.mcmod.inter.Humanoid;
import com.mcmod.inter.Item;
import com.mcmod.inter.World;

public class McMenuBar extends JMenuBar implements ActionListener {
	private boolean manageTime = false;
	private Robot r = null; // LOLOLOLOL
	
	private Thread timeThread = new Thread() {
		@Override
		public void run() {
			while(manageTime) {
				// I can't wait to get Mod thing working lmfao.
				
				World world = Loader.getMinecraft().getWorld();
				
				if(world != null) {
					long time = world.getTime();
					
					if((time % 24000) > 11000) {
						world.setTime(time + 14000);
					}
				}
				
				try {
					Thread.sleep(5000);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	public McMenuBar() {
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // LOLOLOLOL
		JMenu menu = new JMenu("Cheats");
		menu.add(createItem("Item Spawner...", "spawner"));
		menu.add(createItem("Time Manager", "time"));
		add(menu);
		
		JMenu debug = new JMenu("Debug");
		debug.add(createItem("Location", "location"));
		debug.add(createItem("World", "world"));
		debug.add(createItem("Player", "player"));
		add(debug);
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
		} else if(cmd.equals("time")) {
			if(!manageTime) {
				manageTime = true;
				timeThread.start();
			} else {
				manageTime = false;
				try {
					timeThread.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} else if(cmd.equals("location")) {
			McDebug debug = new LocationDebug();
			if(Loader.containsDebug(debug)) {
				Loader.removeDebug(debug);
			} else {
				Loader.addDebug(debug);
			}
		} else if(cmd.equals("world")) {
			McDebug debug = new WorldDebug();
			if(Loader.containsDebug(debug)) {
				Loader.removeDebug(debug);
			} else {
				Loader.addDebug(debug);
			}
		} else if(cmd.equals("player")) {
			String name = JOptionPane.showInputDialog("Name");
			
			if(name != null) {
				World w = Loader.getMinecraft().getWorld();
				
				if(w != null) {
					List<Humanoid> playerList = (List<Humanoid>) w.getPlayerList();
					
					if(playerList != null) {
						for(Humanoid h : playerList) {
							System.out.println("Player: " + h.getName());
							if(h.getName().equalsIgnoreCase(name)) {
								try {Thread.sleep(3000); } catch(Exception e1) {}
								System.out.println("Sending");
								r.keyPress(KeyEvent.VK_ESCAPE);
								try {Thread.sleep(100); } catch(Exception e1) {}
								r.keyRelease(KeyEvent.VK_ESCAPE);

								try {Thread.sleep(1000); } catch(Exception e1) {}
								
								sendString("Sup, " + h.getName() + ".  I see you're chillin at (" + ((int) h.getX()) + ", " + ((int) h.getY()) + ", " + ((int) h.getZ()));
								sendString("Camera Rotation: [" + h.getRotationX() + ", " + h.getRotationY() + "]");
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private void sendKey(char c) {
		AWTKeyStroke stroke = AWTKeyStroke.getAWTKeyStroke(c);
		
		r.keyPress((int)c);
		try { Thread.sleep(100); } catch(Exception e) {}
		r.keyRelease((int)c);
	}
	
	private void sendString(String s) {
		r.keyPress(KeyEvent.VK_ENTER);
		try { Thread.sleep(100); } catch(Exception e) {}
		r.keyRelease(KeyEvent.VK_ENTER);
		
		for(char c : s.toCharArray()) {
			sendKey(c);
		}
		

		r.keyPress(KeyEvent.VK_ENTER);
		try { Thread.sleep(100); } catch(Exception e) {}
		r.keyRelease(KeyEvent.VK_ENTER);
	}
}
