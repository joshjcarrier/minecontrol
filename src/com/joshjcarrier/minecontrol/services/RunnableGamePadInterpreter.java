package com.joshjcarrier.minecontrol.services;

import java.util.ArrayList;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import rx.Subscription;
import rx.util.functions.Action1;

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
	private final RunnableHidReplayService replayService;
	private GamePad gamePad;
	private ControllerProfile profile;
	private Subscription gamePadSubscription;
	
	public RunnableGamePadInterpreter()
	{
		ProfileStorageService profileStorageService = new ProfileStorageService();
		this.profile = profileStorageService.load("default");
		this.replayService = new RunnableHidReplayService(this.profile);
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


		if(gamePadSubscription != null)
		{
			gamePadSubscription.unsubscribe();
		}
		
		if (this.gamePad == null)
		{
			replayService.update(null);
		}
		
		gamePadSubscription = this.gamePad.getState().subscribe(new Action1<GamePadState>(){

			public void call(GamePadState arg0) {
				replayService.update(arg0);
			}});

	}

	public void run()
	{	
		final RunnableHidReplayService replayService = new RunnableHidReplayService(this.profile);
		Thread replayServiceThread = new Thread(replayService);
		replayServiceThread.start();
	}
}
