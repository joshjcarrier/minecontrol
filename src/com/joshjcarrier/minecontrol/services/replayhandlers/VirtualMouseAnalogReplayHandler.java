package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;

import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replays a mouse event if the analog input reaches the threshold value.
 * @author joshjcarrier
 *
 */
public class VirtualMouseAnalogReplayHandler
{
	private final int virtualMouseButtonMask;
	private final boolean isRepeatMode;
	private boolean previouslyActivated;
	
	public VirtualMouseAnalogReplayHandler(int virtualMouseButtonMask, boolean isRepeatMode)
	{
		this.virtualMouseButtonMask = virtualMouseButtonMask;
		this.isRepeatMode = isRepeatMode;
	}
	
	public ReplayState replay(ReplayState replayState, float analogInput, Robot humanInterfaceDeviceService)
	{	
		if (analogInput > 0.9f)
		{
			if (!this.previouslyActivated || this.isRepeatMode)
			{
				humanInterfaceDeviceService.mousePress(this.virtualMouseButtonMask);
				this.previouslyActivated = true;
			}
		}
		else
		{
			if (this.previouslyActivated)
			{
				humanInterfaceDeviceService.mouseRelease(this.virtualMouseButtonMask);
			}
			
			this.previouslyActivated = false;
		}
		
		return replayState;
	}
}
