package com.mcmod.updater.hooks;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.shared.Accessor;
import com.mcmod.shared.Inject;
import com.mcmod.updater.McUpdater;
import com.mcmod.updater.asm.McClassNode;

public abstract class McHook {
	public static Map<String, McClassNode> classes = new HashMap<String, McClassNode>();
	private int identifiedFields = 0;
	
	public abstract boolean canProcess(McClassNode node);
	public abstract void process(McClassNode node);
	
	public void identifyInject(String cc, String cm, McClassNode cn, MethodNode mn, int ip) {
		cn.injections.add(new Inject(cc, cm, cn.name, mn.name, mn.desc, ip));
	}
	
	public void identifyFieldOrMethod(String name, McClassNode cn, String fname, String fdesc, boolean is) {
		if(cn.identifiedItems.containsKey(name)) {
			System.out.println("[WARNING][" + cn.name + "] Duplicate hook: " + name);
		}
		
		cn.identifiedItems.put(name, new Accessor(cn.name, fname, fdesc, is));
	
		identifiedFields++;
	}
	
	public void identifyMethod(String name, McClassNode cn, MethodNode mn) {
		identifyFieldOrMethod(name, cn, mn.name, mn.desc, (mn.access & Opcodes.ACC_STATIC) != 0);
	}
	
	public void identifyField(String name, McClassNode cn, FieldNode field) {
		identifyFieldOrMethod(name, cn, field.name, field.desc, (field.access & Opcodes.ACC_STATIC) != 0);
	}
	
	public void identifyField(String name, FieldInsnNode field) {
		McClassNode cn = McUpdater.classes.get(field.owner);
		identifyFieldOrMethod(name, cn, field.name, field.desc, field.getOpcode() == Opcodes.GETSTATIC);
	}
	
	public void identifyClass(McClassNode node, String name) {
		if(!McHook.classes.containsKey(name))
			McHook.classes.put(name, node);
	}
	
	public void identifyClass(String className, String name) {
		identifyClass(McUpdater.classes.get(className), name);
	}
	
	public int getIdentifiedFields() {
		return identifiedFields;
	}
}
