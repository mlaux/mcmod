package com.mcmod.updater.hooks;

import java.lang.reflect.Modifier;

import com.mcmod.updater.asm.McClassNode;

public class McWindowAdapter extends McHook {
	@Override
	public boolean canProcess(McClassNode node) {
		return node.superName.equals("java/awt/event/WindowAdapter") && Modifier.isFinal(node.access);
	}

	@Override
	public void process(McClassNode node) {
		identifyClass(node, "WindowAdapter");
	}
}
