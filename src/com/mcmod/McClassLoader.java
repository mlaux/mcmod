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
					
					if(pathName.equals("net/minecraft/client/Minecraft"))
						processStaticInjections(node);
					
					processDataInjections(pathName, node);
					processInterfaceInjections(node);
					processInstanceInjections(node);
					
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
	
	private void processStaticInjections(ClassNode node) {
		for(String s : Data.accessors.keySet()) {
			Accessor acc = Data.accessors.get(s);
			if(!acc.isStatic()) continue;
			
			String itemSignature = acc.getItemSignature();
			
			if(itemSignature.charAt(0) == '(')
				hookMethod(node, s, acc);
			else hookField(node, s, acc);
		}
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
	
	private void processInstanceInjections(ClassNode node) {
		for(String s : Data.accessors.keySet()) {
			Accessor accessor = Data.accessors.get(s);
			
			String className = accessor.getClassName();
			String itemSignature = accessor.getItemSignature();
			
			if(node.name.equals(className)) {
				if(itemSignature.charAt(0) == '(')
					hookMethod(node, s, accessor);
				else hookField(node, s, accessor);
			}
		}
	}
	
	private void hookMethod(ClassNode cn, String s, Accessor acc) {
		String className = acc.getClassName();
		String itemName = acc.getItemName();
		String itemSignature = acc.getItemSignature();
		
		System.out.println("Method [" + s + "] " + className + "." + itemName + " (" + itemSignature + ")");
		
		MethodNode method = new MethodNode(Opcodes.ACC_PUBLIC, s, itemSignature, null, null);
		InsnList list = method.instructions;
		
		Type[] types = Type.getArgumentTypes(itemSignature);
		if(!acc.isStatic())
			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		
		for(int x = 0; x < types.length; x++) {
			if(types[x].getSize() == 2)
				list.add(new VarInsnNode(types[x].getOpcode(Opcodes.ILOAD), (2 * x) + 1));
			else
				list.add(new VarInsnNode(types[x].getOpcode(Opcodes.ILOAD), x + 1));
		}
		
		int op = acc.isStatic() ? Opcodes.INVOKESTATIC : Opcodes.INVOKEVIRTUAL;
		list.add(new MethodInsnNode(op, className, itemName, itemSignature));
		list.add(new InsnNode(Type.getReturnType(itemSignature).getOpcode(Opcodes.IRETURN)));
		
		cn.methods.add(method);
	}
	
	private void hookField(ClassNode cn, String s, Accessor acc) {
		String className = acc.getClassName();
		String itemName = acc.getItemName();
		String itemSignature = acc.getItemSignature();
		
		System.out.println("Field [" + s + "] " + className + "." + itemName + " (" + itemSignature + ")");
		
		String methodName = Character.toUpperCase(s.charAt(0)) + s.substring(1);
		
		String type = itemSignature;
		String name = itemSignature.replaceAll("\\[", "");
		
		if(Data.interfaces.containsKey(name)) {
			type = type.replace(name, Data.interfaces.get(name));
		}
		
		MethodNode getterMethod = new MethodNode(Opcodes.ACC_PUBLIC, "get" + methodName, "()" + type, null, null);
		
		InsnList getterList = getterMethod.instructions;
		if(!acc.isStatic())
			getterList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		
		int op = acc.isStatic() ? Opcodes.GETSTATIC : Opcodes.GETFIELD;
		getterList.add(new FieldInsnNode(op, className, itemName, itemSignature));
		getterList.add(new InsnNode(Type.getType(itemSignature).getOpcode(Opcodes.IRETURN)));
		
		MethodNode setterMethod = new MethodNode(Opcodes.ACC_PUBLIC, "set" + methodName, "(" + itemSignature + ")V", null, null);
		
		InsnList setterList = setterMethod.instructions;
		
		if(!acc.isStatic())
			setterList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		setterList.add(new VarInsnNode(Type.getType(itemSignature).getOpcode(Opcodes.ILOAD), 1));
		
		op = acc.isStatic() ? Opcodes.PUTSTATIC : Opcodes.PUTFIELD;
		setterList.add(new FieldInsnNode(op, className, itemName, itemSignature));
		setterList.add(new InsnNode(Opcodes.RETURN));
		
		cn.methods.add(getterMethod);
		cn.methods.add(setterMethod);
	}
}
