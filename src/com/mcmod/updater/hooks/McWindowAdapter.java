package com.mcmod.updater.hooks;

import java.lang.reflect.Modifier;

import com.mcmod.updater.asm.McClassNode;

public class McWindowAdapter extends McHook {
	
	public boolean canProcess(McClassNode node) {
		return node.superName.equals("java/awt/event/WindowAdapter") && Modifier.isFinal(node.access);
	}

	
	public void process(McClassNode node) {
		identifyClass(node, "WindowAdapter");
	}
}
