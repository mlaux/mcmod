package com.mcmod.inter;

public interface World {
	public long getTime();
	public int getSpawnX();
	public int getSpawnY();
	public int getSpawnZ();
	
	public void setTime(long l);	
	public void setSpawnX(int x);
	public void setSpawnY(int y);
	public void setSpawnZ(int z);
}
