package com.mcmod.injection.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;


public class McInventoryItem extends McHook {

	
	public boolean canProcess(McClassNode node) {
		McClassNode inventoryItem = getIdentifiedClass("InventoryItem");
		
		return inventoryItem != null && node.name.equals(inventoryItem.name);
	}

	
	public void process(McClassNode node) {
		MethodNode method = null;
		
		for(MethodNode m : node.constants.get("id")) {
			InstructionSearcher searcher = new InstructionSearcher(m);
			
			if(searcher.nextInsn(Opcodes.PUTFIELD) == null) {
				method = m;
				break;
			}
		}
		
		InstructionSearcher searcher = new InstructionSearcher(method);
		LdcInsnNode ldc = null;
		
		while((ldc = searcher.nextLdcInsn()) != null) {
			FieldInsnNode field = searcher.nextFieldInsn();
			String s = ((String) ldc.cst).toLowerCase();
			identifyField(Character.toLowerCase(s.charAt(0)) + s.substring(1), field);
		}
	}
}
