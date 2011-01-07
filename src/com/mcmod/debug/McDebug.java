package com.mcmod.debug;

public abstract class McDebug {
	public abstract String getName();
	public abstract void render();
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof McDebug) {
			return ((McDebug) o).getName().equals(getName());
		}
		
		return false;
	}
}
