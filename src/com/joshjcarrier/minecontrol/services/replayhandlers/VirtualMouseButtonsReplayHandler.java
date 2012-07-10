package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.services.ReplayState;

public class VirtualMouseButtonsReplayHandler implements IButtonsReplayHandler
{
	private final Buttons activationButton;
	private final ButtonMapping buttonMapping;
	private final boolean isRepeatMode;
	private boolean previouslyActivated;
	
	public VirtualMouseButtonsReplayHandler(Buttons activationButton, ButtonMapping buttonMapping, boolean isRepeatMode)
	{
		this.activationButton = activationButton;
		this.buttonMapping = buttonMapping;
		this.isRepeatMode = isRepeatMode;
	}
	
	public ButtonMapping getButtonMapping() 
	{
		return this.buttonMapping;
	}
	
	public ReplayState replay(ReplayState replayState, EnumSet<Buttons> buttons, Robot humanInterfaceDeviceService)
	{
		if (buttons.contains(this.activationButton))
		{
			if (!this.previouslyActivated || this.isRepeatMode)
			{
				humanInterfaceDeviceService.mousePress(this.buttonMapping.getEventCode());
				previouslyActivated = true;
			}
		}
		else if (previouslyActivated)
		{
			humanInterfaceDeviceService.mouseRelease(this.buttonMapping.getEventCode());
			previouslyActivated = false;
		}
		
		return replayState;
	}
}
