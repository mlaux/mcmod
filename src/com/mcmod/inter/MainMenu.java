package com.mcmod.inter;

public interface MainMenu extends Menu {

    /**
     * @return returns that extra string that's pulsating at the end of Minecraft.
     */
    public String getExtraString();

    /**
     * Sets the extra string to 's'
     * @param s the extra string.
     *
     * @see getExtraString()
     */
    public void setExtraString(String s);
}
