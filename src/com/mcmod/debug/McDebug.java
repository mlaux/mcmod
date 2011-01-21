package com.mcmod.debug;

public abstract class McDebug {

    public abstract String getName();

    public abstract void render();

    public boolean equals(Object o) {
        if (o instanceof McDebug) {
            return ((McDebug) o).getName().equals(getName());
        }

        return false;
    }

    public static McDebug[] getDebugs() {
        return new McDebug[]{new LocationDebug(), new WorldDebug(), new PlayerDebug()};
    }
}
