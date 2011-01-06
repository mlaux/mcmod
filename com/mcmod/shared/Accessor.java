package com.mcmod.shared;

/**
 * Storage class for fields or methods.
 */
public class Accessor {
	private String className;
	private String itemName;
	private String itemSignature;
	
	private boolean isStatic;
	
	public Accessor(String c, String i, String s, boolean is) {
		className = c;
		itemName = i;
		itemSignature = s;
		isStatic = is;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public String getItemSignature() {
		return itemSignature;
	}
	
	public boolean isStatic() {
		return isStatic;
	}
	
	public String toString() {
		return className + "." + itemName + "(" + itemSignature + ")";
	}
}