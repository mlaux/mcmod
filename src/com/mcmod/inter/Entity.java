package com.mcmod.inter;

public interface Entity {
	public double getX();
	public double getY();
	public double getZ();
	
	public double getSpeedX();
	public double getSpeedY();
	public double getSpeedZ();
	
	public float getRotationX();
	public float getRotationY();
	
	public void setPosition(double x, double y, double z);
}
