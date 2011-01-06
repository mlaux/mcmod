package com.mcmod.updater.hooks;

import java.util.List;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.McUpdater;
import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.asm.McMethodNode;
import com.mcmod.updater.util.InstructionSearcher;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class McPlayer extends McHook {
	private static final String cst = "Player is now ";
	
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
		
		FieldInsnNode fin = searcher.nextFieldInsn();
		
		identifyClass(fin.desc.replaceAll("[L;]", ""), "Player");
		identifyClass(node, "Minecraft");
		
		identifyField("player", fin);
		
		method = node.constants.get("Respawning").get(0);
		searcher = new InstructionSearcher(method);
		
		searcher.nextLdcInsn("Respawning");
		fin = searcher.nextFieldInsn();
		
		identifyField("currentMenu", fin);
		
		McClassNode offlinePlayer = McHook.classes.get("Player");
		McClassNode humanoid = McUpdater.classes.get(offlinePlayer.superName);
		
		MethodNode inventoryMethod = humanoid.constants.get("Inventory").get(0);
		
		searcher = new InstructionSearcher(inventoryMethod);
		
		searcher.nextLdcInsn("Inventory");
		
		fin = searcher.nextFieldInsn();
		
		identifyClass(humanoid, "Humanoid");
		identifyField("inventory", fin);
		
		identifyClass(fin.desc.replaceAll("[L;]", ""), "Inventory");
		
		method = node.constants.get("www.minecraft.net").get(0);
		
		searcher = new InstructionSearcher(method);
		searcher.nextLdcInsn("www.minecraft.net");
		
		fin = searcher.nextFieldInsn();
	
		identifyField("URL", fin);
		
		McClassNode humanoidSuperClass = McUpdater.classes.get(humanoid.superName);
		identifyClass(humanoidSuperClass, "PlayerEntity"); // This probably needs a better name.
		
		method = humanoidSuperClass.constants.get("bubble").get(0);
		
		searcher = new InstructionSearcher(method);
		
		searcher.nextLdcInsn("bubble");
		fin = (FieldInsnNode) searcher.prevInsn(Opcodes.GETFIELD);
		
		identifyField("infoMap", fin);

		for(MethodNode mn : humanoidSuperClass.constants.get("Health")) {
			searcher = new InstructionSearcher(mn);
			
			if(searcher.nextInsn(Opcodes.PUTFIELD) != null) continue;
			break;
		}
		
		LdcInsnNode ldc;
		while((ldc = searcher.nextLdcInsn()) != null) {
			fin = (FieldInsnNode) searcher.nextInsn(Opcodes.GETFIELD);
			String name = (String) ldc.cst;
			identifyField(Character.toLowerCase(name.charAt(0)) + name.substring(1), fin);
		}
	}
}
