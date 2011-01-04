package com.mcmod.updater.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.updater.asm.McClassNode;
import com.mcmod.updater.util.InstructionSearcher;

public class McFont extends McHook {

	public boolean canProcess(McClassNode node) {
		return node.constants.containsKey("0123456789abcdef");
	}
	
	public void process(McClassNode node) {
		identifyClass(node, "Font");
		
		for(FieldNode fn : node.instanceFields) {
			if(fn.desc.equals("I"))
				if((fn.access & Opcodes.ACC_PUBLIC) != 0)
					identifyField("Font.textureID", node, fn);
				else
					identifyField("Font.listBase", node, fn);
			else if(fn.desc.equals("[I"))
				identifyField("Font.charWidths", node, fn);
			else if(fn.desc.equals("Ljava/nio/IntBuffer;"))
				identifyField("Font.listIDBuffer", node, fn);
		}
		
		for(MethodNode mn : node.instanceMethods) {
			if(mn.desc.equals("(Ljava/lang/String;)I"))
				identifyMethod("Font.getStringWidth()", node, mn);
			else if(mn.desc.equals("(Ljava/lang/String;III)V")) {
				InstructionSearcher is = new InstructionSearcher(mn);
				is.nextInsn(Opcodes.INVOKEVIRTUAL);
				if(is.nextInsn(Opcodes.INVOKEVIRTUAL) != null)
					identifyMethod("Font.drawStringShadow()", node, mn);
				else
					identifyMethod("Font.drawStringPlain()", node, mn);
			}
		}
	}
}
