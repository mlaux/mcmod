package com.mcmod.updater.hooks;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McInventory extends McHook {
	@Override
	public boolean canProcess(McClassNode node) {
		McClassNode inventory = classes.get("Inventory");

		return inventory != null && node.name.equals(inventory.name);
	}

	@Override
	public void process(McClassNode node) {
		MethodNode method = null;
		
		for(MethodNode m : node.constants.get("Slot")) {
			InstructionSearcher searcher = new InstructionSearcher(m);
			
			int count = 0;
			while(searcher.nextLdcInsn("Slot") != null)
				count++;
			
			if(count == 2)
				method = m;
		}
		
		for(FieldNode f : node.instanceFields) {
			if(f.desc.equals("I"))
				identifyField("getCurrentIndex", node, f);
		}
		
		InstructionSearcher searcher = new InstructionSearcher(method);
		
		searcher.nextLdcInsn("Slot");
		FieldInsnNode fin = searcher.nextFieldInsn();
		identifyField("getInventoryItems", fin);
		
		searcher.nextLdcInsn("Slot");
		fin = searcher.nextFieldInsn();
		identifyField("getEquippableItems", fin);
		
		identifyClass(fin.desc.replaceAll("[\\[\\]L;]", ""), "InventoryItem");
	}
}
