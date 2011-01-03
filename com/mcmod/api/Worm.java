package com.mcmod.api;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.mcmod.shared.Accessor;

public class Worm {
	private Object object = null;
	private Class<?> objectClass;
	private Map<Accessor, Field> fieldCache = new HashMap<Accessor, Field>();
	
	public Worm(Object object) {
		this.object = object;
		this.objectClass = object.getClass();
	}
	
	public Object get(String fieldName) {
		try {
			return getField(fieldName).get(object);
		} catch (Exception e) {
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
		Accessor acc = Data.accessors.get(name);
		Field field = fieldCache.get(acc);
		
		if(field == null) {
			try {
				String fName = acc.getItemName();
				field = objectClass.getField(fName);
				field.setAccessible(true);
				fieldCache.put(acc, field);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return field;
	}
}
