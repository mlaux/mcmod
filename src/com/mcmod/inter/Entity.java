package com.mcmod.inter;

public interface Entity {

	/**
	 * @return the X coordinate of the Entity.
	 */
	public double getX();

	/**
	 * @return the Y coordinate of the Entity.
	 */
	public double getY();

	/**
	 * @return the Z coordinate of the Entity.
	 */
	public double getZ();

	/**
	 * @return the X speed of the Entity.
	 */
	public double getSpeedX();

	/**
	 * @return the Y speed of the Entity.
	 */
	public double getSpeedY();

	/**
	 * @return the Z speed of the Entity.
	 */
	public double getSpeedZ();

	/**
	 * @return the x-rotation of the Entity.
	 */
	public float getRotationX();

	/**
	 * @return the y-rotation of the Entity.
	 */
	public float getRotationY();

	/**
	 * @return whether or not the Entity is on the ground.
	 */
	public boolean getOnGround();

	/**
	 * NOTE: This can be used for no fall damage.  Flyer actually
	 * takes advantage of this, so when you're landing from flying
	 * you won't die.
	 *
	 * @return how far above ground the Entity is.
	 */
	public float getFallDistance();

	/**
	 * @return how long the fire on the Entity will last; if the Entity
	 * 		   is not on fire, this will return -20.
	 */
	public int getFireTimer();

	/**
	 * This field is kindof unique.  It starts out at 300 (Which means you
	 * have all your air bubbles.) whenever this field gets down to 0 that
	 * means you're out of air. The field continues to tick down until it
	 * gets to -20, at which point it takes away 1 heart and reset AirTimer
	 * to 0.
	 *
	 * @return the amount of air the Entity has.
	 */
	public int getAirTimer();

	public void setSpeedX(double x);

	public void setSpeedY(double y);

	public void setSpeedZ(double z);

	/**
	 * Sets the Entity's air timer.
	 *
	 *
	 * @param timer the amount of air the Entity has.
	 * @see getAirTimer();
	 */
	public void setAirTimer(int timer);

	/**
	 * Sets the Entity's fire timer.
	 * @param timer the duration of the fire on the enemy.
	 * @see getFireTimer();
	 */
	public void setFireTimer(int timer);

	/**
	 * Sets the Entity's fall distance.
	 *
	 * @param f the Entity's fall distance.
	 * @see getFallDistance();
	 */
	public void setFallDistance(float f);

	/**
	 * Sets whether or not the Entity is on the ground.
	 *
	 * @param b whether the Entity is on the ground or not.
	 * @see getOnGround();
	 */
	public void setOnGround(boolean b);

	/**
	 * Teleports the Entity to the specified X,Y,Z coordinate.
	 *
	 * @param x the X coordinate to teleport the Entity to.
	 * @param y the Y coordinate to teleport the Entity to.
	 * @param z the Z coordinate to teleport the Entity to.
	 */
	public void setPosition(double x, double y, double z);
}
