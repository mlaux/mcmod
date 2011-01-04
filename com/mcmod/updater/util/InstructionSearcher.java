package com.mcmod.updater.util;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class InstructionSearcher {
	private AbstractInsnNode[] nodes;
	private int index = -1;
	
	public InstructionSearcher(MethodNode method) {
		this(method.instructions);
	}
	
	public InstructionSearcher(InsnList list) {
		nodes = list.toArray();
	}
	
	public FieldInsnNode nextFieldInsn() {
		if(index >= nodes.length) return null;
		
		for(int x = index + 1; x < nodes.length; x++) {
			if(nodes[x] instanceof FieldInsnNode) {
				index = x;
				return (FieldInsnNode) nodes[x];
			}
		}
		
		return null;
	}
	
	public FieldInsnNode nextFieldInsnOfType(String desc) {
		FieldInsnNode fin = null;
		
		while((fin = nextFieldInsn()) != null) {
			if(fin.desc.equals(desc)) {
				return fin;
			}
		}
		
		return null;
	}

	public LdcInsnNode nextLdcInsn() {
		if(index >= nodes.length) return null;
		
		for(int x = index + 1; x < nodes.length; x++) {
			if(nodes[x] instanceof LdcInsnNode) {
				index = x;
					
				return (LdcInsnNode) nodes[x];
			}
		}
		
		return null;
	}
	
	public LdcInsnNode nextLdcInsn(Object cst) {
		if(index >= nodes.length) return null;
		
		for(int x = index + 1; x < nodes.length; x++) {
			if(nodes[x] instanceof LdcInsnNode) {
				LdcInsnNode ldc = (LdcInsnNode) nodes[x];
				
				if(ldc.cst.equals(cst)) {
					index = x;
					return ldc;
				}
			}
		}
		
		return null;
	}
	
	public AbstractInsnNode prevInsn(int id) {
		for(int x = index - 1; x > -1; x--) {
			if(nodes[x].getOpcode() == id) {
				index = x;
				
				return nodes[x];
			}
		}
		
		return null;
	}
	
	public AbstractInsnNode nextInsn(int id) {
		for(int x = index + 1; x< nodes.length; x++) {
			if(nodes[x].getOpcode() == id) {
				index = x;
				
				return nodes[x];
			}
		}
		
		return null;
	}
	
	public AbstractInsnNode nextInsn() {
		index++;
		return nodes[index];
	}
	
	public AbstractInsnNode prevInsn() {
		index--;
		return nodes[index];
	}

	public int position() {
		return index;
	}
}
