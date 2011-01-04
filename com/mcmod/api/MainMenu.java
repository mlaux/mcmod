package com.mcmod.api;

public class MainMenu extends Menu {
	private Worm worm;
	
	public MainMenu(Object o) {
		super(o);
		this.worm = new Worm(o);
	}
	
	public String getExtraString() {
		return (String) worm.get("MainMenu.extraString");
	}
	
	public void setExtraString(String s) {
		worm.set("MainMenu.extraString", s);
	}
}
