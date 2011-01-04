package com.mcmod;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.lwjgl.opengl.GL11;

import com.mcmod.api.Button;
import com.mcmod.api.Data;
import com.mcmod.api.Font;
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
	private static McClassLoader2 classLoader;
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
		classLoader = new McClassLoader2();
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
		
		api = new Minecraft(minecraft);
		
		super.setJMenuBar(new McMenuBar());
		
		pack();
		setLocationRelativeTo(null);
	}

	public static void onRender() {
		//Font font = api.getFont();
		//font.drawStringShadow("Menu: " + api.getCurrentMenu().g, 30, 30, 0xffffffff);
	
		
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		for(Button b : api.getCurrentMenu().getButtonList()) {
			DrawingHelper.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}
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
