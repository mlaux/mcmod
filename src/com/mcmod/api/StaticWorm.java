package com.mcmod.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import com.mcmod.Loader;
import com.mcmod.shared.Accessor;

/**
 * Utility class for instantiation and field modification using 
 * reflection.
 * <br><br>
 * In this class, <i>renamed format</i> refers to the names given to classes
 * according to the McMod updater. For example, 'Player' is in renamed format
 * but 'aq' is not.
 */
public class StaticWorm {
	/**
	 * Gets the value of the field <i>name</i> as an Object.
	 * @param name Name of the field in renamed format
	 * @return Value of the field
	 */
	public static Object get(String name) {
		try {
			Accessor acc = lookupField(name);
			return Loader.getClass(acc.getClassName()).getField(acc.getItemName()).get(null);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the value of the field <i>name</i> as an int.
	 * @param name Name of the field in renamed format
	 * @return Value of the field
	 */
	public static int getInt(String name) {
		try {
			Accessor acc = lookupField(name);
			return Loader.getClass(acc.getClassName()).getField(acc.getItemName()).getInt(null);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	/**
	 * Sets the value of fieldName on the specified object <i>on</i> to the
	 * value <i>data</i>.
	 * @param on Instance of the object to set the field on
	 * @param fieldName Name of the field to set, in renamed format.
	 * @param data Value to set the field to.
	 */
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
	
	/**
	 * Creates an instance of the named class, using the constructor with
	 * <i>types</i> argument types and <i>args</i> argument values.
	 * @param name Name of the class to instantiate, in renamed format.
	 * Not the obfuscated class name from the Minecraft client.
	 * @param types Parameter types to the constructor
	 * @param args Values to pass to the constructor
	 * @return A new instance of the class.
	 */
	public static Object instantiate(String name, Class<?>[] types, Object... args) {
		try {
			Constructor<?> cns = Loader.getClass(name).getConstructor(types);
			return cns.newInstance(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Creates an instance of the named class, using the first constructor
	 * in the object's .class file definition.
	 * @param name Name of the class to instantiate, in renamed format.
	 * Not the obfuscated class name from the Minecraft client.
	 * @param args Values to pass to the constructor
	 * @return A new instance of the class.
	 */
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
