package com.mcmod;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Output {
	private static Robot robot;
	
	static {
		try {
			robot = new Robot();
		} catch(Exception e) {}
	}
	
	public static void sendString(String s) {
		sendChar(KeyEvent.VK_ENTER);
		delay(1000);
		for(char c : s.toCharArray()) {
			sendChar(c);
		}
		
		delay(1000);
		sendChar(KeyEvent.VK_ENTER);
	}
	
	public static void sendChar(char c) {
		sendChar((int) c);
	}
	
	public static void sendChar(int code) {
		delay(200);
		robot.keyPress(code);
		delay(200);
		robot.keyRelease(code);
		delay(200);
	}
	
	private static void delay(int time) {
		try { Thread.sleep(time); } catch(Exception e) {}
	}
}
