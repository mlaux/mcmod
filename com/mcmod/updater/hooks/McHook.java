package com.mcmod.updater.hooks;

import java.util.HashMap;
import java.util.Map;

import com.mcmod.updater.McUpdater;
import com.mcmod.updater.asm.McClassNode;

public abstract class McHook {
	public static Map<String, McClassNode> classes = new HashMap<String, McClassNode>();
	private int identifiedFields = 0;
	
	public abstract boolean canProcess(McClassNode node);
	public abstract void process(McClassNode node);
	
	public void identifyField(String owner, String name, String fieldName) {
		McClassNode node = classes.get(owner);
		
		if(node == null) {
			System.err.println("Identify the class before you add fields to it.");
			throw new RuntimeException("unknown owner");
		}
		
		if(node.identified_fields.containsKey(name)) {
			System.out.println("[WARNING][" + owner + "] Duplicate hook: " + name);
		}
		
		node.identified_fields.put(name, fieldName);
	
		identifiedFields++;
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
