package com.joshjcarrier.minecontrol.services;

/**
 * Convenience methods to interact with threads.
 * @author joshjcarrier
 *
 */
public final class ThreadHelper
{
	public static void Sleep(int milliseconds)
	{
		try
		{
			Thread.sleep(milliseconds);
		} 
		catch (InterruptedException e) 
		{ 
			/* do nothing */	
		}
	}
}
