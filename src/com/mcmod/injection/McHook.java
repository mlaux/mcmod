package com.mcmod.injection;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public abstract class McHook {

	public abstract boolean canProcess(McClassNode node);

	public abstract void process(McClassNode node);

	public void identifyClass(McClassNode node, String name) {
		Injector.addClass(new ClassID(name, node));
	}

	public void identifyClass(String className, String name) {
		identifyClass(McClassLoader.classCache.get(className), name);
	}

	public void identifyField(String name, McClassNode cn, FieldNode fn) {
		Injector.addField(new FieldID(name, cn, fn));
	}

	public void identifyField(String name, FieldInsnNode insn) {
		McClassNode cn = McClassLoader.classCache.get(insn.owner);
		FieldNode fn = null;
		for (FieldNode f : cn.fields) {
			if (f.name.equals(insn.name)) {
				fn = f;
			}
		}
		if (fn == null) {
			return;
		}
		identifyField(name, cn, fn);
	}

	public void identifyMethod(String name, McClassNode cn, MethodNode mn) {
		Injector.addMethod(new MethodID(name, cn, mn));
	}

	public void identifyMethod(String name, MethodInsnNode insn) {
		McClassNode cn = McClassLoader.classCache.get(insn.owner);
		MethodNode mth = null;
		for (MethodNode mn : cn.methods) {
			if (mn.name.equals(insn.name) && mn.desc.equals(insn.desc)) {
				mth = mn;
			}
		}
		if (mth == null) {
			return;
		}
		identifyMethod(name, cn, mth);
	}

	public void identifyInject(String cc, String cm, MethodNode mn, int ip) {
		Injector.addInjection(new InjectionID(cc, cm, mn, ip));
	}

	protected McClassNode getIdentifiedClass(String name) {
		return Injector.getIdentifiedClass(name);
	}
}
