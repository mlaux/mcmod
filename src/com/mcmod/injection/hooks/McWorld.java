package com.mcmod.injection.hooks;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;

public class McWorld extends McHook {

    public boolean canProcess(McClassNode node) {
        return node.constants.get("Time") != null;
    }

    public void process(McClassNode node) {
        identifyClass(node, "World");

        MethodNode method = null;

        for (MethodNode mn : node.constants.get("Time")) {
            if (!mn.desc.contains("File")) {
                method = mn;
                break;
            }
        }

        String[] ldcs = {"SpawnX", "SpawnY", "SpawnZ", "Time"};
        InstructionSearcher searcher = new InstructionSearcher(method);

        FieldInsnNode fin = null;

        for (String s : ldcs) {
            searcher.nextLdcInsn(s);
            fin = searcher.nextFieldInsn();

            identifyField(Character.toLowerCase(s.charAt(0)) + s.substring(1), fin);
        }

        McClassNode minecraft = getIdentifiedClass("Minecraft");

        for (FieldNode field : minecraft.instanceFields) {
            if (field.desc.equals("L" + node.name + ";")) {
                identifyField("world", minecraft, field);
            }
        }

        method = node.constants.get("Player count: ").get(0);
        searcher = new InstructionSearcher(method);
        searcher.nextLdcInsn("Player count: ");

        String[] names = {"playerList", "entityList"};

        for (String s : names) {
            fin = searcher.nextFieldInsn();
            identifyField(s, fin);
        }

        int lowest = Integer.MAX_VALUE;

        for (MethodNode mn : node.instanceMethods) {
            if (mn.desc.equals("(IIII)Z")) {
                searcher = new InstructionSearcher(mn);
                if (searcher.count(Opcodes.INVOKEVIRTUAL) == 2) {
                    int size = searcher.size();

                    if (size < lowest) {
                        /* Hackish, but works =/ */
                        lowest = size;
                        method = mn;
                    }
                }
            }
        }

        identifyMethod("setBlock", node, method);
    }
}
