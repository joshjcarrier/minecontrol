package com.joshjcarrier.minecontrol.services;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.EnumSet;
import java.util.HashMap;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;
import com.joshjcarrier.minecontrol.services.replayhandlers.IButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualKeyAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualKeyButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualMouseAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualMouseButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualMouseMoveAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualScrollButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualToggleKeyButtonsReplayHandler;

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
	
	public RunnableHidReplayService()
	{	
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
		VirtualKeyAnalogReplayHandler leftThumbStickXHandler = new VirtualKeyAnalogReplayHandler(KeyEvent.VK_D, KeyEvent.VK_A, 1.6f);
		VirtualKeyAnalogReplayHandler leftThumbStickYHandler = new VirtualKeyAnalogReplayHandler(KeyEvent.VK_S, KeyEvent.VK_W, 1.6f);
		VirtualMouseMoveAnalogReplayHandler rightThumbStickHandler = new VirtualMouseMoveAnalogReplayHandler();
		VirtualMouseAnalogReplayHandler leftTriggerHandler = new VirtualMouseAnalogReplayHandler(KeyEvent.BUTTON3_MASK, false);
		VirtualMouseAnalogReplayHandler rightTriggerHandler = new VirtualMouseAnalogReplayHandler(KeyEvent.BUTTON1_MASK, false);
		
		while (true)
		{				
			if (activeGamePadState == null)
			{
				ThreadHelper.Sleep(1000);
				continue;
			}
							
			EnumSet<Buttons> buttons = activeGamePadState.getButtons();
			for (IButtonsReplayHandler replayHandler : digitalIdentifierMap.values())
			{
				replayState = replayHandler.replay(replayState, buttons, humanInterfaceDeviceService);
			}
			
			leftThumbStickXHandler.replay(replayState, (float)activeGamePadState.getThumbSticks().getLeft().getX(), humanInterfaceDeviceService);
			leftThumbStickYHandler.replay(replayState, (float)activeGamePadState.getThumbSticks().getLeft().getY(), humanInterfaceDeviceService);
			rightThumbStickHandler.replay(replayState, activeGamePadState.getThumbSticks().getRight(), humanInterfaceDeviceService);
			leftTriggerHandler.replay(replayState, activeGamePadState.getTriggers().getLeft(), humanInterfaceDeviceService);
			rightTriggerHandler.replay(replayState, activeGamePadState.getTriggers().getRight(), humanInterfaceDeviceService);
			
			// TODO 2.0+ adjust wait period based on computer performance
			ThreadHelper.Sleep(5);
		}	
	}
	
	// TODO get from profile
	public HashMap<Buttons, IButtonsReplayHandler> digitalIdentifierMap = new HashMap<Buttons, IButtonsReplayHandler>()
	{
		public static final long serialVersionUID = 7658388604108766926L;
		{
			put(Buttons.A, new VirtualKeyButtonsReplayHandler(Buttons.A, KeyEvent.VK_SPACE, false));
			put(Buttons.B, new VirtualKeyButtonsReplayHandler(Buttons.B, KeyEvent.VK_E, false));
			put(Buttons.X, new VirtualKeyButtonsReplayHandler(Buttons.X, KeyEvent.VK_Q, false));
			put(Buttons.Y, new VirtualKeyButtonsReplayHandler(Buttons.Y, KeyEvent.VK_F5, false));
			
			put(Buttons.LEFT_SHOULDER, new VirtualScrollButtonsReplayHandler(Buttons.LEFT_SHOULDER, -1, false));
			put(Buttons.RIGHT_SHOULDER, new VirtualScrollButtonsReplayHandler(Buttons.RIGHT_SHOULDER, 1, false));
			
			put(Buttons.BACK, new VirtualKeyButtonsReplayHandler(Buttons.BACK, KeyEvent.VK_ESCAPE, false));
//			put(Buttons.START, new VirtualKeyReplayHandler(Buttons.A, KeyEvent.VK_0, false));
			
			put(Buttons.LEFT_STICK, new VirtualToggleKeyButtonsReplayHandler(Buttons.LEFT_STICK, KeyEvent.VK_SHIFT));
			put(Buttons.RIGHT_STICK, new VirtualKeyButtonsReplayHandler(Buttons.RIGHT_STICK, KeyEvent.VK_SPACE, false));
			
			put(Buttons.DPAD_LEFT, new VirtualKeyButtonsReplayHandler(Buttons.DPAD_LEFT, KeyEvent.VK_TAB, false));
			put(Buttons.DPAD_UP, new VirtualMouseButtonsReplayHandler(Buttons.DPAD_UP, KeyEvent.BUTTON2_MASK, false));
			put(Buttons.DPAD_RIGHT, new VirtualKeyButtonsReplayHandler(Buttons.DPAD_RIGHT, KeyEvent.VK_F3, false));			
			put(Buttons.DPAD_DOWN, new VirtualKeyButtonsReplayHandler(Buttons.DPAD_DOWN, KeyEvent.VK_F2, false));			
		}
	};
}
