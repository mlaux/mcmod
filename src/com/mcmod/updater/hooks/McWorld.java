package com.mcmod.updater.hooks;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McWorld extends McHook {
	@Override
	public boolean canProcess(McClassNode node) {
		return node.constants.get("Time") != null;
	}

	@Override
	public void process(McClassNode node) {
		identifyClass(node, "World");
		
		MethodNode method = null;
		
		for(MethodNode mn : node.constants.get("Time")) {
			if(!mn.desc.contains("File")) {
				method = mn;
				break;
			}
		}
		
		String[] ldcs = { "SpawnX", "SpawnY", "SpawnZ", "Time" };
		InstructionSearcher searcher = new InstructionSearcher(method);
		
		FieldInsnNode fin = null;
		
		for(String s : ldcs) {
			searcher.nextLdcInsn(s);
			fin = searcher.nextFieldInsn();
			
			identifyField(Character.toLowerCase(s.charAt(0)) + s.substring(1), fin);
		}
		
		McClassNode minecraft = classes.get("Minecraft");
		
		for(FieldNode field : minecraft.instanceFields) {
			if(field.desc.equals("L" + node.name + ";")) {
				identifyField("world", minecraft, field);
			}
		}
		
		method = node.constants.get("Player count: ").get(0);
		searcher = new InstructionSearcher(method);
		searcher.nextLdcInsn("Player count: ");
		
		String[] names = { "playerList", "entityList" };
		
		for(String s : names) {
			fin = searcher.nextFieldInsn();
			identifyField(s, fin);
		}
	}
}
