package com.mcmod.injection.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;

public class McItem extends McHook {

	public boolean canProcess(McClassNode node) {
		return node.constants.get("item.") != null;
	}

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
