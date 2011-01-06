package com.mcmod;

import static org.lwjgl.opengl.GL11.*;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.lwjgl.input.Keyboard;

import com.mcmod.api.Data;
import com.mcmod.api.StaticWorm;
import com.mcmod.api.Worm;
import com.mcmod.inter.Font;
import com.mcmod.inter.Item;
import com.mcmod.inter.MainMenu;
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

		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(854, 480));
		
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		Object minecraft = StaticWorm.instantiate("MinecraftExtension", this, canvas, null, 854, 480, false, this);
		
		Thread thread = new Thread((Runnable) minecraft, "Minecraft main thread");
		thread.setPriority(Thread.MAX_PRIORITY);
		
		Worm worm = new Worm(minecraft);
		worm.set("URL", "www.minecraft.net");
		
		Object playerInfo = StaticWorm.instantiate("PlayerInfo", user, sid);
		worm.set("playerInfo", playerInfo);
		
		Object listener = StaticWorm.instantiate("WindowAdapter", minecraft, thread);
		addWindowListener((WindowListener) listener);
		
		thread.start();
		
		super.setJMenuBar(new McMenuBar());
		
		pack();
		setLocationRelativeTo(null);
		
		api = (Minecraft) minecraft;
	}
	
	private static boolean changed = false;
	
	public static void onRender() {
		Font f = api.getFont();
		
		glDisable(GL_TEXTURE_2D);
		{
			
		}
		glEnable(GL_TEXTURE_2D);

		if(!changed) {
			MainMenu menu = (MainMenu) api.getCurrentMenu();
			menu.setExtraString("Happy Birthday, Tekk!");
			changed = true;
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
