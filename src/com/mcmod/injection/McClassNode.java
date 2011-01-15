package com.mcmod.injection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;


public class McClassNode extends ClassNode {
	public List<FieldNode> staticFields = new ArrayList<FieldNode>();
	public List<FieldNode> instanceFields = new ArrayList<FieldNode>();
	
	public List<MethodNode> staticMethods = new ArrayList<MethodNode>();
	public List<MethodNode> instanceMethods = new ArrayList<MethodNode>();
	
	public Map<Object, List<McMethodNode>> constants = new HashMap<Object, List<McMethodNode>>();
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldNode field = new FieldNode(access, name, desc, signature, value);
		
		if((access & Opcodes.ACC_STATIC) != 0) {
			staticFields.add(field);
		} else {
			instanceFields.add(field);
		}
	
		fields.add(field);
		
		return field;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		McMethodNode method = new McMethodNode(this, access, name, desc, signature, exceptions);
		
		if((access & Opcodes.ACC_STATIC) != 0) {
			staticMethods.add(method);
		} else {
			instanceMethods.add(method);
		}
		
		methods.add(method);
		
		return method;
	}
}
