package com.mcmod.injection;

class ClassID {
	public static final String INTERFACE_PACKAGE = "com/mcmod/inter/";
	
	public String interfaceName;
	public McClassNode classNode;
	
	public ClassID(String in, McClassNode cn) {
		interfaceName = in;
		classNode = cn;
		System.out.println("- " + cn.name + " identified as " + INTERFACE_PACKAGE + interfaceName);
	}
	
	public void inject() {
		String name = INTERFACE_PACKAGE + interfaceName;
		if(!classNode.interfaces.contains(name))
			classNode.interfaces.add(name);
	}
}