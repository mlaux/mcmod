package com.mcmod.shared;

/**
 * Storage class for fields or methods.
 */
public class Accessor {
	private String className;
	private String itemName;
	private String itemSignature;
	
	public Accessor(String c, String i, String s) {
		className = c;
		itemName = i;
		itemSignature = s;
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
	
	public String toString() {
		return className + "." + itemName + "(" + itemSignature + ")";
	}
}