package com.mcmod.api;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Worm {
	private Object object = null;
	private Class<?> objectClass;
	private Map<String, Field> fieldCache = new HashMap<String, Field>();
	
	public Worm(Object object) {
		this.object = object;
		this.objectClass = object.getClass();
	}
	
	public Worm(Class<?> objectClass) {
		this.object = null;
		this.objectClass = objectClass;
	}
	
	public Object get(String fieldName) {
		try {
			return getField(fieldName).get(object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getInt(String fieldName) {
		try {
			return getField(fieldName).getInt(object);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public void set(String fieldName, Object value) {
		try {
			getField(fieldName).set(object, value);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setInt(String fieldName, int value) {
		try {
			getField(fieldName).setInt(object, value);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 	
	public Field getField(String name) {
		String s = ReflectionLoader.reflection_data.get(name);
		Field field = fieldCache.get(s);
		
		if(field == null) {
			try {
				System.out.println("Getting: \"" + name + "\"" + s);
				field = objectClass.getField(s);
				field.setAccessible(true);
				fieldCache.put(s, field);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return field;
	}
}
