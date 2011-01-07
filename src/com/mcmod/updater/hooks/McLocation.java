package com.mcmod.updater.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McLocation extends McHook {
	private String[] names = { "x", "y", "z", "speedX", "speedY", "speedZ", "rotationX", "rotationY"};
	
	@Override
	public boolean canProcess(McClassNode node) {
		return node.constants.get("Pos") != null;
	}

	@Override
	public void process(McClassNode node) {
		identifyClass(node, "Entity");
		MethodNode method = null;
		InstructionSearcher searcher = null;
		
		
		for(MethodNode mn : node.constants.get("Pos")) {
			searcher = new InstructionSearcher(mn);
			
			if(searcher.nextInsn(Opcodes.PUTFIELD) == null) {
				method = mn;
				break;
			}
		}
		
		searcher = new InstructionSearcher(method);
		searcher.nextLdcInsn("Pos");
		
		FieldInsnNode fin;
		
		for(String s : names) {
			fin = searcher.nextFieldInsn();
			identifyField(s, fin);
		}
	}
}
