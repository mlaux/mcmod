package com.mcmod;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import com.mcmod.api.Data;
import com.mcmod.shared.Inject;

/**
 * Works, but kind of slow compared to URLClassLoader.
 * This is because it looks through every file for every class.
 * Would be a lot better if it cached.
 * @author Matt
 *
 */
public class McClassLoader extends ClassLoader {
	private File[] files;
	
	public McClassLoader(File[] f) {
		files = f;
	}
	
	public Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] data = loadFile(name);
		if(data == null)
			throw new ClassNotFoundException(name + " not found in any JARs");
		return super.defineClass(name, data, 0, data.length);
	}
	
	public URL findResource(String name) {
		try {
			for(File f : files) {
				JarFile jf = new JarFile(f);
				JarEntry je = jf.getJarEntry(name);
				if(je == null) continue;
				String s = "jar:file:/" + f.getAbsolutePath().replace(File.separator, "/") + "!/" + name;
				return new URL(s);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private byte[] loadFile(String name) {
		name = name.replace('.', '/');
		try {
			for(File f : files) {
				JarFile jf = new JarFile(f);
				JarEntry je = jf.getJarEntry(name.concat(".class"));
				if(je == null) continue;
				InputStream is = jf.getInputStream(je);
				byte[] data = new byte[(int) je.getSize()];
				int off = 0, read;
				while((read = is.read(data, off, data.length - off)) != -1)
					off += read;
				
				is.close();
				
				if(Data.injections.containsKey(name))
					data = modify(data, Data.injections.get(name));
				return data;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private byte[] modify(byte[] src, Inject inj) {
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(src);
		cr.accept(cn, 0);
		
		inj.process(cn);
		
		ClassWriter cw = new ClassWriter(0);
		cn.accept(cw);
		return cw.toByteArray(); 
	}
}
