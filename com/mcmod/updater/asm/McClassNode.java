package com.mcmod.updater.asm;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class McClassNode extends ClassNode {
	public List<FieldNode> static_fields = new ArrayList<FieldNode>();
	public List<FieldNode> instance_fields = new ArrayList<FieldNode>();
	public List<MethodNode> static_methods = new ArrayList<MethodNode>();
	public List<MethodNode> instance_methods = new ArrayList<MethodNode>();
	
	public Map<String, String> identified_fields = new HashMap<String, String>();
	
	public Map<Object, List<McMethodNode>> constants = new HashMap<Object, List<McMethodNode>>();
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldNode field = new FieldNode(access, name, desc, signature, value);
		
		if(Modifier.isStatic(access)) {
			static_fields.add(field);
		} else {
			instance_fields.add(field);
		}
	
		fields.add(field);
		
		return field;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		McMethodNode method = new McMethodNode(this, access, name, desc, signature, exceptions);
		
		if(Modifier.isStatic(access)) {
			static_methods.add(method);
		} else {
			instance_methods.add(method);
		}
		
		methods.add(method);
		
		return method;
	}
}
