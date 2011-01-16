package com.mcmod.injection.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;


public class McLocation extends McHook {
	private String[] names = { "x", "y", "z", "speedX", "speedY", "speedZ", "rotationY", "rotationX", "fallDistance", "fireTimer", "airTimer", "onGround" };
	
	
	public boolean canProcess(McClassNode node) {
		return node.constants.get("Pos") != null;
	}

	
	public void process(McClassNode node) {
		identifyClass(node, "Entity");
		MethodNode method = null;
		InstructionSearcher searcher = null;
		
		for(MethodNode mn : node.instanceMethods) {
			if(!mn.desc.equals("(DDDFF)V"))
				continue;
			InstructionSearcher is = new InstructionSearcher(mn);
			MethodInsnNode min = (MethodInsnNode) is.nextInsn(Opcodes.INVOKEVIRTUAL);
			if(is.nextInsn(Opcodes.INVOKEVIRTUAL) == null)
				continue;
			identifyMethod("setPosition", min);
		}
		
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
