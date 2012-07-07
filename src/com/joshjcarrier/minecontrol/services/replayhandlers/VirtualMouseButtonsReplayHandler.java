package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.services.ReplayState;

public class VirtualMouseButtonsReplayHandler implements IButtonsReplayHandler
{
	private final Buttons activationButton;
	private final int virtualMouseButtonMask;
	private final boolean isRepeatMode;
	private boolean previouslyActivated;
	
	public VirtualMouseButtonsReplayHandler(Buttons activationButton, int virtualMouseButtonMask, boolean isRepeatMode)
	{
		this.activationButton = activationButton;
		this.virtualMouseButtonMask = virtualMouseButtonMask;
		this.isRepeatMode = isRepeatMode;
	}
	
	public ReplayState replay(ReplayState replayState, EnumSet<Buttons> buttons, Robot humanInterfaceDeviceService)
	{
		if (buttons.contains(this.activationButton))
		{
			if (!this.previouslyActivated || this.isRepeatMode)
			{
				humanInterfaceDeviceService.mousePress(this.virtualMouseButtonMask);
				previouslyActivated = true;
			}
		}
		else if (previouslyActivated)
		{
			humanInterfaceDeviceService.mouseRelease(this.virtualMouseButtonMask);
			previouslyActivated = false;
		}
		
		return replayState;
	}
}
