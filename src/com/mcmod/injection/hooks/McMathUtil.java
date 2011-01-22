package com.mcmod.injection.hooks;

import org.objectweb.asm.tree.MethodNode;

import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;

public class McMathUtil extends McHook {

	public boolean canProcess(McClassNode node) {
		return node.constants.get(10430.378f) != null;
	}

	public void process(McClassNode cn) {
		identifyClass(cn, "MathUtil");

		for (MethodNode mn : cn.staticMethods) {
			if (!cn.constants.get(10430.378f).contains(mn)) {
				continue;
			}

			if (cn.constants.get(16384.0f).contains(mn)) {
				identifyMethod("cos", cn, mn);
			} else {
				identifyMethod("sin", cn, mn);
			}
		}
	}
}
