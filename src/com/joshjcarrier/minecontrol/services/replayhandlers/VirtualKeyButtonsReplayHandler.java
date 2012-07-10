package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replays a key press and release event when the activation button is pressed and stops when depressed, pressing the key either once or a repeated number of times.
 * @author joshjcarrier
 *
 */
public class VirtualKeyButtonsReplayHandler implements IButtonsReplayHandler
{
	private final Buttons activationButton;
	private final ButtonMapping buttonMapping;
	private final boolean isRepeatMode;
	private boolean previouslyActivated;
	
	public VirtualKeyButtonsReplayHandler(Buttons activationButton, ButtonMapping buttonMapping, boolean isRepeatMode)
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
				humanInterfaceDeviceService.keyPress(this.buttonMapping.getEventCode());
				this.previouslyActivated = true;
			}
		}
		else
		{
			if (this.previouslyActivated)
			{
				humanInterfaceDeviceService.keyRelease(this.buttonMapping.getEventCode());
			}
			
			this.previouslyActivated = false;
		}
		
		return replayState;
	}
}