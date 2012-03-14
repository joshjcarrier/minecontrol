package com.joshjcarrier.minecontrol.replayhandlers;

import java.awt.Robot;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;
import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replays a key press event when the activation button is pressed and stops the next time the button is activated again.
 * @author joshjcarrier
 *
 */
public class VirtualToggleKeyReplayHandler implements IReplayHandler
{
	private final Buttons activationButton;
	private final int virtualKeyMask;
	private boolean previouslyActivated;
	private boolean toggleEnabled;
	
	public VirtualToggleKeyReplayHandler(Buttons activationButton, int virtualKeyMask)
	{	
		this.activationButton = activationButton;
		this.virtualKeyMask = virtualKeyMask;
	}
	
	public ReplayState replay(ReplayState replayState, GamePadState gamePadState, Robot humanInterfaceDeviceService)
	{
		if (gamePadState.getButtons().contains(this.activationButton))
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
				}

				if (!this.toggleEnabled)
				{
					humanInterfaceDeviceService.keyRelease(this.virtualKeyMask);
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
			humanInterfaceDeviceService.keyPress(this.virtualKeyMask);
		}
		
		return replayState;
	}
}