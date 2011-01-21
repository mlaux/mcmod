package com.mcmod.inter;

public interface Humanoid extends Animable {

    /**
     * @return the Humanoid's inventory.
     */
    public Inventory getInventory();

    /**
     * @return the Humanoid's name.
     */
    public String getName();
}
