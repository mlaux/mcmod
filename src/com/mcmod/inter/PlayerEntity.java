package com.mcmod.inter;

public interface PlayerEntity extends Entity {
	public int getHealth();
	
	public InfoMap getInfoMap();
	
	public int getDeathTime();
	
	public int getHurtTime();
	
	public int getAttackTime();
}
