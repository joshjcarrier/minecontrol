package com.joshjcarrier.minecontrol.services;

import java.util.ArrayList;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
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
	private GamePad gamePad;
	private ControllerProfile profile;
	
	public RunnableGamePadInterpreter()
	{
		ProfileStorageService profileStorageService = new ProfileStorageService();
		this.profile = profileStorageService.load("default");
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
	
	public ControllerProfile getControllerProfile() {
		return this.profile;
	}
	
	public void setInputReaderDevice(Controller controller)
	{
		this.gamePad = new GamePad(controller);
	}

	public void run()
	{	
		RunnableHidReplayService replayService = new RunnableHidReplayService(this.profile);
		Thread replayServiceThread = new Thread(replayService);
		replayServiceThread.start();
		
		while (true)
		{
			if (this.gamePad == null)
			{
				replayService.update(null);
				ThreadHelper.Sleep(1000);
				continue;
			}
			
			GamePadState gamePadState = this.gamePad.getState();
			replayService.update(gamePadState);
			
			ThreadHelper.Sleep(5);
		}
	}

}
