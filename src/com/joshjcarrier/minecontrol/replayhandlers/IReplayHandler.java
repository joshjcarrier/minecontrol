package com.joshjcarrier.minecontrol.replayhandlers;

import java.awt.Robot;

import com.joshjcarrier.minecontrol.framework.input.GamePadState;
import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replay an event given the state of the game pad.
 * @author joshjcarrier
 *
 */
public interface IReplayHandler
{
	ReplayState replay(ReplayState replayState, GamePadState gamePadState, Robot humanInterfaceDeviceService);
}
