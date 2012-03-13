package com.joshjcarrier.minecontrol.framework;

/**
 * A two-dimensional point.
 * @author joshjcarrier
 *
 */
public class Vector2
{
	private final float x;
	private final float y;
	
	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
}
