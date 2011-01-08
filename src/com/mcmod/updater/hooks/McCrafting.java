package com.mcmod.updater.hooks;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.asm.McMethodNode;

public class McCrafting extends McHook {
	@Override
	public boolean canProcess(McClassNode node) {
		List<McMethodNode> methods = node.constants.get("###");
		return methods != null && methods.size() > 3 && node.methods.size() > 2;
	}

	@Override
	public void process(McClassNode node) {
		identifyClass(node, "CraftingManager");
		
		FieldNode f = node.staticFields.get(0);
		
		identifyField("craftingManager", node, f);
		
		f = node.instanceFields.get(0);
		
		identifyField("recipeList", node, f);
	}
}
