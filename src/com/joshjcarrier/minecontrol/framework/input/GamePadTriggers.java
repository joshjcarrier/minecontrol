package com.joshjcarrier.minecontrol.framework.input;

/**
 * The trigger positions on the game pad.
 * @author joshjcarrier
 *
 */
public class GamePadTriggers
{
	private final float left;
	private final float right;
	
	public GamePadTriggers(float left, float right)
	{
		this.left = left;
		this.right = right;
	}
	
	public float getLeft()
	{
		return this.left;
	}
	
	public float getRight()
	{
		return this.right;
	}
}
