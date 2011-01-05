package com.mcmod.inter;

import java.util.List;

public interface MainMenu extends Menu {
	public String getExtraString();
	
	@SuppressWarnings("rawtypes")
	public List getButtonList();
}
