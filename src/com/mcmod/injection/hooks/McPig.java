package com.mcmod.injection.hooks;

import org.objectweb.asm.tree.FieldNode;

import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;

public class McPig extends McHook {

    public boolean canProcess(McClassNode node) {
        return node.constants.get("/mob/pig.png") != null;
    }

    public void process(McClassNode node) {
        identifyClass(node, "Pig");

        for (FieldNode fn : node.instanceFields) {
            if (fn.desc.equals("Z")) {
                identifyField("saddled", node, fn);
            }
        }
    }
}
