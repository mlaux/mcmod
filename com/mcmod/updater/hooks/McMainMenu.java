package com.mcmod.updater.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.mcmod.updater.McUpdater;
import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McMainMenu extends McHook {

	@Override
	public boolean canProcess(McClassNode node) {
		return node.constants.get("Happy new year!") != null;
	}

	@Override
	public void process(McClassNode node) {
		identifyClass(node, "MainMenu");
		identifyClass(node.superName, "Menu");
		
		MethodNode method = node.constants.get("Happy new year!").get(0);
		
		InstructionSearcher searcher = new InstructionSearcher(method);
		searcher.nextLdcInsn("Happy new year!");
		
		FieldInsnNode fin = searcher.nextFieldInsn();
	
		identifyField("getExtraString", fin);
		
		TypeInsnNode tin = (TypeInsnNode) searcher.nextInsn(Opcodes.NEW);
		
		identifyClass(McUpdater.classes.get(tin.desc), "Button");
		
		fin = searcher.nextFieldInsnOfType("Ljava/util/List;");
		
		identifyField("getButtonList", fin);
		
		McClassNode button = classes.get("Button");
		
		for(MethodNode mn : button.instanceMethods) {
			if(mn.desc.endsWith("Z")) {
				identifyMethod("containsPoint", button, mn);
				
				searcher = new InstructionSearcher(mn);
				
				String[] names = { "isEnabled", "getX", "getY", null, "getWidth", null, "getHeight" };
				
				for(String s : names) {
					fin = searcher.nextFieldInsn();
				
					if(s != null) {
						identifyField(s, fin);
					}
				}
			}
		}
	}
}
