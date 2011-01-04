package com.mcmod.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mcmod.shared.Accessor;

public class Worm {
	private Object object = null;
	private Class<?> objectClass;
	private Map<Accessor, Field> fieldCache = new HashMap<Accessor, Field>();
	private Map<Accessor, Method> methodCache = new HashMap<Accessor, Method>();
	
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
	
	public void invoke(String name, Object... args) {
		try {
			getMethod(name).invoke(object, args);
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
	
	public Method getMethod(String name) {
		Accessor acc = Data.accessors.get(name);
		Method mth = methodCache.get(acc);
		
		if(mth == null) {
			try {
				String fName = acc.getItemName();
				mth = objectClass.getMethod(fName, getArgumentTypes(acc.getItemSignature()));
				mth.setAccessible(true);
				methodCache.put(acc, mth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return mth;
	}
	
	/**
	 * Credits: SCE lolol
	 * Returns the Class<?> array for the specified method signature
	 * @param signature
	 * @return
	 * @throws ClassNotFoundException
	 */
	private Class<?>[] getArgumentTypes(String signature) throws ClassNotFoundException {
		String types = signature.substring(signature.indexOf('(') + 1, signature.indexOf(')'));
		List<Class<?>> ret = new ArrayList<Class<?>>();
		for(int k = 0; k < types.length(); k++) {
			switch(types.charAt(k)) {
				case 'B': ret.add(Byte.TYPE); break;
				case 'S': ret.add(Short.TYPE); break;
				case 'I': ret.add(Integer.TYPE); break;
				case 'C': ret.add(Character.TYPE); break;
				case 'Z': ret.add(Boolean.TYPE); break;
				case 'J': ret.add(Long.TYPE); break;
				case 'F': ret.add(Float.TYPE); break;
				case 'D': ret.add(Double.TYPE); break;
				case 'L':
					String type = types.substring(k + 1, types.indexOf(';', k));
					type = type.replace("/", ".");
					ret.add(Class.forName(type));
					k += type.length();
					break;
			}
		}
		return ret.toArray(new Class<?>[ret.size()]);
	}
}
