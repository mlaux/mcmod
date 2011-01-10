package com.mcmod.inter;

public interface Pig extends FriendlyMob {
	/**
	 * @return true if the pig is saddled, false if not
	 */
	public boolean getSaddled();
	
	public void setSaddled(boolean saddled);
}
