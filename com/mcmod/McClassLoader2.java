package com.mcmod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class McClassLoader2 extends URLClassLoader {
	private JarFile minecraft = null;
	private String jarLocation = "";
	private Map<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
	
	public McClassLoader2(URL[] files) {
		super(files);
		
		try {
			jarLocation = Util.getWorkingDirectory("minecraft") + "/bin/minecraft.jar";
			minecraft = new JarFile(jarLocation);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public URL getResource(String name) {
		try {
			return new URL("jar:file:" + jarLocation + "!/" + name);
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
    }
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> c = null;
		
		if(loadedClasses.containsKey(name)) {
			return loadedClasses.get(name);
		}
		
		try {
			c = super.findSystemClass(name);
		} catch(Exception e) {
			try {
				ZipEntry entry = minecraft.getEntry(name.replaceAll("\\.", File.separator) + ".class");
				
				if(entry != null) {
					ClassNode node = new ClassNode();
					ClassReader reader = new ClassReader(minecraft.getInputStream(entry));
					reader.accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
					
					//TODO Hax to your heart's content.
					
					ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
					node.accept(writer);
					
					byte[] buffer = writer.toByteArray();
					
					c = super.defineClass(name, buffer, 0, buffer.length);
					loadedClasses.put(name, c);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	
		if(c != null) {
			return c;
		}
	
		return super.loadClass(name);
	}
}
