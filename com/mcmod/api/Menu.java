package com.mcmod.api;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private Worm worm;
	
	public Menu(Object o) {
		System.out.println("Menu: " + o.getClass().getSimpleName());
		this.worm = new Worm(o);
	}
	
	public List<Button> getButtonList() {
		List<Button> buttons = new ArrayList<Button>();

		for(Object o : (List) worm.get("Menu.buttonList")) {
			buttons.add(new Button(o));
		}
		
		return buttons;
	}
}
