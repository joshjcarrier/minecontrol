package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.services.ReplayState;

public class VirtualScrollButtonsReplayHandler implements IButtonsReplayHandler
{
	private final Buttons activationButton;
	private final int scrollAmount;
	private final boolean isRepeatMode;
	private boolean previouslyActivated;
	
	public VirtualScrollButtonsReplayHandler(Buttons activationButton, int scrollAmount, boolean isRepeatMode)
	{
		this.activationButton = activationButton;
		this.scrollAmount = scrollAmount;
		this.isRepeatMode = isRepeatMode;
	}
	
	public ReplayState replay(ReplayState replayState, EnumSet<Buttons> buttons, Robot humanInterfaceDeviceService)
	{
		if (buttons.contains(this.activationButton))
		{
			if (!this.previouslyActivated || this.isRepeatMode)
			{
				humanInterfaceDeviceService.mouseWheel(this.scrollAmount);
				previouslyActivated = true;
			}
		}
		else
		{
			previouslyActivated = false;
		}
		
		return replayState;
	}
}
