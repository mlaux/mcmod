package com.mcmod.injection.hooks;

import java.lang.reflect.Modifier;

import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;

public class McExtension extends McHook {

	public boolean canProcess(McClassNode node) {
		return node.superName.equals("net/minecraft/client/Minecraft") && Modifier.isFinal(node.access);
	}

	public void process(McClassNode node) {
		identifyClass(node, "MinecraftExtension");
	}
}
