package com.mcmod.updater;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;

import com.mcmod.shared.Accessor;
import com.mcmod.shared.Inject;
import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.hooks.McChicken;
import com.mcmod.updater.hooks.McCrafting;
import com.mcmod.updater.hooks.McExtension;
import com.mcmod.updater.hooks.McFont;
import com.mcmod.updater.hooks.McHook;
import com.mcmod.updater.hooks.McInventory;
import com.mcmod.updater.hooks.McInventoryItem;
import com.mcmod.updater.hooks.McItem;
import com.mcmod.updater.hooks.McLocation;
import com.mcmod.updater.hooks.McMainMenu;
import com.mcmod.updater.hooks.McMathUtil;
import com.mcmod.updater.hooks.McPig;
import com.mcmod.updater.hooks.McPlayer;
import com.mcmod.updater.hooks.McPlayerInfo;
import com.mcmod.updater.hooks.McSign;
import com.mcmod.updater.hooks.McWindowAdapter;
import com.mcmod.updater.hooks.McWorld;
import com.mcmod.util.Util;

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
			updater.dump(new FileOutputStream("hooks.dat"));
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public McUpdater() throws IOException {
		System.out.println(Util.getWorkingDirectory("minecraft"));
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
		return new McHook[] {
			new McExtension(), 
			new McWindowAdapter(), 
			new McFont(),
			new McPlayer(), 
			new McPlayerInfo(),
			new McInventory(), 
			new McInventoryItem(), 
			new McItem(),
			new McMainMenu(),
			new McLocation(),
			new McWorld(),
			new McChicken(),
			new McPig(),
			new McCrafting(),
			new McSign(),
			new McMathUtil()
		};
	}
	
	public void printLog() {
		for(String name : McHook.classes.keySet()) {
			McClassNode node = McHook.classes.get(name);
			
			System.out.println("[*] " + node.name + " identified as " + name);
			
			for(String s : node.identifiedItems.keySet()) {
				System.out.println("->    " + s + " is " + node.identifiedItems.get(s));
			}
			
			for(Inject i : node.injections) {
				System.out.println("++    Callback to " + i.getCallClass()
						+ "." + i.getCallMethod() + " inserted into "
						+ i.getInjectClass() + "." + i.getInjectMethod()
						+ "(" + i.getInjectMethodSignature() + ") at position " 
						+ i.getInjectPosition());
			}
			
			System.out.println();
		}
	}
	
	public void dump(OutputStream file) {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(file));
		
		System.out.print("Dumping file...");
		
		for(String name : McHook.classes.keySet()) {
			McClassNode node = McHook.classes.get(name);
			
			writer.println("c:" + name + ":" + node.name);
			
			for(String s : node.identifiedItems.keySet()) {
				Accessor field = node.identifiedItems.get(s);
				boolean isMth = field.getItemSignature().startsWith("(");
				writer.println((isMth ? "m" : "f") + ":" + s + ":" + field.getClassName() 
						+ ":" + field.getItemName() + ":" + field.getItemSignature() 
						+ ":" + (field.isStatic() ? "s" : "i"));
			}
			
			for(Inject i : node.injections) {
				writer.println("i:" + i.getCallClass() + ":" + i.getCallMethod() 
						+ ":" + i.getInjectClass() + ":" + i.getInjectMethod() 
						+ ":" + i.getInjectMethodSignature()
						+ ":" + i.getInjectPosition());
			}
		}
		
		writer.flush();
		writer.close();
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
