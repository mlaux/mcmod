package com.mcmod.injection;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

class FieldID {

    public String humanName;
    public ClassNode fieldClass;
    public FieldNode field;

    public FieldID(String hn, ClassNode fc, FieldNode fn) {
        humanName = hn;
        fieldClass = fc;
        field = fn;
        System.out.println("  - " + humanName + " -> " + fieldClass.name + "." + field.name + "(" + field.desc + ")");
    }

    public void inject() {
        String methodName = Character.toUpperCase(humanName.charAt(0)) + humanName.substring(1);
        boolean isStatic = (field.access & Opcodes.ACC_STATIC) != 0;

        String sigType = field.desc;
        String check = sigType.replace("[", "");
        String classType = null;
        if (check.startsWith("L")) {
            String name = check.substring(1, check.length() - 1);
            String inter = Injector.getInterfaceName(name);
            if (inter != null) {
                sigType = sigType.replace(name, inter);
                classType = name;
            }
        }

        MethodNode getterMethod = new MethodNode(Opcodes.ACC_PUBLIC, "get" + methodName, "()" + sigType, null, null);

        InsnList getterList = getterMethod.instructions;
        if (!isStatic) {
            getterList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        }

        int op = isStatic ? Opcodes.GETSTATIC : Opcodes.GETFIELD;
        getterList.add(new FieldInsnNode(op, fieldClass.name, field.name, field.desc));
        getterList.add(new InsnNode(Type.getType(sigType).getOpcode(Opcodes.IRETURN)));

        if (isStatic) {
            McClassLoader.getStaticClass().methods.add(getterMethod);
        } else {
            fieldClass.methods.add(getterMethod);
        }

        if (sigType.startsWith("[")) {
            return; // Can't do setters on array because of casting
        }
        MethodNode setterMethod = new MethodNode(Opcodes.ACC_PUBLIC, "set" + methodName, "(" + sigType + ")V", null, null);

        InsnList setterList = setterMethod.instructions;

        if (!isStatic) {
            setterList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        }
        setterList.add(new VarInsnNode(Type.getType(sigType).getOpcode(Opcodes.ILOAD), 1));

        if (classType != null) {
            setterList.add(new TypeInsnNode(Opcodes.CHECKCAST, classType));
        }

        op = isStatic ? Opcodes.PUTSTATIC : Opcodes.PUTFIELD;
        setterList.add(new FieldInsnNode(op, fieldClass.name, field.name, field.desc));
        setterList.add(new InsnNode(Opcodes.RETURN));

        if (isStatic) {
            McClassLoader.getStaticClass().methods.add(setterMethod);
        } else {
            fieldClass.methods.add(setterMethod);
        }

        System.out.println(methodName + " " + sigType + " " + classType);
    }
}
