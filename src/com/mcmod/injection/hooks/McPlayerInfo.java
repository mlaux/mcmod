package com.mcmod.injection.hooks;

import java.util.List;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.mcmod.injection.InstructionSearcher;
import com.mcmod.injection.McClassNode;
import com.mcmod.injection.McHook;
import com.mcmod.injection.McMethodNode;

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
        FieldInsnNode mcfield = searcher.nextFieldInsn();

        identifyClass(mcfield.owner, "Minecraft");

        McClassNode mc = getIdentifiedClass("Minecraft");
        for (FieldNode fn : mc.instanceFields) {
            if (fn.desc.equals("L" + getIdentifiedClass("Font").name + ";")) {
                identifyField("font", mc, fn);
            }
        }

        for (MethodNode mn : mc.instanceMethods) {
            if (!mn.name.equals("run")) {
                continue;
            }
            InstructionSearcher is = new InstructionSearcher(mn);
            is.nextLdcInsn("Post render");
            identifyInject("com/mcmod/Loader", "onRender", mn, is.position());
        }

        field = searcher.nextFieldInsn();

        identifyClass(field.owner, "PlayerInfo");
        identifyField("username", field);

        identifyField("playerInfo", mcfield);

        for (int x = 0; x < 3; x++) {
            field = searcher.nextFieldInsn();
        }

        identifyField("sessionID", field);

        for (int x = 0; x < 3; x++) {
            field = searcher.nextFieldInsn();
        }

        identifyField("password", field);
    }
}
