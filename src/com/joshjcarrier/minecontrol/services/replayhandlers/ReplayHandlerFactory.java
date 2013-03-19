package com.joshjcarrier.minecontrol.services.replayhandlers;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;

public class ReplayHandlerFactory 
{
	public IButtonsReplayHandler create(Buttons button, ButtonMapping buttonMapping)
	{
		switch(buttonMapping.getMappingType())
		{
			case Application:
				return new ApplicationCommandReplayHandler(button, buttonMapping);
			
			case Keyboard:
				if(buttonMapping.isToggleMode())
				{
					return new VirtualToggleKeyButtonsReplayHandler(button, buttonMapping);
				}
				else
				{
					return new VirtualKeyButtonsReplayHandler(button, buttonMapping, false);	
				}
				
			case Mouse:
				return new VirtualMouseButtonsReplayHandler(button, buttonMapping, false);
				
			default:
				return new UnboundButtonReplayHandler();		
		}
	}
}
