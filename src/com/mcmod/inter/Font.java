package com.mcmod.inter;

import java.nio.IntBuffer;

public interface Font {
	public IntBuffer getListIDBuffer();
	
	public int getTextureID();
	
	public int[] getCharWidths();
	
	public int getListBase();
	
	public void drawStringPlain(String s, int x, int y, int col);

	public void drawStringShadow(String s, int x, int y, int col);

	public int getStringWidth(String s);
}
