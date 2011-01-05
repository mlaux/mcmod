package com.mcmod;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import com.mcmod.api.Data;
import com.mcmod.shared.Accessor;

public class McClassLoader extends ClassLoader {
	private JarFile minecraft = null;
	private String jarLocation = "";
	private Map<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
	
	public McClassLoader() {
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
		
		String pathName = name.replace('.', '/');
		
		if(loadedClasses.containsKey(name)) {
			return loadedClasses.get(name);
		}
		
		try {
			c = super.findSystemClass(name);
		} catch(Exception e) {
			try {
				ZipEntry entry = minecraft.getEntry(pathName + ".class");
				
				if(entry != null) {
					ClassNode node = new ClassNode();
					ClassReader reader = new ClassReader(minecraft.getInputStream(entry));
					reader.accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
					
					processDataInjections(pathName, node);
					processInterfaceInjections(node);
					processFieldAndMethodInjections(node);
					
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
	
	private void processDataInjections(String pathName, ClassNode node) {
		if(Data.injections.containsKey(pathName)) {
			Data.injections.get(pathName).process(node);
		}
	}
	
	private void processInterfaceInjections(ClassNode node) {
		for(String s : Data.classes.keySet()) {
			String name = Data.classes.get(s);
			if(name.equals(node.name)) {
				System.out.println("Adding " + s + " to " + node.name);
				node.interfaces.add("com/mcmod/inter/" + s);
			}
		}
	}
	
	private void processFieldAndMethodInjections(ClassNode node) {
		for(String s : Data.accessors.keySet()) {
			/* currently doesn't handle methods, because I need to think
			 *  of a good way to implement etc */
			
			Accessor accessor = Data.accessors.get(s);
			
			String className = accessor.getClassName();
			String itemName = accessor.getItemName();
			String itemSignature = accessor.getItemSignature();
			
			if(itemSignature.charAt(0) == '(') {
				if(node.name.equals(className)) {
					System.out.println("Method [" + s + "] " + className + "." + itemName + " (" + itemSignature + ")");
				
					MethodNode method = new MethodNode(Opcodes.ACC_PUBLIC, s, itemSignature, null, null);
					InsnList list = method.instructions;
					
					Type[] types = Type.getArgumentTypes(itemSignature);
					
					list.add(new VarInsnNode(Opcodes.ALOAD, 0));
					
					for(int x = 0; x < types.length; x++) {
						list.add(new VarInsnNode(types[x].getOpcode(Opcodes.ILOAD), x + 1));
					}
					
					list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, className, itemName, itemSignature));
					list.add(new InsnNode(Type.getReturnType(itemSignature).getOpcode(Opcodes.IRETURN)));
					
					node.methods.add(method);
				}
			} else {
				if(node.name.equals(className)) {
					String methodName = Character.toUpperCase(s.charAt(0)) + s.substring(1);
					
					String type = itemSignature;
					String name = itemSignature.replaceAll("\\[", "");
					
					if(Data.interfaces.containsKey(name)) {
						type = type.replace(name, Data.interfaces.get(name));
					}
					
					MethodNode getterMethod = new MethodNode(Opcodes.ACC_PUBLIC, "get" + methodName, "()" + type, null, null);
					
					InsnList getterList = getterMethod.instructions;
					getterList.add(new VarInsnNode(Opcodes.ALOAD, 0));
					getterList.add(new FieldInsnNode(Opcodes.GETFIELD, className, itemName, itemSignature));
					getterList.add(new InsnNode(Type.getType(itemSignature).getOpcode(Opcodes.IRETURN)));
					
					MethodNode setterMethod = new MethodNode(Opcodes.ACC_PUBLIC, "set" + methodName, "(" + itemSignature + ")V", null, null);
					
					InsnList setterList = setterMethod.instructions;
					setterList.add(new VarInsnNode(Opcodes.ALOAD, 0));
					setterList.add(new VarInsnNode(Type.getType(itemSignature).getOpcode(Opcodes.ILOAD), 1));
					setterList.add(new FieldInsnNode(Opcodes.PUTFIELD, className, itemName, itemSignature));
					setterList.add(new InsnNode(Opcodes.RETURN));
					
					node.methods.add(getterMethod);
					node.methods.add(setterMethod);
				}
			}
		}
	}
}
