package com.mcmod.inter;

public interface Sign extends TileEntity {

	/**
	 * @return the lines of text on this Sign
	 */
	public String[] getLines();

	public int getCurrentLine();
}
