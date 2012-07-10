package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replays a key press event when the activation button is pressed and stops the next time the button is activated again.
 * @author joshjcarrier
 *
 */
public class VirtualToggleKeyButtonsReplayHandler implements IButtonsReplayHandler
{
	private final Buttons activationButton;
	private final ButtonMapping buttonMapping;
	private boolean previouslyActivated;
	private boolean toggleEnabled;
	
	public VirtualToggleKeyButtonsReplayHandler(Buttons activationButton, ButtonMapping buttonMapping)
	{	
		this.activationButton = activationButton;
		this.buttonMapping = buttonMapping;
	}
	
	public ButtonMapping getButtonMapping() 
	{
		return this.buttonMapping;
	}
	
	public ReplayState replay(ReplayState replayState, EnumSet<Buttons> buttons, Robot humanInterfaceDeviceService)
	{
		if (buttons.contains(this.activationButton))
		{
			if (!this.previouslyActivated)
			{
				if (!this.toggleEnabled)
				{
					this.toggleEnabled = true;
				}
				else
				{
					this.toggleEnabled = false;
					humanInterfaceDeviceService.keyRelease(this.buttonMapping.getEventCode());
				}
				
				this.previouslyActivated = true;
			}
		}
		else 
		{
			this.previouslyActivated = false;
		}
		
		if (this.toggleEnabled)
		{
			humanInterfaceDeviceService.keyPress(this.buttonMapping.getEventCode());
		}
		
		return replayState;
	}
}