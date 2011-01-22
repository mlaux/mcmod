package com.mcmod.injection;

import java.util.ArrayList;
import java.util.List;

public class Injector {

	private static final List<ClassID> classIDs = new ArrayList<ClassID>();
	private static final List<FieldID> fieldIDs = new ArrayList<FieldID>();
	private static final List<MethodID> methodIDs = new ArrayList<MethodID>();
	private static final List<InjectionID> injectIDs = new ArrayList<InjectionID>();

	public static void addClass(ClassID cid) {
		classIDs.add(cid);
	}

	public static void addField(FieldID fid) {
		fieldIDs.add(fid);
	}

	public static void addMethod(MethodID mid) {
		methodIDs.add(mid);
	}

	public static void addInjection(InjectionID iid) {
		injectIDs.add(iid);
	}

	public static String getClassName(String interName) {
		for (ClassID cid : classIDs) {
			if (cid.interfaceName.equals(interName)) {
				return cid.classNode.name;
			}
		}
		return null;
	}

	public static McClassNode getIdentifiedClass(String interName) {
		for (ClassID cid : classIDs) {
			if (cid.interfaceName.equals(interName)) {
				return cid.classNode;
			}
		}
		return null;
	}

	public static String getInterfaceName(String className) {
		for (ClassID cid : classIDs) {
			if (cid.classNode.name.equals(className)) {
				return ClassID.INTERFACE_PACKAGE + cid.interfaceName;
			}
		}
		return null;
	}

	public static void inject() {
		for (ClassID cid : classIDs) {
			cid.inject();
		}
		for (FieldID fid : fieldIDs) {
			fid.inject();
		}
		for (MethodID mid : methodIDs) {
			mid.inject();
		}
		for (InjectionID iid : injectIDs) {
			iid.inject();
		}
	}
}
