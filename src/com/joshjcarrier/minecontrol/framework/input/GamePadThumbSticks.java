package com.joshjcarrier.minecontrol.framework.input;

import java.awt.geom.Point2D;

/**
 * The position of the left and right game pad thumb sticks.
 * @author joshjcarrier
 *
 */
public class GamePadThumbSticks
{
	private final Point2D left;
	private final Point2D right;
	
	public GamePadThumbSticks(float leftX, float leftY, float rightX, float rightY)
	{
		this.left = new Point2D.Float(leftX, leftY);
		this.right = new Point2D.Float(rightX, rightY);
	}

	public Point2D getLeft()
	{
		return this.left;
	}
	
	public Point2D getRight()
	{
		return this.right;
	}
}
