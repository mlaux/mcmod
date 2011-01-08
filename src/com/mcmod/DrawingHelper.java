package com.mcmod;

import org.lwjgl.opengl.GL11;

import com.mcmod.inter.Font;

public class DrawingHelper {
	public static void drawRect(int x, int y, int width, int height) {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		{
			GL11.glVertex2i(x, y);
			GL11.glVertex2i(x, y + height);
			GL11.glVertex2i(x + width, y + height);
			GL11.glVertex2i(x + width, y);
			GL11.glVertex2i(x, y);
		}
		GL11.glEnd();
	}
	
	public static void drawPlainString(String s, int x, int y, int color) {
		Font f = Loader.getMinecraft().getFont();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		{
			f.drawStringPlain(s, x, y, color);
		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public static void drawShadowString(String s, int x, int y, int color) {
		Font f = Loader.getMinecraft().getFont();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		{
			f.drawStringShadow(s, x, y, color);
		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
}
