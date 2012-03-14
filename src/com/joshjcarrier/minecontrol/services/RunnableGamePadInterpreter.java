package com.joshjcarrier.minecontrol.services;

import java.util.ArrayList;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import com.joshjcarrier.minecontrol.framework.input.GamePad;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;

/**
 * Manages the active game pad connection.
 * @author joshjcarrier
 *
 */
public class RunnableGamePadInterpreter implements Runnable
{
	//private InputReaderProfileService controllerProfileService;
	private RunnableHidReplayService replayService;
	//private Controller controller;
	//private InputReaderDevice controller;
	private GamePad gamePad;
	
	public RunnableGamePadInterpreter()
	{
		//this.controllerProfileService = new InputReaderProfileService();
	}
	
	public ArrayList<GamePadWrapper> getInputReaderDevices()
	{
		ArrayList<GamePadWrapper> devices = new ArrayList<GamePadWrapper>();
		for(Controller controller : ControllerEnvironment.getDefaultEnvironment().getControllers())
		{
			devices.add(new GamePadWrapper(controller));
		}
		
		return devices;
	}
	
	public void setInputReaderDevice(Controller controller)
	{
		this.gamePad = new GamePad(controller);
		this.replayService = new RunnableHidReplayService();
		Thread replayServiceThread = new Thread(replayService);
		replayServiceThread.start();
	}

	public void run()
	{
		if(this.gamePad == null)
		{
			return;
		}
		
		while (true)
		{
			GamePadState gamePadState = this.gamePad.getState();
			replayService.update(gamePadState);
			
			ThreadHelper.Sleep(5);
		}
	}

}
