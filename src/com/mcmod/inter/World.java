package com.mcmod.inter;

import java.util.List;

public interface World {

	/**
	 * @return The time of day in this World, as a <code>long</code> integer
	 * from 0 to 23999.
	 */
	public long getTime();

	/**
	 * @return the X coordinate of the player spawn point in this World
	 */
	public int getSpawnX();

	/**
	 * @return the Y coordinate of the player spawn point in this World
	 */
	public int getSpawnY();

	/**
	 * @return the Z coordinate of the player spawn point in this World
	 */
	public int getSpawnZ();

	/**
	 * Sets the time in this World to the specified value.
	 * @param l Time, expressed as a <code>long</code> from 0 to 23999.
	 */
	public void setTime(long l);

	public void setSpawnX(int x);

	public void setSpawnY(int y);

	public void setSpawnZ(int z);

	/**
	 * @return a List of players in this World
	 */
	@SuppressWarnings("rawtypes")
	public List getPlayerList();

	/**
	 * @return a List of entities (players and mobs) in this World
	 */
	@SuppressWarnings("rawtypes")
	public List getEntityList();

	/**
	 * Sets the block at (x, y, z) in tile coordinates to <code>id</code>.
	 * @param x X coordinate of block
	 * @param y Y coordinate of block
	 * @param z Z coordinate of block
	 * @param id ID to set the block to
	 * @return
	 */
	public boolean setBlock(int x, int y, int z, int id);
	
	/**
	 * Gets the block ID at (x, y, z) 
	 * @param x X coordinate of the block
	 * @param y Y coordinate of the block
	 * @param z Z coordinate of the block
	 * @return The id of the block
	 */
	public int getBlockId(int x, int y, int z);
}
