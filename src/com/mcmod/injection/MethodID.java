package com.mcmod.injection;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

class MethodID {
	public String humanName;
	public ClassNode methodClass;
	public MethodNode method;
	
	public MethodID(String hn, ClassNode cn, MethodNode mn) {
		humanName = hn;
		methodClass = cn;
		method = mn;
		System.out.println("  - " + humanName + " -> " + methodClass.name + "." + method.name + "(" + method.desc + ")");
	}
	
	public void inject() {
		boolean isStatic = (method.access & Opcodes.ACC_STATIC) != 0;
		MethodNode proxyMethod = new MethodNode(Opcodes.ACC_PUBLIC,
				humanName, method.desc, null, null);
		InsnList list = proxyMethod.instructions;

		Type[] types = Type.getArgumentTypes(proxyMethod.desc);
		if (!isStatic)
			list.add(new VarInsnNode(Opcodes.ALOAD, 0));

		for (int x = 0; x < types.length; x++) {
			int op = types[x].getOpcode(Opcodes.ILOAD);
			list.add(new VarInsnNode(op,
					types[x].getSize() == 2 ? (2 * x) + 1 : x + 1));
		}

		int op = isStatic ? Opcodes.INVOKESTATIC : Opcodes.INVOKEVIRTUAL;
		list.add(new MethodInsnNode(op, methodClass.name, method.name, method.desc));
		list.add(new InsnNode(Type.getReturnType(method.desc).getOpcode(Opcodes.IRETURN)));
		
		if(isStatic)
			McClassLoader.getStaticClass().methods.add(proxyMethod);
		else methodClass.methods.add(proxyMethod);
	}
}