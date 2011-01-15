package com.mcmod.updater.hooks;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.mcmod.McClassLoader;
import com.mcmod.updater.asm.McClassNode;

public abstract class McHook {
	private static Map<String, String> interfaces = new HashMap<String, String>();
	
	protected static Map<String, McClassNode> classes = new HashMap<String, McClassNode>();
	
	public abstract boolean canProcess(McClassNode node);
	public abstract void process(McClassNode node);
	
	public void identifyInject(String cc, String cm, MethodNode mn, int ip) {
		MethodInsnNode min = new MethodInsnNode(Opcodes.INVOKESTATIC, cc, cm, "()V");
		mn.instructions.insert(mn.instructions.get(ip), min);
		System.out.println("Added callback to " + cc + "." + cm + " in " + mn.name + " at " + ip);
	}
	
	public void identifyMethod(String name, McClassNode cn, MethodNode mn) {
		System.out.println(name + " is " + cn.name + "." + mn.name + "(" + mn.desc + ")");
		boolean isStatic = (mn.access & Opcodes.ACC_STATIC) != 0;
		
		MethodNode method = new MethodNode(Opcodes.ACC_PUBLIC, name, mn.desc, null, null);
		InsnList list = method.instructions;
		
		Type[] types = Type.getArgumentTypes(mn.desc);
		if(!isStatic)
			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		
		for(int x = 0; x < types.length; x++) {
			int op = types[x].getOpcode(Opcodes.ILOAD);
			list.add(new VarInsnNode(op, types[x].getSize() == 2 ? (2 * x) + 1 : x + 1));
		}
		
		int op = isStatic ? Opcodes.INVOKESTATIC : Opcodes.INVOKEVIRTUAL;
		list.add(new MethodInsnNode(op, cn.name, mn.name, mn.desc));
		list.add(new InsnNode(Type.getReturnType(mn.desc).getOpcode(Opcodes.IRETURN)));
		
		cn.methods.add(method);
	}
	
	public void identifyField(String name, McClassNode cn, FieldNode field) {
		hookField(name, cn, field);
	}
	
	public void identifyField(String name, FieldInsnNode insn) {
		McClassNode cn = McClassLoader.classCache.get(insn.owner);
		FieldNode fn = null;
		for(FieldNode f : cn.fields)
			if(f.name.equals(insn.name))
				fn = f;
		if(fn == null)
			return;
		hookField(name, cn, fn);
	}
	
	public void identifyMethod(String name, MethodInsnNode insn) {
		McClassNode cn = McClassLoader.classCache.get(insn.owner);
		MethodNode mth = null;
		for(MethodNode f : cn.methods)
			if(f.name.equals(insn.name))
				mth = f;
		if(mth == null)
			return;
		identifyMethod(name, cn, mth);
	}
	
	public void identifyClass(McClassNode node, String name) {
		String binName = "com/mcmod/inter/" + name;
		if(!node.interfaces.contains(binName)) {
			node.interfaces.add(binName);
			classes.put(name, node);
			interfaces.put(node.name, binName);
		}
	}
	
	public void identifyClass(String className, String name) {
		identifyClass(McClassLoader.classCache.get(className), name);
	}
	
	protected McClassNode getClass(String name) {
		return McClassLoader.classCache.get(name);
	}
	
	private void hookField(String s, ClassNode cn, FieldNode fn) {
		System.out.println(s + " is " + cn.name + "." + fn.name + "(" + fn.desc + ")");
		boolean isStatic = (fn.access & Opcodes.ACC_STATIC) != 0;
		String methodName = Character.toUpperCase(s.charAt(0)) + s.substring(1);
		
		String type = fn.desc;
		String name = type.replace("[", "");
		if(name.startsWith("L")) {
			name = name.substring(1, name.length() - 1);
			if(interfaces.containsKey(name)) {
				type = type.replace(name, interfaces.get(name));
			}
		}
		
		MethodNode getterMethod = new MethodNode(Opcodes.ACC_PUBLIC, "get" + methodName, "()" + type, null, null);
		
		InsnList getterList = getterMethod.instructions;
		if(!isStatic)
			getterList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		
		int op = isStatic ? Opcodes.GETSTATIC : Opcodes.GETFIELD;
		getterList.add(new FieldInsnNode(op, cn.name, fn.name, fn.desc));
		getterList.add(new InsnNode(Type.getType(type).getOpcode(Opcodes.IRETURN)));
		
		MethodNode setterMethod = new MethodNode(Opcodes.ACC_PUBLIC, "set" + methodName, "(" + type + ")V", null, null);
		
		InsnList setterList = setterMethod.instructions;
		
		if(!isStatic)
			setterList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		setterList.add(new VarInsnNode(Type.getType(type).getOpcode(Opcodes.ILOAD), 1));
		
		if(interfaces.get(name) != null)
			setterList.add(new TypeInsnNode(Opcodes.CHECKCAST, interfaces.get(name)));
		
		op = isStatic ? Opcodes.PUTSTATIC : Opcodes.PUTFIELD;
		setterList.add(new FieldInsnNode(op, cn.name, fn.name, type));
		setterList.add(new InsnNode(Opcodes.RETURN));
		
		cn.methods.add(getterMethod);
		cn.methods.add(setterMethod);
	}
	
	public static String getClassName(String interName) {
		return classes.get(interName).name;
	}
}
