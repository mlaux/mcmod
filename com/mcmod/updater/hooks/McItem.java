package com.mcmod.updater.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McItem extends McHook {
	@Override
	public boolean canProcess(McClassNode node) {
		return node.constants.get("item.") != null;
	}

	@Override
	public void process(McClassNode node) {
		identifyClass(node, "Item");
		
		InstructionSearcher searcher = new InstructionSearcher(node.constants.get("item.").get(0));
		FieldInsnNode fin = searcher.nextFieldInsn();
		identifyField("name", fin);
		
		searcher = new InstructionSearcher(node.constants.get("CONFLICT @ ").get(0));
		searcher.nextLdcInsn("CONFLICT @ ");
		fin = (FieldInsnNode) searcher.prevInsn(Opcodes.PUTFIELD);
	
		identifyField("ID", fin);
		
		fin = (FieldInsnNode) searcher.nextInsn(Opcodes.GETSTATIC);
		identifyField("itemCache", fin);
	}
}
