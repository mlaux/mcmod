package com.mcmod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

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

public class McClassLoader extends ClassLoader {
	public static Map<String, McClassNode> classCache = new HashMap<String, McClassNode>();
	
	private JarFile minecraft = null;
	private String jarLocation = "";
	private Map<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
	
	private McHook[] hooks = getHooks();
	
	public McClassLoader() throws IOException {
		jarLocation = Util.getWorkingDirectory("minecraft") + "/bin/minecraft.jar";
		minecraft = new JarFile(jarLocation);
		
		Enumeration<JarEntry> entries = minecraft.entries();
		while(entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			
			String name = entry.getName();
			
			if(name.endsWith(".class")) {
				McClassNode node = new McClassNode();
				ClassReader reader = new ClassReader(minecraft.getInputStream(entry));
				reader.accept(node, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
				
				classCache.put(node.name, node);
			}
		}
		
		System.out.println("Loaded " + classCache.size() + " classes.");

		
		for(McHook hook : hooks)
			for(McClassNode node : classCache.values())
				if(hook.canProcess(node))
					hook.process(node);
	}
	
	public URL getResource(String name) {
		try {
			return new URL("jar:file:" + jarLocation + "!/" + name);
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
    }
	
	
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if(loadedClasses.containsKey(name))
			return loadedClasses.get(name);
		
		try {
			return super.findSystemClass(name);
		} catch(Exception e) {
			ClassNode node = classCache.get(name.replace('.', '/'));
			
			if(node != null) {
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				node.accept(writer);
				
				byte[] buffer = writer.toByteArray();
				Class<?> cl = super.defineClass(name, buffer, 0, buffer.length);
				loadedClasses.put(name, cl);
				return cl;
			}
		}
	
		throw new ClassNotFoundException();
	}
	
	private static McHook[] getHooks() {
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
}
