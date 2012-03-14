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
	private int sensitivityX = 20, sensitivityY = 20;
	
	public ReplayState replay(ReplayState replayState, Point2D analogInput, Robot humanInterfaceDeviceService)
	{
		Point2D moveMouse = analogInput;
		if (moveMouse.getX() != 0 || moveMouse.getY() != 0)
		{
			PointerInfo info = MouseInfo.getPointerInfo();
			
			// TODO 2.0+ smoothen mouse
			humanInterfaceDeviceService.mouseMove(
					(int) (info.getLocation().x + moveMouse.getX() * sensitivityX),
					(int) (info.getLocation().y + moveMouse.getY() * sensitivityY));
		}
		
		return replayState;
	}
}
