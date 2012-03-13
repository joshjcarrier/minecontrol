package com.joshjcarrier.minecontrol.framework.input;

import com.joshjcarrier.minecontrol.framework.Vector2;

/**
 * The position of the left and right game pad thumb sticks.
 * @author joshjcarrier
 *
 */
public class GamePadThumbSticks
{
	private final Vector2 left;
	private final Vector2 right;
	
	public GamePadThumbSticks(float leftX, float leftY, float rightX, float rightY)
	{
		this.left = new Vector2(leftX, leftY);
		this.right = new Vector2(rightX, rightY);
	}

	public Vector2 getLeft()
	{
		return this.left;
	}
	
	public Vector2 getRight()
	{
		return this.right;
	}
}
