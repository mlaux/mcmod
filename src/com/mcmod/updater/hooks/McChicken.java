package com.mcmod.updater.hooks;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class McChicken extends McHook {
	public boolean canProcess(McClassNode node) {
		return node.constants.get("/mob/chicken.png") != null;
	}
	
	public void process(McClassNode node) {
		identifyClass(node, "Chicken");
		MethodNode mn = node.constants.get("/mob/chicken.png").get(0);
		
		InstructionSearcher searcher = new InstructionSearcher(mn);
		
		searcher.nextFieldInsnOfType("Ljava/util/Random;");
		
		FieldInsnNode fin = (FieldInsnNode) searcher.nextInsn(Opcodes.PUTFIELD);
		identifyField("eggTimer", fin);
	}
}
