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

import com.mcmod.api.Data;
import com.mcmod.api.StaticWorm;
import com.mcmod.api.Worm;
import com.mcmod.debug.McDebug;
import com.mcmod.inter.Minecraft;
import com.mcmod.util.ExceptionHandler;
import com.mcmod.util.ReflectionExplorer;

/**
 * Currently, this just opens up offline mode. 
 * We can add a login form and stuff later, but I decided
 * for the time being to just skip it.
 * 
 * @author Nicholas Bailey
 */
@SuppressWarnings("serial")
public class Loader extends JFrame {
	private static McClassLoader classLoader;
	private static Minecraft api;
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
		super("Minecraft - McModded");
		classLoader = new McClassLoader();
		setLayout(new BorderLayout());

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(854, 480));
		
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		Object minecraft = StaticWorm.instantiate("MinecraftExtension", this, canvas, null, 854, 480, false, this);
		
		Thread thread = new Thread((Runnable) minecraft, "Minecraft main thread");
		thread.setPriority(Thread.MAX_PRIORITY);
		
		ExceptionHandler eh = new ExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(eh); // For main thread
		thread.setUncaughtExceptionHandler(eh); // For minecraft thread
		
		Worm worm = new Worm(minecraft);
		worm.set("URL", "www.minecraft.net");
		
		Object playerInfo = StaticWorm.instantiate("PlayerInfo", user, sid);
		worm.set("playerInfo", playerInfo);
		
		Object listener = StaticWorm.instantiate("WindowAdapter", minecraft, thread);
		addWindowListener((WindowListener) listener);
		
		thread.start();
		
		menuBar = new McMenuBar();
		super.setJMenuBar(menuBar);
		
		pack();
		setLocationRelativeTo(null);
		
		api = (Minecraft) minecraft;
//		explorer = new ReflectionExplorer(minecraft);
//		explorer.setVisible(true);
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
			return classLoader.loadClass(Data.classes.get(name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Minecraft getMinecraft() {
		return api;
	}
	
	public static Canvas getCanvas() {
		return canvas;
	}
}
