package com.mcmod.updater.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.McUpdater;
import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McSign extends McHook {
	
	public boolean canProcess(McClassNode node) {
		return node.constants.get("/item/sign.png") != null;
	}

	
	public void process(McClassNode node) {
		MethodNode mn = node.constants.get("> ").get(0);
		InstructionSearcher searcher = new InstructionSearcher(mn);
		
		searcher.nextLdcInsn("> ");
		
		FieldInsnNode fin = (FieldInsnNode) searcher.prevInsn(Opcodes.GETFIELD);
		McClassNode sign = McUpdater.classes.get(fin.owner);
		identifyClass(sign, "Sign");
		identifyField("currentLine", fin);
		
		fin = (FieldInsnNode) searcher.prevInsn(Opcodes.GETFIELD);
		identifyField("lines", fin);
	}
}
