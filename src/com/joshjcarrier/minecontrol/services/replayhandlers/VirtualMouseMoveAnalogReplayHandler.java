package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.geom.Point2D;

import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replays a mouse move event if the analog input reaches a threshold value.
 * @author joshjcarrier
 *
 */
public class VirtualMouseMoveAnalogReplayHandler implements IAnalogReplayHandler
{
	public static final int DefaultSensitivity = 20;
	public static final int DefaultSecondarySensitivity = 40;
	
	private int sensitivityX, sensitivityY, sensitivitySecondaryX, sensitivitySecondaryY;
	private byte invertY;
	
	public VirtualMouseMoveAnalogReplayHandler(
			int sensitivityX, 
			int sensitivityY,
			int sensitivitySecondaryX,
			int sensitivitySecondaryY,
			boolean invertY) 
	{
		this.sensitivityX = sensitivityX;
		this.sensitivityY = sensitivityY;
		this.sensitivitySecondaryX = sensitivitySecondaryX;
		this.sensitivitySecondaryY = sensitivitySecondaryY;
		
		if(invertY)
		{
			this.invertY = -1;	
		}
		else
		{
			this.invertY = 1;
		}
	}
	
	public VirtualMouseMoveAnalogReplayHandler() 
	{
		this(DefaultSensitivity, DefaultSensitivity, DefaultSecondarySensitivity, DefaultSecondarySensitivity, false);
	}
	
	public ReplayState replay(ReplayState replayState, Point2D analogInput, Robot humanInterfaceDeviceService)
	{
		Point2D moveMouse = analogInput;
		if (moveMouse.getX() != 0 || moveMouse.getY() != 0)
		{
			PointerInfo info = MouseInfo.getPointerInfo();
			
			// TODO 2.0+ smoothen mouse
			humanInterfaceDeviceService.mouseMove(
					(int) (info.getLocation().x + moveMouse.getX() * sensitivityX),
					(int) (info.getLocation().y + moveMouse.getY() * sensitivityY * this.invertY));
		}
		
		return replayState;
	}

	public int getSensitivityX() 
	{
		return this.sensitivityX;
	}

	public void setSensitivityX(int sensitivityX) 
	{
		this.sensitivityX = sensitivityX;
	}

	public int getSensitivityY() 
	{
		return this.sensitivityY;
	}

	public void setSensitivityY(int sensitivityY) 
	{
		this.sensitivityY = sensitivityY;
	}
	
	public int getSensitivitySecondaryX() 
	{
		return this.sensitivitySecondaryX;
	}

	public void setSensitivitySecondaryX(int sensitivityX) 
	{
		this.sensitivitySecondaryX = sensitivityX;
	}

	public int getSensitivitySecondaryY() 
	{
		return this.sensitivitySecondaryY;
	}

	public void setSensitivitySecondaryY(int sensitivityY) 
	{
		this.sensitivitySecondaryY = sensitivityY;
	}

	public boolean isInvertY() 
	{
		return this.invertY < 0;
	}

	public void setInvertY(boolean invertY) 
	{
		if (invertY)
		{
			this.invertY = -1;
		}
		else
		{
			this.invertY = 1;
		}
	}
}
