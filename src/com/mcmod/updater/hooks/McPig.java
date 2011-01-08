package com.mcmod.updater.hooks;

import org.objectweb.asm.tree.FieldNode;

import com.mcmod.updater.asm.McClassNode;

public class McPig extends McHook {

	@Override
	public boolean canProcess(McClassNode node) {
		return node.constants.get("/mob/cow.png") != null;
	}

	@Override
	public void process(McClassNode node) {
		identifyClass(node, "Pig");
		
		for(FieldNode fn : node.instanceFields) {
			if(fn.desc.equals("Z")) {
				identifyField("saddled", node, fn);
			}
		}
	}
}
