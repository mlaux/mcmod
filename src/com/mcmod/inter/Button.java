package com.mcmod.inter;

public interface Button {
	/**
	 * @return the height of the button.
	 */
	public int getHeight();
	
	/**
	 * @return the x coordinate of the button.
	 */
	public int getX();
	
	/**
	 * @return the y coordinate of the button.
	 */
	public int getY();
	
	/**
	 * @return the width of the button.
	 */
	public int getWidth();
	
	/**
	 * @return whether or not the button is enabled.
	 */
	public boolean isEnabled();
}
