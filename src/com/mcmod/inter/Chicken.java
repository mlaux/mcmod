package com.mcmod.inter;

public interface Chicken extends FriendlyMob {

	/**
	 * @return the amount of time before the chicken lays it's next egg.
	 *		 the time is in Minecraft units.
	 */
	public int getEggTimer();

	/**
	 * Sets the time until the chicken will lay it's next egg.
	 * @param x the amount of time in minecraft units until the chicken will lay an egg.
	 */
	public void setEggTimer(int x);
}
