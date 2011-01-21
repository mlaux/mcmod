package com.mcmod.inter;

import java.nio.IntBuffer;

public interface Font {

    /**
     * Gets a buffer with the display list names for this font,
     * as returned by glGenLists(). This is kind of useless.
     * @return IntBuffer filled with GL display list IDs
     */
    public IntBuffer getListIDBuffer();

    /**
     * Gets the ID of the font texture, as returned by glGenTextures().
     * @return font texture ID
     */
    public int getTextureID();

    /**
     * @return an array filled with the widths of each character in the
     * font, in pixels.
     */
    public int[] getCharWidths();

    /**
     * Returns the base list ID as returned by glGenLists(count).
     * @return base id
     */
    public int getListBase();

    /**
     * Draws the String 's' at the specified (x, y) coordinates using the
     * specified color, in 32-bit unsigned 0xAARRGGBB format. The drawn string
     * will not have a shadow behind it. Most people should use the drawString
     * methods in {@link DrawingHelper} because they manage the texture.
     * @param s String to draw
     * @param x X position to draw the top-left corner of the string
     * @param y Y position to draw the top-left corner of the string
     * @param col Color of the string, in 32-bit packed int 0xAARRGGBB format.
     */
    public void drawStringPlain(String s, int x, int y, int col);

    /**
     * Draws the String 's' at the specified (x, y) coordinates using the
     * specified color, in 32-bit unsigned 0xAARRGGBB format. The drawn string
     * will have a black shadow behind it. Most people should use the
     * drawString methods in {@link DrawingHelper} because they manage the
     * texture.
     * @param s String to draw
     * @param x X position to draw the top-left corner of the string
     * @param y Y position to draw the top-left corner of the string
     * @param col Color of the string, in 32-bit packed int 0xAARRGGBB format.
     */
    public void drawStringShadow(String s, int x, int y, int col);

    /**
     * Returns the width of the specified string in pixels, if it were
     * drawn with this Font. That is, it 'measures' the String.
     * @param s String to measure
     * @return the width of 's' in pixels
     */
    public int getStringWidth(String s);
}
