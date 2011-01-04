package com.mcmod.shared;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class Inject {
	private String callClass;
	private String callMethod;
	
	private String injectClass;
	private String injectMethod;
	private String injectMethodSig;
	
	private int injectPosition;
	
	public Inject(String cc, String cm, String ic, String im, String ims, int ip) {
		callClass = cc;
		callMethod = cm;
		
		injectClass = ic;
		injectMethod = im;
		injectMethodSig = ims;
		
		injectPosition = ip;
		
		System.out.println(ic);
	}
	
	public String getCallClass() {
		return callClass;
	}
	
	public String getCallMethod() {
		return callMethod;
	}
	
	public String getInjectClass() {
		return injectClass;
	}
	
	public String getInjectMethod() {
		return injectMethod;
	}
	
	public String getInjectMethodSignature() {
		return injectMethodSig;
	}
	
	public int getInjectPosition() {
		return injectPosition;
	}

	public void process(ClassNode cn) {
		MethodNode mn = null;
		for(Object _mn : cn.methods) {
			MethodNode check = (MethodNode) _mn;
			if(check.name.equals(injectMethod) && check.desc.equals(injectMethodSig))
				mn = check;
		}
		
		if(mn == null) {
			System.out.println("Unable to find method for injecting callback in " + cn);
			return;
		}
		
		MethodInsnNode min = new MethodInsnNode(Opcodes.INVOKESTATIC, callClass, callMethod, "()V");
		mn.instructions.insert(mn.instructions.get(injectPosition), min);
		
		System.out.println("Seems like a good injection.");
	}
}
