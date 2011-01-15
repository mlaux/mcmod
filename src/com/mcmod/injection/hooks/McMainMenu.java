package com.mcmod.injection.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;


public class McMainMenu extends McHook {

	
	public boolean canProcess(McClassNode node) {
		return node.constants.get("Happy new year!") != null;
	}

	
	public void process(McClassNode node) {
		identifyClass(node, "MainMenu");
		identifyClass(node.superName, "Menu");
		
		MethodNode method = node.constants.get("Happy new year!").get(0);
		
		InstructionSearcher searcher = new InstructionSearcher(method);
		searcher.nextLdcInsn("Happy new year!");
		
		FieldInsnNode fin = searcher.nextFieldInsn();
	
		identifyField("extraString", fin);
		
		TypeInsnNode tin = (TypeInsnNode) searcher.nextInsn(Opcodes.NEW);
		
		identifyClass(getClass(tin.desc), "Button");
		
		fin = searcher.nextFieldInsnOfType("Ljava/util/List;");
		
		identifyField("buttonList", fin);
		
		McClassNode button = classes.get("Button");
		
		for(MethodNode mn : button.instanceMethods) {
			if(mn.desc.endsWith("Z")) {
				identifyMethod("containsPoint", button, mn);
				
				searcher = new InstructionSearcher(mn);
				
				String[] names = { "enabled", "X", "Y", null, "width", null, "height" };
				
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
