package com.mcmod.injection;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

class InjectionID {

	public String callClass;
	public String callMethod;
	public MethodNode injectMethod;
	public int instructionPtr;

	public InjectionID(String cc, String cm, MethodNode im, int ip) {
		callClass = cc;
		callMethod = cm;
		injectMethod = im;
		instructionPtr = ip;
	}

	public void inject() {
		MethodInsnNode min = new MethodInsnNode(Opcodes.INVOKESTATIC, callClass, callMethod, "()V");
		injectMethod.instructions.insert(injectMethod.instructions.get(instructionPtr), min);
		System.out.println("  + Added callback to " + callClass + "." + callMethod + " in " + injectMethod.name + " at " + instructionPtr);
	}
}
