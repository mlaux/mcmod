package com.mcmod.injection.hooks;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;

public class McInventory extends McHook {

    public boolean canProcess(McClassNode node) {
        McClassNode inventory = getIdentifiedClass("Inventory");

        return inventory != null && node.name.equals(inventory.name);
    }

    public void process(McClassNode node) {
        MethodNode method = null;

        for (MethodNode m : node.constants.get("Slot")) {
            InstructionSearcher searcher = new InstructionSearcher(m);

            int count = 0;
            while (searcher.nextLdcInsn("Slot") != null) {
                count++;
            }

            if (count == 2) {
                method = m;
            }
        }

        for (FieldNode f : node.instanceFields) {
            if (f.desc.equals("I")) {
                identifyField("currentIndex", node, f);
            }
        }

        InstructionSearcher searcher = new InstructionSearcher(method);

        searcher.nextLdcInsn("Slot");
        FieldInsnNode fin = searcher.nextFieldInsn();
        identifyField("inventoryItems", fin);

        searcher.nextLdcInsn("Slot");
        fin = searcher.nextFieldInsn();
        identifyField("equippableItems", fin);

        identifyClass(fin.desc.replaceAll("[\\[\\]L;]", ""), "InventoryItem");
    }
}
