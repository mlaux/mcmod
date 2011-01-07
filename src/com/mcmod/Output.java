package com.mcmod;

import java.awt.Component;
import java.awt.event.KeyEvent;

public class Output {
	public static void sendString(String s) {
		sendKey(Loader.getCanvas(), (char) KeyEvent.VK_ENTER);
		for(char ch : s.toCharArray())
			sendKey(Loader.getCanvas(), ch);
		sendKey(Loader.getCanvas(), (char) KeyEvent.VK_ENTER);
	}

	public static void sendKey(Component comp, char ch) {
		int code = ch;
		if(ch >= 'a' && ch <= 'z')
			code -= 32;
		
		KeyEvent e;
		e = new KeyEvent(comp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, code, ch);
		comp.dispatchEvent(e);
		e = new KeyEvent(comp, KeyEvent.KEY_TYPED, System.currentTimeMillis(),
				0, 0, ch, 0);
		comp.dispatchEvent(e);
		e = new KeyEvent(comp, KeyEvent.KEY_RELEASED,
				System.currentTimeMillis(), 0, code, ch);
		comp.dispatchEvent(e);
	}
}