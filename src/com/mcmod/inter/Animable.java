package com.mcmod.inter;

public interface Animable extends Entity {
	/**
	 * Health of the Animable object.  The max health is
	 * 20, for every 2 health the Animable object loses, 
	 * it will lose 1 heart.
	 * 
	 * @return the Animable object's health.
	 */
	public int getHealth();
	
	public InfoMap getInfoMap();
	
	/**
	 * @return NFI.
	 */
	public int getDeathTime();
	
	/**
	 * @return NFI.
	 */
	public int getHurtTime();
	
	/**
	 * @return NFI.
	 */
	public int getAttackTime();
	
	/**
	 * Set's the Animable object's health.
	 * @param i the new health.
	 * @see getHealth();
	 */
	public void setHealth(int i);
}
