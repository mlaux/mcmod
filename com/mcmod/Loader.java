package com.mcmod;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.mcmod.api.Data;
import com.mcmod.api.Minecraft;
import com.mcmod.api.StaticWorm;
import com.mcmod.api.Worm;

/**
 * Currently, this just opens up offline mode. 
 * We can add a login form and stuff later, but I decided
 * for the time being to just skip it.
 * 
 * @author Nicholas Bailey
 */
public class Loader extends JFrame {
	private static URLClassLoader classLoader;
	private static Minecraft minecraft;
	
	
	public static void main(String[] args) {
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		File dir = Util.getWorkingDirectory("minecraft");
		String binFolder = new File(dir, "bin").getAbsolutePath();

		System.setProperty("org.lwjgl.librarypath", binFolder + "/natives");
		System.setProperty("net.java.games.input.librarypath", binFolder + "/natives");
		
		String user = JOptionPane.showInputDialog("Enter a Minecraft username.");
		
		Loader loader = new Loader(getDependentJars(binFolder), user);
		loader.setVisible(true);
	}
	
	public Loader(URL[] urls, String user) {
		super("Minecraft - McModded");
		classLoader = new URLClassLoader(urls);
		setLayout(new BorderLayout());

		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(854, 480));
		
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		Object minecraft = StaticWorm.instantiate("MinecraftExtension", this, canvas, null, 854, 480, false, this);
		
		Thread thread = new Thread((Runnable) minecraft, "Minecraft main thread");
		thread.setPriority(Thread.MAX_PRIORITY);
		
		Worm worm = new Worm(minecraft);
		worm.set("Minecraft.url", "www.minecraft.net");
		
		Object playerInfo = StaticWorm.instantiate("PlayerInfo", user, "");
		worm.set("Minecraft.playerInfo", playerInfo);
		
		Object listener = StaticWorm.instantiate("WindowAdapter", minecraft, thread);
		addWindowListener((WindowListener) listener);
		
		thread.start();
		
		Loader.minecraft = new Minecraft(minecraft);
		
		super.setJMenuBar(new McMenuBar());
		
		pack();
		setLocationRelativeTo(null);
	}
	
	private static URL[] getDependentJars(String base) {
		String[] names = { "jinput.jar",  "lwjgl.jar",  "lwjgl_util.jar",  "minecraft.jar" };
		URL[] urls = new URL[names.length];
		
		for(int x = 0; x < names.length; x++) {
			try {
				urls[x] = new File(base, names[x]).toURI().toURL();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return urls;
	}
	
	public static Class<?> getClass(String name) {
		try {
			return classLoader.loadClass(Data.classes.get(name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Minecraft getMinecraft() {
		return minecraft;
	}
}
