package com.mcmod.updater.hooks;

import java.util.List;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.asm.McMethodNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McPlayerInfo extends McHook {
	private String cst = "Setting user: ";
	
	public boolean canProcess(McClassNode node) {
		List<McMethodNode> methods = node.constants.get(cst);
		return methods != null && methods.size() == 1;
	}
	
	public void process(McClassNode node) {
		MethodNode method = node.constants.get(cst).get(0);
		
		InstructionSearcher searcher = new InstructionSearcher(method);

		searcher.nextLdcInsn(cst);
		
		FieldInsnNode field = searcher.nextFieldInsn();
		
		field = searcher.nextFieldInsn();
		
		identifyClass(field.owner, "Minecraft");
		
		McClassNode mc = classes.get("Minecraft");
		for(FieldNode fn : mc.instanceFields) {
			if(fn.desc.equals("L" + classes.get("Font").name + ";"))
				identifyField("Minecraft.font", mc, fn);
		}
		
		for(MethodNode mn : mc.instanceMethods) {
			if(!mn.name.equals("run")) continue;
			InstructionSearcher is = new InstructionSearcher(mn);
			is.nextLdcInsn("Post render");
			identifyInject("com/mcmod/Loader", "onRender", mc, mn, is.position());
		}
		
		identifyField("Minecraft.playerInfo", field);
		
		field = searcher.nextFieldInsn();
		
		identifyClass(field.owner, "PlayerInfo");
		identifyField("PlayerInfo.username", field);
		
		for(int x = 0; x < 3; x++)
			field = searcher.nextFieldInsn();
		
		identifyField("PlayerInfo.sessionId", field);
		
		for(int x = 0; x < 3; x++)
			field = searcher.nextFieldInsn();
		
		identifyField("PlayerInfo.password", field);
	}
}
