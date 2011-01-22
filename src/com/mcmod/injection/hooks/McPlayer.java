package com.mcmod.injection.hooks;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassLoader;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;
import com.mcmod.injection.McMethodNode;

public class McPlayer extends McHook {

	private static final String cst = "Player is now ";

	public boolean canProcess(McClassNode node) {
		List<McMethodNode> nodes = node.constants.get(cst);

		return nodes != null && nodes.size() == 1;
	}

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

		McClassNode offlinePlayer = getIdentifiedClass("Player");
		McClassNode humanoid = McClassLoader.getClassNode(offlinePlayer.superName);

		MethodNode inventoryMethod = humanoid.constants.get("Inventory").get(0);

		searcher = new InstructionSearcher(inventoryMethod);

		searcher.nextLdcInsn("Inventory");

		fin = searcher.nextFieldInsn();

		identifyClass(humanoid, "Humanoid");
		identifyField("inventory", fin);

		identifyClass(fin.desc.replaceAll("[L;]", ""), "Inventory");

		method = humanoid.constants.get("http://s3.amazonaws.com/MinecraftCloaks/").get(0);
		searcher = new InstructionSearcher(method);
		searcher.nextLdcInsn("http://s3.amazonaws.com/MinecraftCloaks/");

		fin = searcher.nextFieldInsn();
		identifyField("name", fin);

		method = node.constants.get("www.minecraft.net").get(0);

		searcher = new InstructionSearcher(method);
		searcher.nextLdcInsn("www.minecraft.net");

		fin = searcher.nextFieldInsn();

		identifyField("URL", fin);

		McClassNode humanoidSuperClass = McClassLoader.getClassNode(humanoid.superName);
		identifyClass(humanoidSuperClass, "Animable");

		method = humanoidSuperClass.constants.get("bubble").get(0);

		searcher = new InstructionSearcher(method);

		searcher.nextLdcInsn("bubble");
		fin = (FieldInsnNode) searcher.prevInsn(Opcodes.GETFIELD);

		identifyField("infoMap", fin);

		for (MethodNode mn : humanoidSuperClass.constants.get("Health")) {
			searcher = new InstructionSearcher(mn);

			if (searcher.nextInsn(Opcodes.PUTFIELD) != null) {
				continue;
			}
			break;
		}

		LdcInsnNode ldc;
		while ((ldc = searcher.nextLdcInsn()) != null) {
			fin = (FieldInsnNode) searcher.nextInsn(Opcodes.GETFIELD);
			String name = (String) ldc.cst;
			identifyField(Character.toLowerCase(name.charAt(0)) + name.substring(1), fin);
		}
	}
}
