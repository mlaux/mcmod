package com.mcmod.updater.hooks;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.McUpdater;
import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.asm.McMethodNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McPlayer extends McHook {
	private String cst = "Player is now ";
	
	@Override
	public boolean canProcess(McClassNode node) {
		List<McMethodNode> nodes = node.constants.get(cst);
		
		return nodes != null && nodes.size() == 1;
	}

	@Override
	public void process(McClassNode node) {
		McMethodNode method = node.constants.get(cst).get(0);
		
		InstructionSearcher searcher = new InstructionSearcher(method);
		searcher.nextLdcInsn(cst);
		
		FieldInsnNode player = searcher.nextFieldInsn();
		
		identifyClass(player.desc.replaceAll("[L;]", ""), "Player");
		identifyClass(node, "Minecraft");
		
		identifyField("Minecraft", "player", player.name);
		
		McClassNode offlinePlayer = McHook.classes.get("Player");
		McClassNode humanoid = McUpdater.classes.get(offlinePlayer.superName);
		
		MethodNode inventoryMethod = humanoid.constants.get("Inventory").get(0);
		
		searcher = new InstructionSearcher(inventoryMethod);
		
		searcher.nextLdcInsn("Inventory");
		
		player = searcher.nextFieldInsn();
		
		identifyClass(humanoid, "Humanoid");
		identifyField("Humanoid", "inventory", player.name);
		
		identifyClass(player.desc.replaceAll("[L;]", ""), "Inventory");
	}
}
