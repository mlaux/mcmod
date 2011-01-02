package com.mcmod;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.mcmod.api.Inventory;
import com.mcmod.api.InventoryItem;
import com.mcmod.api.Minecraft;

/**
 * Currently, this just opens up offline mode. 
 * We can add a login form and stuff later, but I decided
 * for the time being to just skip it.
 * 
 * @author Nicholas Bailey
 */
public class Loader extends JFrame {
	public static URLClassLoader classLoader;
	private Minecraft minecraft;
	
	
	public static void main(String[] args) {
		File dir = Util.getWorkingDirectory("minecraft");
		
		Loader loader = new Loader(new File(dir, "bin").getAbsolutePath());
		loader.setVisible(true);
	}
	
	public Loader(String applicationBase) {
		super("Minecraft - McModded");
		System.setProperty("org.lwjgl.librarypath", applicationBase + "/natives");
		System.setProperty("net.java.games.input.librarypath", applicationBase + "/natives");
		
		try {
			classLoader = new URLClassLoader(getDependentJars(applicationBase));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		
		setupFrame();
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Cheats");
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem("Infinite Items");
		JMenuItem spawn = new JMenuItem("Spawn Item");
		
		new Thread() {
			public void run() {
				while(true) {
					if(item.isSelected()) {
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
		}.start();
		
		spawn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = JOptionPane.showInputDialog("Item ID: ");
			
				try {
					minecraft.getPlayer().getInventory().addItem(Integer.parseInt(s), 255);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		menu.add(item);
		menu.add(spawn);
		bar.add(menu);
		
		super.setJMenuBar(bar);
		
		super.pack();
	}
	
	public void setupFrame() {
		Canvas canvas = new Canvas();
		
		super.setLayout(new BorderLayout());
		
		super.getContentPane().add(canvas, "Center");
		
		canvas.setPreferredSize(new Dimension(854, 480));
		super.setLocationRelativeTo(null);
		
		Object minecraft = null;
		
		try {
			Constructor constructor = classLoader.loadClass("gu").getConstructors()[0];
			minecraft = constructor.newInstance(this, canvas, null, 854, 480, false, this);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Thread thread = new Thread((Runnable) minecraft, "Minecraft main thread");
		thread.setPriority(10);
		
		try {
			setField(minecraft, "j", "www.minecraft.net");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		try {
			Constructor playerInfoConstructor = classLoader.loadClass("eh").getConstructors()[0];
			// We need to unhardcode the name lol
			Object playerInfo = playerInfoConstructor.newInstance("Alkaline", "");
			
			setField(minecraft, "i", playerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		super.setVisible(true);
		
		
		Object gy = null;
		
		try {
			Constructor gyConstructor = classLoader.loadClass("gy").getConstructors()[0];
			gy = gyConstructor.newInstance(minecraft, thread);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		super.addWindowListener((WindowListener) gy);
		
		thread.start();
		

		this.minecraft = new Minecraft(minecraft);
	}
	
	public void setField(Object object, String fieldName, Object data) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field field = clazz.getField(fieldName);
		boolean accessible = field.isAccessible();
		
		if(!accessible)
			field.setAccessible(true);
		
		field.set(object, data);
		
		if(!accessible) 
			field.setAccessible(false);
	}
	
	public URL[] getDependentJars(String base) throws MalformedURLException {
		String[] names = { "jinput.jar",  "lwjgl.jar",  "lwjgl_util.jar",  "minecraft.jar" };
		URL[] urls = new URL[names.length];
		
		for(int x = 0; x < names.length; x++) {
			urls[x] = new File(base, names[x]).toURI().toURL();
		}
		
		return urls;
	}
}
