package com.joshjcarrier.minecontrol.replayhandlers;

import java.awt.Robot;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;
import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replays a key press and release event when the activation button is pressed and stops when depressed, pressing the key either once or a repeated number of times.
 * @author joshjcarrier
 *
 */
public class VirtualKeyReplayHandler implements IReplayHandler
{
	private final Buttons activationButton;
	private final int virtualKeyMask;
	private final boolean isRepeatMode;
	private boolean previouslyActivated;
	
	public VirtualKeyReplayHandler(Buttons activationButton, int virtualKeyMask, boolean isRepeatMode)
	{
		this.activationButton = activationButton;
		this.virtualKeyMask = virtualKeyMask;
		this.isRepeatMode = isRepeatMode;
	}
	
	public ReplayState replay(ReplayState replayState, GamePadState gamePadState, Robot humanInterfaceDeviceService)
	{
		if (gamePadState.getButtons().contains(this.activationButton))
		{
			if (!this.previouslyActivated || this.isRepeatMode)
			{
				humanInterfaceDeviceService.keyPress(this.virtualKeyMask);
				previouslyActivated = true;
			}
		}
		else
		{
			humanInterfaceDeviceService.keyRelease(this.virtualKeyMask);
			previouslyActivated = false;
		}
		
		return replayState;
	}
}