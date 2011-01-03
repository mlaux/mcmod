package com.mcmod.api;

public class PlayerEntity {
	private Worm worm;
	
	public PlayerEntity(Object o) {
		this.worm = new Worm(o);
	}
	
	public int getHealth() {
		return worm.getInt("PlayerEntity.health");
	}
	
	public int getAttackTime() {
		return worm.getInt("PlayerEntity.attackTime");
	}
	
	public int getHurtTime() {
		return worm.getInt("PlayerEntity.hurtTime");
	}
	
	public int getDeathTime() {
		return worm.getInt("PlayerEntity.deathTime");
	}
	
	public void setHealth(int health) {
		worm.setInt("PlayerEntity.health", health);
	}
	
	public void setAttackTime(int attackTime) {
		worm.setInt("PlayerEntity.attackTime", attackTime);
	}
	
	public void setHurtTime(int hurtTime) {
		worm.setInt("PlayerEntity.hurtTime", hurtTime);
	}
	
	public void setDeathTime(int deathTime) {
		worm.setInt("PlayerEntity.deathTime", deathTime);
	}
	
	public Object getInfoMap() {
		// TODO: Reverse this class and make a wrapper for it.
		return worm.get("PlayerEntity.infoMap");
	}
}
