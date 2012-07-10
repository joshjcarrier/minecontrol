package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replay an event given the active buttons.
 * @author joshjcarrier
 *
 */
public interface IButtonsReplayHandler
{
	ButtonMapping getButtonMapping();
	
	ReplayState replay(ReplayState replayState, EnumSet<Buttons> buttons, Robot humanInterfaceDeviceService);
}
