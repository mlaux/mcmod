package com.mcmod.updater.hooks;

import java.util.List;

import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class McFinder extends McHook {
	@Override
	public boolean canProcess(McClassNode node) {
		return true;
	}

	@Override
	public void process(McClassNode node) {
		for(MethodNode mn : (List<MethodNode>) node.methods) {
			InstructionSearcher searcher = new InstructionSearcher(mn);
			
			MethodInsnNode min = null;
			
			while((min = (MethodInsnNode) searcher.nextInsn(Opcodes.INVOKEVIRTUAL)) != null) {
				if(min.desc.equals("(J)V")) {
					if(min.owner.equals("dd") || min.owner.endsWith("Minecraft"))
						System.out.println("[" + mn.name + mn.desc + "]" + node.name + ": " + min.owner + "." + min.name);
				}
			}
		}
	}
}
