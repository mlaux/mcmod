package com.mcmod.inter;

import java.util.List;

public interface World {
	public long getTime();
	public int getSpawnX();
	public int getSpawnY();
	public int getSpawnZ();
	
	public void setTime(long l);	
	public void setSpawnX(int x);
	public void setSpawnY(int y);
	public void setSpawnZ(int z);
	
	@SuppressWarnings("rawtypes")
	public List getPlayerList();
	
	@SuppressWarnings("rawtypes")
	public List getEntityList();
}
