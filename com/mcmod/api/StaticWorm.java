package com.mcmod.api;

import com.mcmod.Loader;

public class StaticWorm {
	public static Object get(String className, String name) {
		try {
			return getClass(className).getField(lookupField(name)).get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int getInt(String className, String name) {
		try {
			return getClass(className).getField(lookupField(name)).getInt(null);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static String lookupField(String field) {
		return ReflectionLoader.reflection_data.get(field);
	}
	
	public static Class<?> getClass(String name) {
		String n = ReflectionLoader.reflection_data.get(name);
		
		try {
			return Loader.classLoader.loadClass(n);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
