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
			int count = 0;
			
			InstructionSearcher searcher = new InstructionSearcher(m);
			
			Object o;
			
			while((o = searcher.nextLdcInsn("Slot")) != null) count++;
			
			if(count == 2) {
				method = m;
				break;
			}
		}
		
		for(FieldNode f : node.instance_fields) {
			if(f.desc.equals("I")) {
				identifyField("Inventory", "currentIndex", f.name);
			}
		}
		
		InstructionSearcher searcher = new InstructionSearcher(method);
		
		searcher.nextLdcInsn("Slot");
		FieldInsnNode fin = searcher.nextFieldInsn();
		identifyField("Inventory", "inventoryItems", fin.name);
		
		searcher.nextLdcInsn("Slot");
		fin = searcher.nextFieldInsn();
		identifyField("Inventory", "equippableItems", fin.name);
		
		identifyClass(fin.desc.replaceAll("[\\[\\]L;]", ""), "InventoryItem");
	}
}
