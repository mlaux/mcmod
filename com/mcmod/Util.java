package com.mcmod;

import java.io.File;

public class Util {
	public static File getWorkingDirectory(String file) {
		String userHome = System.getProperty("user.home");
		String os = System.getProperty("os.name").toLowerCase();
		
		File workingDirectory = null;
		
		if(os.contains("win")) {
			String data = System.getenv("APPDATA");
			
			if(data != null) {
				workingDirectory = new File(data, "." + file);
			} else {
				workingDirectory = new File(userHome, "." + file);
			}
		} else if(os.contains("unix")) {
			workingDirectory = new File(userHome, "." + file);
		} else if(os.contains("mac")) {
			workingDirectory = new File(userHome, "Library/Application Support/" + file);
		} else {
			workingDirectory = new File(userHome, file);
		}
		
		return workingDirectory;
	}
}
