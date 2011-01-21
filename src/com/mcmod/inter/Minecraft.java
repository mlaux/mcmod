package com.mcmod.inter;

public interface Minecraft {

    /**
     * Gets the menu that is open, or <code>null</code> if none.
     * @return current menu
     */
    public Menu getCurrentMenu();

    /**
     * Gets the font that Minecraft uses to render text.
     * @return current font
     */
    public Font getFont();

    /**
     * Gets the PlayerInfo instance with the username, password, and session
     * ID of the local player.
     * @return
     */
    public PlayerInfo getPlayerInfo();

    public void setPlayerInfo(PlayerInfo pl);

    public String getURL();

    public void setURL(String url);

    /**
     * @return {@link Player} instance of the current player
     */
    public Player getPlayer();

    public Item[] getItemCache();

    public void setItemCache(Item[] it); // wat use lol

    /**
     * @return World object that the player is in
     */
    public World getWorld();

    public CraftingManager getCraftingManager();

    /**
     * Utility function for getting sines in minecraft's fixed
     * point format.
     * @param f float to find the sine of
     * @return sin(f)
     */
    public float sin(float f);

    /**
     * Utility function for getting cosines in minecraft's fixed
     * point format.
     * @param f float to find the cosine of
     * @return cos(f)
     */
    public float cos(float f);
}
