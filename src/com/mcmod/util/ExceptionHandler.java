package com.mcmod.util;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println("Exception in thread " + t);
		e.printStackTrace();
	}
}
