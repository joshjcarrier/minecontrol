package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.services.ReplayState;

public class UnboundButtonReplayHandler implements IButtonsReplayHandler 
{
	public ButtonMapping getButtonMapping() 
	{
		return ButtonMapping.UNBOUND;
	}

	public ReplayState replay(ReplayState replayState,
			EnumSet<Buttons> buttons, Robot humanInterfaceDeviceService) 
	{
		return replayState;
	}
}
