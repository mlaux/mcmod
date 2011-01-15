package com.mcmod.api;

import java.lang.reflect.Constructor;

import com.mcmod.Loader;

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
}
