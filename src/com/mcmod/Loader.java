package com.mcmod;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import com.mcmod.api.StaticWorm;
import com.mcmod.debug.McDebug;
import com.mcmod.inter.Minecraft;
import com.mcmod.inter.PlayerInfo;
import com.mcmod.updater.hooks.McHook;
import com.mcmod.util.ExceptionHandler;
import com.mcmod.util.ReflectionExplorer;
import com.mcmod.util.Util;

/**
 * Loader for the Minecraft game.
 */
public class Loader extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static McClassLoader classLoader;
	private static Minecraft minecraft;
	private static Canvas canvas;
	private static ReflectionExplorer explorer = null;
	
	private static McMenuBar menuBar;
	
	public static void main(String[] args) {
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		LoginDialog ld = new LoginDialog();
		ld.setVisible(true);
		String[] info = ld.getInfo();
		if(info[0] == null) System.exit(0);
		
		File dir = Util.getWorkingDirectory("minecraft");
		String binFolder = new File(dir, "bin").getAbsolutePath();

		System.setProperty("org.lwjgl.librarypath", binFolder + "/natives");
		System.setProperty("net.java.games.input.librarypath", binFolder + "/natives");
		
		Loader loader = new Loader(info[0], info[1]);
		loader.setVisible(true);
	}
	
	public Loader(String user, String sid) {
		super("McMod revision 63");
		try {
			classLoader = new McClassLoader();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		setLayout(new BorderLayout());

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(854, 480));
		
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		Object app = StaticWorm.instantiate("MinecraftExtension", this, canvas, null, 854, 480, false, this);
		minecraft = (Minecraft) app;
		
		Thread thread = new Thread((Runnable) app, "Minecraft main thread");
		
		minecraft.setURL("www.minecraft.net");
		
		Object playerInfo = StaticWorm.instantiate("PlayerInfo", user, sid);
		minecraft.setPlayerInfo((PlayerInfo) playerInfo);
		
		Object listener = StaticWorm.instantiate("WindowAdapter", app, thread);
		addWindowListener((WindowListener) listener);

		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
		ExceptionHandler eh = new ExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(eh); // For main thread
		thread.setUncaughtExceptionHandler(eh); // For minecraft thread
		
		menuBar = new McMenuBar();
		setJMenuBar(menuBar);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public static void onRender() {
		if(explorer != null)
			explorer.tick();
		
		glDisable(GL_TEXTURE_2D);
		{
			for(TogglableModMenuItem item : menuBar.getActiveMods())
				item.getMod().process();
			for(McDebug debug : menuBar.getActiveDebugs())
				debug.render();
		}
		glEnable(GL_TEXTURE_2D);
	}
	
	public static Class<?> getClass(String name) {
		try {
			return classLoader.loadClass(McHook.getClassName(name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Minecraft getMinecraft() {
		return minecraft;
	}
	
	public static Canvas getCanvas() {
		return canvas;
	}
}
