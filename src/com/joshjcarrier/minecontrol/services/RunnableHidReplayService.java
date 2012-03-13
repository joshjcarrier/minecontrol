package com.joshjcarrier.minecontrol.services;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.EnumSet;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;

/**
 * Translates game pad state and user options into interactions with human interface devices (mouse and keyboard).
 * @author joshjcarrier
 *
 */
public class RunnableHidReplayService implements Runnable
{
	private Robot humanInterfaceDeviceService;
	private GamePadState updatedGamePadState;
	private GamePadState activeGamePadState;
	
	public RunnableHidReplayService()
	{	
		try
		{
			this.humanInterfaceDeviceService = new Robot();

			// this prevents the OS from ignoring events generated too quickly
			// in succession
			this.humanInterfaceDeviceService.setAutoDelay(10);
		} 
		catch (AWTException e)
		{
			// TODO 1.2 throw exception
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
			ThreadHelper.Sleep(20);
			
			if(activeGamePadState == null)
			{
				continue;
			}
			
			EnumSet<Buttons> buttons = activeGamePadState.getButtons();
			
			if(buttons.isEmpty())
			{
				continue;
			}
			
			System.out.println(buttons);
			
			// process digital buttons
			// process analog
		}		
	}
}
