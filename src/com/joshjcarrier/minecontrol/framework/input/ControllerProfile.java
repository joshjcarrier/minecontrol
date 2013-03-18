package com.joshjcarrier.minecontrol.framework.input;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.joshjcarrier.minecontrol.services.ReplayState;
import com.joshjcarrier.minecontrol.services.replayhandlers.IButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualKeyAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualKeyButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualMouseAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualMouseButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualMouseMoveAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualScrollButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualToggleKeyButtonsReplayHandler;

public class ControllerProfile 
{
	public static final int DefaultLeftThumbStickXPositiveMask = KeyEvent.VK_D;
	public static final int DefaultLeftThumbStickXNegativeMask = KeyEvent.VK_A;
	public static final int DefaultLeftThumbStickYPositiveMask = KeyEvent.VK_S;
	public static final int DefaultLeftThumbStickYNegativeMask = KeyEvent.VK_W;
	
	private VirtualKeyAnalogReplayHandler leftThumbStickXHandler;
	private VirtualKeyAnalogReplayHandler leftThumbStickYHandler;
	private VirtualMouseMoveAnalogReplayHandler rightThumbStickHandler;
	private VirtualMouseAnalogReplayHandler leftTriggerHandler;
	private VirtualMouseAnalogReplayHandler rightTriggerHandler;
	
	public ControllerProfile() 
	{
		this.leftThumbStickXHandler = new VirtualKeyAnalogReplayHandler(DefaultLeftThumbStickXPositiveMask, DefaultLeftThumbStickXNegativeMask, VirtualKeyAnalogReplayHandler.DefaultTolerance);
		this.leftThumbStickYHandler = new VirtualKeyAnalogReplayHandler(DefaultLeftThumbStickYPositiveMask, DefaultLeftThumbStickYNegativeMask, VirtualKeyAnalogReplayHandler.DefaultTolerance);
		this.rightThumbStickHandler = new VirtualMouseMoveAnalogReplayHandler();
		this.leftTriggerHandler = new VirtualMouseAnalogReplayHandler(KeyEvent.BUTTON3_MASK, false);
		this.rightTriggerHandler = new VirtualMouseAnalogReplayHandler(KeyEvent.BUTTON1_MASK, false);
	}
	
	public Map<Buttons, IButtonsReplayHandler> getButtonMappingReplayHandlers()
	{
		return this.digitalIdentifierMap;
	}
	
	public String getIdentifier()
	{
		// TODO read/write identifier
		return "default";
	}
	
	public VirtualKeyAnalogReplayHandler getLeftThumbStickXHandler() 
	{
		return leftThumbStickXHandler;
	}
	
	public void setLeftThumbStickXHandler(VirtualKeyAnalogReplayHandler handler) 
	{
		this.leftThumbStickXHandler = handler;
	}

	public VirtualKeyAnalogReplayHandler getLeftThumbStickYHandler() 
	{
		return leftThumbStickYHandler;
	}
	
	public void setLeftThumbStickYHandler(VirtualKeyAnalogReplayHandler handler) 
	{
		this.leftThumbStickYHandler = handler;
	}

	public VirtualMouseMoveAnalogReplayHandler getRightThumbStickHandler() 
	{
		return rightThumbStickHandler;
	}
	
	public void setRightThumbStickHandler(VirtualMouseMoveAnalogReplayHandler handler) 
	{
		this.rightThumbStickHandler = handler;
	}

	public ReplayState replay(GamePadState activeGamePadState, ReplayState replayState, Robot humanInterfaceDeviceService)
	{
		EnumSet<Buttons> buttons = activeGamePadState.getButtons();
		for (Entry<Buttons, IButtonsReplayHandler> replayHandler : digitalIdentifierMap.entrySet())
		{
			replayState = replayHandler.getValue().replay(replayState, buttons, humanInterfaceDeviceService);
		}
		
		leftThumbStickXHandler.replay(replayState, (float)activeGamePadState.getThumbSticks().getLeft().getX(), humanInterfaceDeviceService);
		leftThumbStickYHandler.replay(replayState, (float)activeGamePadState.getThumbSticks().getLeft().getY(), humanInterfaceDeviceService);
		rightThumbStickHandler.replay(replayState, activeGamePadState.getThumbSticks().getRight(), humanInterfaceDeviceService);
		leftTriggerHandler.replay(replayState, activeGamePadState.getTriggers().getLeft(), humanInterfaceDeviceService);
		rightTriggerHandler.replay(replayState, activeGamePadState.getTriggers().getRight(), humanInterfaceDeviceService);
		
		return replayState;
	}
	
	private HashMap<Buttons, IButtonsReplayHandler> digitalIdentifierMap = new HashMap<Buttons, IButtonsReplayHandler>()
	{
		public static final long serialVersionUID = 7658388604108766926L;
		{
			put(Buttons.A, new VirtualKeyButtonsReplayHandler(Buttons.A, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_SPACE), false));
			put(Buttons.B, new VirtualKeyButtonsReplayHandler(Buttons.B, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_E), false));
			put(Buttons.X, new VirtualKeyButtonsReplayHandler(Buttons.X, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_Q), false));
			put(Buttons.Y, new VirtualKeyButtonsReplayHandler(Buttons.Y, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F5), false));
			
			put(Buttons.LEFT_SHOULDER, new VirtualScrollButtonsReplayHandler(Buttons.LEFT_SHOULDER, new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.MOUSE_WHEEL, -1), false));
			put(Buttons.RIGHT_SHOULDER, new VirtualScrollButtonsReplayHandler(Buttons.RIGHT_SHOULDER, new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.MOUSE_WHEEL, 1), false));
			
			put(Buttons.BACK, new VirtualKeyButtonsReplayHandler(Buttons.BACK, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_ESCAPE), false));
//			put(Buttons.START, new VirtualKeyReplayHandler(Buttons.A, KeyEvent.VK_0, false));
			
			put(Buttons.LEFT_STICK, new VirtualToggleKeyButtonsReplayHandler(Buttons.LEFT_STICK, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_SHIFT)));
			put(Buttons.RIGHT_STICK, new VirtualKeyButtonsReplayHandler(Buttons.RIGHT_STICK, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_SPACE), false));
			
			put(Buttons.DPAD_LEFT, new VirtualKeyButtonsReplayHandler(Buttons.DPAD_LEFT, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_TAB), false));
			put(Buttons.DPAD_UP, new VirtualMouseButtonsReplayHandler(Buttons.DPAD_UP, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.BUTTON2_MASK), false));
			put(Buttons.DPAD_RIGHT, new VirtualKeyButtonsReplayHandler(Buttons.DPAD_RIGHT, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F3), false));			
			put(Buttons.DPAD_DOWN, new VirtualKeyButtonsReplayHandler(Buttons.DPAD_DOWN, new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F2), false));			
		}
	};
}
