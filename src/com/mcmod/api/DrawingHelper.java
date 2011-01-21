package com.mcmod.api;

import org.lwjgl.opengl.GL11;

import com.mcmod.Loader;
import com.mcmod.inter.Font;

public class DrawingHelper {

    /**
     * Draws the 2D rectangle described by the specified x, y, width, and
     * height using OpenGL.
     * @param x X coordinate of top-left corner
     * @param y Y coordinate of top-left corner
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     */
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

    /**
     * Draws the String 's' at the specified (x, y) coordinates using the
     * specified color, in 32-bit unsigned 0xAARRGGBB format. The drawn string
     * will not have a shadow behind it.
     * @param s String to draw
     * @param x X position to draw the top-left corner of the string
     * @param y Y position to draw the top-left corner of the string
     * @param col Color of the string, in 32-bit packed int 0xAARRGGBB format.
     */
    public static void drawPlainString(String s, int x, int y, int color) {
        Font f = Loader.getMinecraft().getFont();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        {
            f.drawStringPlain(s, x, y, color);
        }
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Draws the String 's' at the specified (x, y) coordinates using the
     * specified color, in 32-bit unsigned 0xAARRGGBB format. The drawn string
     * will have a black shadow behind it.
     * @param s String to draw
     * @param x X position to draw the top-left corner of the string
     * @param y Y position to draw the top-left corner of the string
     * @param col Color of the string, in 32-bit packed int 0xAARRGGBB format.
     */
    public static void drawShadowString(String s, int x, int y, int color) {
        Font f = Loader.getMinecraft().getFont();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        {
            f.drawStringShadow(s, x, y, color);
        }
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
}
