package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.ApplicationEvent;
import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.services.ReplayState;

public class ApplicationCommandReplayHandler implements IButtonsReplayHandler 
{
	private final Buttons activationButton;
	private final ButtonMapping buttonMapping;
	private int refactoryCounter;
	
	public ApplicationCommandReplayHandler(Buttons activationButton, ButtonMapping buttonMapping) 
	{
		this.activationButton = activationButton;
		this.buttonMapping = buttonMapping;
	}
	
	public ButtonMapping getButtonMapping() 
	{
		return this.buttonMapping;
	}

	public ReplayState replay(ReplayState replayState,
			EnumSet<Buttons> buttons, Robot humanInterfaceDeviceService) 
	{
		if (buttons.contains(this.activationButton) && refactoryCounter == 0)
		{
			switch(this.buttonMapping.getEventCode())
			{
				case ApplicationEvent.MouseMode:
					System.out.println("TODO toggle mouse sensitivity on controller profile");
					break;
			}
			
			// causes a delay where this activation button cannot be processed until it reaches 0 again.
			refactoryCounter = 70;
		}
		else if (refactoryCounter > 0)
		{
			refactoryCounter -= 1;
		}
		
		return replayState;
	}
}
