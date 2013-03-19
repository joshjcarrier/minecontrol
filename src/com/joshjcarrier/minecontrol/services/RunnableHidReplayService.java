package com.joshjcarrier.minecontrol.services;

import java.awt.AWTException;
import java.awt.Robot;

import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;

/**
 * Translates game pad state and user options into interactions with human interface devices (mouse and keyboard).
 * @author joshjcarrier
 *
 */
public class RunnableHidReplayService implements Runnable
{
	private Robot humanInterfaceDeviceService;
	private GamePadState activeGamePadState;	
	private ReplayState replayState;
	private ControllerProfile profile;
	
	public RunnableHidReplayService(ControllerProfile profile)
	{	
		this.profile = profile;
		this.replayState = ReplayState.Primary;
		
		try
		{
			this.humanInterfaceDeviceService = new Robot();

			// this prevents the OS from ignoring events generated too quickly in succession
			this.humanInterfaceDeviceService.setAutoDelay(10);
		} 
		catch (AWTException e)
		{
			// TODO 2.0+ throw exception
			e.printStackTrace();
		}
	}

	public void update(GamePadState gamePadState)
	{
		this.activeGamePadState = gamePadState;		
	}	
	
	public void run()
	{
		while (true)
		{				
			if (activeGamePadState == null)
			{
				ThreadHelper.Sleep(1000);
				continue;
			}
			
			replayState = this.profile.replay(activeGamePadState, replayState, humanInterfaceDeviceService);
			
			// TODO 2.0+ adjust wait period based on computer performance
			ThreadHelper.Sleep(5);
		}	
	}
}
