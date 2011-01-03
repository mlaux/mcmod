package com.mcmod.api;


public class Player extends Humanoid {
	private Worm worm;
	
	public Player(Object o) {
		super(o);
		this.worm = new Worm(o);
	}
}
