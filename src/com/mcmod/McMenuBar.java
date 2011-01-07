package com.mcmod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import com.mcmod.api.Mod;

public class McMenuBar extends JMenuBar {
	private List<TogglableModMenuItem> togglableMods = new ArrayList<TogglableModMenuItem>();
	private List<ModMenuItem> mods = new ArrayList<ModMenuItem>();
	
	public McMenuBar() {
		JMenu menu = new JMenu("Mods");
		loadMods();
		
		for(ModMenuItem mod : mods) {
			menu.add(mod);
		}
		
		menu.add(new JSeparator());
		
		for(TogglableModMenuItem mod : togglableMods) {
			menu.add(mod);
		}
		
		super.add(menu);
	}
	
	public void loadMods() {
		try {
			File dir = new File("./bin");
			URLClassLoader classLoader = new URLClassLoader(new URL[] { dir.toURI().toURL() });
			
			for(String name : dir.list()) {
				if(name.endsWith(".class")) {
					try {
						Class<?> c = classLoader.loadClass(name.replace(".class", ""));
						
						if(Mod.class.isAssignableFrom(c)) {
							try {
								Mod m = (Mod) c.newInstance();
								
								if(m.isTogglable()) {
									togglableMods.add(new TogglableModMenuItem(m));
								} else {
									mods.add(new ModMenuItem(m));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public List<TogglableModMenuItem> getActiveMods() {
		List<TogglableModMenuItem> items = new ArrayList<TogglableModMenuItem>();
		
		for(TogglableModMenuItem item : this.togglableMods) {
			if(item.isSelected()) {
				items.add(item);
			}
		}
		
		return items;
	}
}
