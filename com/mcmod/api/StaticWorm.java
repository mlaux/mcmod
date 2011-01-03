package com.mcmod.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import com.mcmod.Loader;
import com.mcmod.shared.Accessor;

public class StaticWorm {
	public static Object get(String name) {
		try {
			Accessor acc = lookupField(name);
			return Loader.getClass(acc.getClassName()).getField(acc.getItemName()).get(null);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int getInt(String name) {
		try {
			Accessor acc = lookupField(name);
			return Loader.getClass(acc.getClassName()).getField(acc.getItemName()).getInt(null);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	public static void setField(Object on, String fieldName, Object data) {
		try {
			Class<?> clazz = on.getClass();
			Field field = clazz.getField(fieldName);
			boolean accessible = field.isAccessible();
			
			if(!accessible)
				field.setAccessible(true);
			
			field.set(on, data);
			
			if(!accessible) 
				field.setAccessible(false);
		} catch(Exception e) {
			System.err.println("Error setting field " + fieldName + " on " + on + " to " + data);
			e.printStackTrace();
		}
	}
	
	public static Object instantiate(String name, Object... args) {
		try {
		//	Class<?>[] types = new Class<?>[args.length];
		//	for(int k = 0; k < args.length; k++)
		//		types[k] = args[k].getClass(); TODO This chokes when null is an arg
			Constructor<?> cns = Loader.getClass(name).getConstructors()[0];
			return cns.newInstance(args);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Accessor lookupField(String field) {
		return Data.accessors.get(field);
	}
}
