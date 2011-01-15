package com.mcmod.injection;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;


public class McMethodNode extends MethodNode {
	private McClassNode owner;
	
	public McMethodNode(McClassNode owner, int access, String name, String desc, String signature, String[] exceptions) {
		super(access, name, desc, signature, exceptions);
	
		this.owner = owner;
	}
	
	@Override
	public void visitLdcInsn(Object cst) {
		List<McMethodNode> methods = owner.constants.get(cst);
		
		if(methods == null) {
			methods = new ArrayList<McMethodNode>();
		}
		
		methods.add(this);
		
		owner.constants.put(cst, methods);
		instructions.add(new LdcInsnNode(cst));
	}
}
