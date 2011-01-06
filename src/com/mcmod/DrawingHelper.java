package com.mcmod;

import org.lwjgl.opengl.GL11;

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
}
