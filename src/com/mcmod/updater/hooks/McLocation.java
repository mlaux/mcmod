package com.mcmod.updater.hooks;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McLocation extends McHook {
	private String[] ldcs = { "x: ", "y: ", "z: " };
	
	@Override
	public boolean canProcess(McClassNode node) {
		return node.constants.get("x: ") != null;
	}

	@Override
	public void process(McClassNode node) {
		MethodNode method = node.constants.get("x: ").get(0);
		
		InstructionSearcher searcher = new InstructionSearcher(method);
	
		for(String s : ldcs) {
			searcher.nextLdcInsn(s);
			
			FieldInsnNode fin = null;
			
			for(int x = 0; x < 3; x++) {
				fin = searcher.nextFieldInsn();
			}
			
			identifyField(s.charAt(0) + "", fin);
		}
	}
}
