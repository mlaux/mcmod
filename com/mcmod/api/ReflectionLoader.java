package com.mcmod.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.mcmod.Loader;

public class ReflectionLoader {
	public static Map<String, String> reflection_data = new HashMap<String, String>();
	
	static {
		BufferedReader br = new BufferedReader(new InputStreamReader(Loader.class.getResourceAsStream("hooks.dat")));
	
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				// Minecraft:inventory:g
				char c = line.charAt(0);
				line = line.substring(1);
				
				switch(c) {
				case 'f':
					String[] data = line.split(":");
					
					if(data.length != 3)
						throw new RuntimeException("Invalid hooks.dat");
					
					reflection_data.put(data[0] + "." + data[1], data[2]);
					break;
				case 'c':
					data = line.split(":");
					
					if(data.length != 2)
						throw new RuntimeException("Invalid hooks.dat");
					
					reflection_data.put(data[0], data[1]);
					break;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
