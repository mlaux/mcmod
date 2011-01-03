package com.mcmod.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.mcmod.Loader;
import com.mcmod.shared.Accessor;

public class Data {
	public static Map<String, String> classes = new HashMap<String, String>();
	public static Map<String, Accessor> accessors = new HashMap<String, Accessor>();
	
	static {
		BufferedReader br = new BufferedReader(new InputStreamReader(Loader.class.getResourceAsStream("hooks.dat")));
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				// File format is type:data:[data:[data...]]
				
				// c:realName:className
				// c:PlayerInfo:eh
				
				// f:mnemonic:className:fieldName:signature
				// f:playerInfo.username:eh:b:Ljava/lang/String;
				
				// Lines beginning with # are ignored
				
				String[] data = line.split(":");
				char c = data[0].charAt(0);
				
				switch(c) {
					case 'f':
					case 'm':
						if(data.length != 5)
							throw new RuntimeException("Invalid hooks.dat");
						
						accessors.put(data[1], new Accessor(data[2], data[3], data[4]));
						break;
					case 'c':
						if(data.length != 3)
							throw new RuntimeException("Invalid hooks.dat");
						
						classes.put(data[1], data[2]);
						break;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
