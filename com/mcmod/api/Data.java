package com.mcmod.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.mcmod.Loader;
import com.mcmod.shared.Accessor;
import com.mcmod.shared.Inject;

public class Data {
	public static Map<String, String> classes = new HashMap<String, String>();
	public static Map<String, Accessor> accessors = new HashMap<String, Accessor>();
	public static Map<String, Inject> injections = new HashMap<String, Inject>();
	public static Map<String, String> interfaces = new HashMap<String, String>();
	
	static {
		BufferedReader br = new BufferedReader(new InputStreamReader(Loader.class.getResourceAsStream("hooks.dat")));
		String line;
		
		try {
			System.out.println("Loading Accessors...");
			while((line = br.readLine()) != null) {
				// File format is type:data:[data:[data...]]
				
				// c:realName:className
				// c:PlayerInfo:eh
				
				// f:mnemonic:className:fieldName:signature
				// f:playerInfo.username:eh:b:Ljava/lang/String;
				// m is same as f
				
				// i:callclass:callmethod:realclass:realmethod:signature:index
				
				// Lines beginning with # are ignored
				
				if(line.startsWith("#"))
					continue;
				
				String[] data = line.split(":");
				char c = data[0].charAt(0);
				
				switch(c) {
					case 'f':
					case 'm':
						if(data.length != 6)
							throw new RuntimeException("Invalid hooks.dat");
						
						accessors.put(data[1], new Accessor(data[2], data[3], data[4], data[5].equals("s")));
						
						System.out.println("Loaded Accessor: " + data[1]);
						break;
					case 'c':
						if(data.length != 3)
							throw new RuntimeException("Invalid hooks.dat");
						
						classes.put(data[1], data[2]);
						System.out.println("Loaded Class: " + data[1]);
						
						interfaces.put("L" + data[2] + ";", "Lcom/mcmod/inter/" + data[1] + ";");
						break;
					case 'i':
						System.out.println("Loaded injection: " + data[3]);
						if(data.length != 7)
							throw new RuntimeException("Invalid hooks.dat");
						
						injections.put(data[3], new Inject(data[1], data[2], data[3], data[4], data[5], Integer.parseInt(data[6])));
						break;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finished.");
	}
}
