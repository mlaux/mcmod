package com.mcmod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import com.mcmod.api.Mod;
import com.mcmod.debug.McDebug;

@SuppressWarnings("serial")
public class McMenuBar extends JMenuBar {
	private List<TogglableModMenuItem> togglableMods = new ArrayList<TogglableModMenuItem>();
	private List<ModMenuItem> mods = new ArrayList<ModMenuItem>();
	private Map<JCheckBoxMenuItem, McDebug> debugs = new HashMap<JCheckBoxMenuItem, McDebug>();
	
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
		
		JMenu debugs = new JMenu("Debug");
		
		for(McDebug debug : McDebug.getDebugs()) {
			debugs.add(createDebugMenu(debug));
		}
	}
	
	public JCheckBoxMenuItem createDebugMenu(McDebug debug) {
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(debug.getName());
		
		debugs.put(item, debug);
		
		return item;
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
	
	public List<McDebug> getActiveDebugs() {
		List<McDebug> debugs = new ArrayList<McDebug>();
		
		for(JCheckBoxMenuItem item : this.debugs.keySet()) {
			if(item.isSelected()) {
				debugs.add(this.debugs.get(item));
			}
		}
		
		return debugs;
	}
}
