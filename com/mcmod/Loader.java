package com.mcmod;

import java.awt.*;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.*;

import com.mcmod.api.*;
import com.mcmod.inter.Minecraft;

/**
 * Currently, this just opens up offline mode. 
 * We can add a login form and stuff later, but I decided
 * for the time being to just skip it.
 * 
 * @author Nicholas Bailey
 */
public class Loader extends JFrame {
	private static McClassLoader classLoader;
	private static Minecraft api;
	
	public static void main(String[] args) {
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		String user = JOptionPane.showInputDialog("Enter a Minecraft username.");
		if(user == null) System.exit(1);
		
		File dir = Util.getWorkingDirectory("minecraft");
		String binFolder = new File(dir, "bin").getAbsolutePath();

		System.setProperty("org.lwjgl.librarypath", binFolder + "/natives");
		System.setProperty("net.java.games.input.librarypath", binFolder + "/natives");
		
		Loader loader = new Loader(user);
		loader.setVisible(true);
	}
	
	public Loader(String user) {
		super("Minecraft - McModded");
		classLoader = new McClassLoader();
		setLayout(new BorderLayout());

		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(854, 480));
		
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		Object minecraft = StaticWorm.instantiate("MinecraftExtension", this, canvas, null, 854, 480, false, this);
		
		Thread thread = new Thread((Runnable) minecraft, "Minecraft main thread");
		thread.setPriority(Thread.MAX_PRIORITY);
		
		Worm worm = new Worm(minecraft);
		worm.set("URL", "www.minecraft.net");
		
		Object playerInfo = StaticWorm.instantiate("PlayerInfo", user, "");
		worm.set("playerInfo", playerInfo);
		
		Object listener = StaticWorm.instantiate("WindowAdapter", minecraft, thread);
		addWindowListener((WindowListener) listener);
		
		thread.start();
		
		super.setJMenuBar(new McMenuBar());
		
		pack();
		setLocationRelativeTo(null);
		
		api = (Minecraft) minecraft;
	}
	
	public static void onRender() {
		
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
		return api;
	}
}
