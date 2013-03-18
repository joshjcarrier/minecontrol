package com.joshjcarrier.minecontrol.services.replayhandlers;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;

public class ReplayHandlerFactory 
{
	public IButtonsReplayHandler create(Buttons button, ButtonMapping buttonMapping, boolean isRepeatMode)
	{
		switch(buttonMapping.getMappingType())
		{
			case Application:
				return new ApplicationCommandReplayHandler(button, buttonMapping);
			
			case Keyboard:
				return new VirtualKeyButtonsReplayHandler(button, buttonMapping, isRepeatMode);
				
			case Mouse:
				return new VirtualMouseButtonsReplayHandler(button, buttonMapping, isRepeatMode);
				
			default:
				return new UnboundButtonReplayHandler();		
		}
	}
}
