package com.mcmod.injection.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassLoader;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;

public class McSign extends McHook {

	public boolean canProcess(McClassNode node) {
		return node.constants.get("/item/sign.png") != null;
	}

	public void process(McClassNode node) {
		MethodNode mn = node.constants.get("> ").get(0);
		InstructionSearcher searcher = new InstructionSearcher(mn);

		searcher.nextLdcInsn("> ");

		FieldInsnNode fin = (FieldInsnNode) searcher.prevInsn(Opcodes.GETFIELD);
		McClassNode sign = McClassLoader.getClassNode(fin.owner);
		identifyClass(sign, "Sign");
		identifyField("currentLine", fin);

		fin = (FieldInsnNode) searcher.prevInsn(Opcodes.GETFIELD);
		identifyField("lines", fin);
	}
}
