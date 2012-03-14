package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;
import java.awt.geom.Point2D;

import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replay an event given the analog input value.
 * @author joshjcarrier
 *
 */
public interface IAnalogReplayHandler
{
	ReplayState replay(ReplayState replayState, Point2D analogInput, Robot humanInterfaceDeviceService);
}
