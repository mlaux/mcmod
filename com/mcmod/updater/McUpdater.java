package com.mcmod.updater;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;

import com.mcmod.Loader;
import com.mcmod.Util;
import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.hooks.McHook;
import com.mcmod.updater.hooks.McInventory;
import com.mcmod.updater.hooks.McInventoryItem;
import com.mcmod.updater.hooks.McItem;
import com.mcmod.updater.hooks.McPlayer;
import com.mcmod.updater.hooks.McPlayerInfo;

/****************************************************
 * 
 *  TEKK THIS IS SUPER SUPER HACKED UP, I PLAN ON MAKING IT
 *  BETTER LATER, OR YOU CAN, EITHER WAY IT WILL BE MADE BETTER.
 *  
 *  SAME GOING FOR THE REFLECTION LOADING, IT JUST SEEMS REALLY NASTY
 *  [/capslock]
 *
 ****************************************************/
public class McUpdater {
	private JarFile file;
	public static Map<String, McClassNode> classes = new HashMap<String, McClassNode>();
	
	public static void main(String[] args) {
		try {
			McUpdater updater = new McUpdater();
			updater.update();
			updater.printLog();
			updater.dump(new FileOutputStream(new File(Loader.class.getResource("hooks.dat").toURI())));
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public McUpdater() throws IOException {
		file = new JarFile(Util.getWorkingDirectory("minecraft") + "/bin/minecraft.jar");
	}
	
	public void update() throws IOException {
		loadClasses();
		
		McHook[] hooks = loadHooks();
		
		for(McHook hook : hooks) {
			for(McClassNode node : classes.values()) {
				if(hook.canProcess(node)) {
					hook.process(node);
				}
			}
		}
	}
	
	public McHook[] loadHooks() {
		/* Tekk, I'm terribad at making a good dependency automated loader thing,
		 * if you want, you can lol. */
		return new McHook[] { new McPlayer(), new McPlayerInfo(), new McInventory(), new McInventoryItem(), new McItem() };
	}
	
	public void printLog() {
		for(String name : McHook.classes.keySet()) {
			McClassNode node = McHook.classes.get(name);
			
			System.out.println("[*] " + node.name + " identified as " + name);
			
			for(String s : node.identified_fields.keySet()) {
				System.out.println("->    get" + (Character.toUpperCase(s.charAt(0))) + s.substring(1) + " returns " + name + "." + node.identified_fields.get(s));
			}
			
			System.out.println();
		}
	}
	
	public void dump(OutputStream file) {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file));
		
		System.out.print("Dumping file...");
		
		for(String name : McHook.classes.keySet()) {
			McClassNode node = McHook.classes.get(name);
			
			try {
				writer.write("c" + name + ":" + node.name);
				writer.newLine();
				writer.flush();
			} catch(IOException e) {
				e.printStackTrace(System.err);
			}
			
			for(String s : node.identified_fields.keySet()) {
				String field = node.identified_fields.get(s);
			
				try {
					writer.write("f" + name + ":" + s + ":" + field);
					writer.newLine();
					writer.flush();
				} catch(IOException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		try {
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("done.");
	}
	
	public void loadClasses() throws IOException {
		Enumeration<JarEntry> entries = file.entries();
	
		System.out.println("Loading classes...");
		
		while(entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			
			String name = entry.getName();
			
			if(name.endsWith(".class")) {
				McClassNode node = new McClassNode();
				ClassReader reader = new ClassReader(file.getInputStream(entry));
				reader.accept(node, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
				
				classes.put(node.name, node);
			}
		}
		
		System.out.println("Loaded " + classes.size() + " classes.");
	}
}
