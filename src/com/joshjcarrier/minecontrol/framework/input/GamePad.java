package com.joshjcarrier.minecontrol.framework.input;

import java.util.EnumSet;
import java.util.HashMap;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 * Abstraction layer between hardware polling and application to obtain the game pad state.
 * @author joshjcarrier
 * 
 */
public class GamePad
{
	// TODO 1.2 all these mappings: might be inconsistent between controllers - auto configure?
//	private static HashMap<ControllerButton, String> buttonFriendlyNames = new HashMap<ControllerButton, String>()
//	{
//		private static final long serialVersionUID = 7658388604108766926L;
//		{
//			put(ControllerButton._0, "A button");
//			put(ControllerButton._1, "B button");
//			put(ControllerButton._2, "X button");
//			put(ControllerButton._3, "Y button");
//			put(ControllerButton._4, "Left shoulder button");
//			put(ControllerButton._5, "Right shoulder button");
//			put(ControllerButton._6, "Select button");
//			put(ControllerButton._7, "Start button");
//			put(ControllerButton._8, "Left joystick button");
//			put(ControllerButton._9, "Right joystick button");
//			
//			// put(Identifier.Axis.Y, "Left joystick (Y axis)");
//			// put(Identifier.Axis.X, "Left joystick (X axis)");
//			
//			// put(Identifier.Axis.RY, "Right joystick (Y axis)");
//			// put(Identifier.Axis.RX, "Right joystick (X axis)");
//			
//			// put(Identifier.Axis.Z, "Left/right triggers");
//		}
//	};
	
	private static HashMap<Identifier, Buttons> digitalIdentifierMap = new HashMap<Identifier, Buttons>()
	{
		private static final long serialVersionUID = 7658388604108766926L;
		{
			put(Identifier.Button._0, Buttons.A);
			put(Identifier.Button._1, Buttons.B);
			put(Identifier.Button._2, Buttons.X);
			put(Identifier.Button._3, Buttons.Y);
			put(Identifier.Button._4, Buttons.LEFT_SHOULDER);
			put(Identifier.Button._5, Buttons.RIGHT_SHOULDER);
			put(Identifier.Button._6, Buttons.BACK);
			put(Identifier.Button._7, Buttons.START);
			put(Identifier.Button._8, Buttons.LEFT_STICK);
			put(Identifier.Button._9, Buttons.RIGHT_STICK);
		}
	};
		
	private final Controller controller;
	
	public GamePad(Controller controller)
	{
		this.controller = controller;
	}
	
	EnumSet<Buttons> previousButtons = EnumSet.noneOf(Buttons.class);
	public GamePadState getState()
	{
		// primes the event queue with pending controller events
		this.controller.poll();
		
		EnumSet<Buttons> buttons = EnumSet.copyOf(previousButtons);
		float leftThumbStickX = 0;
		float leftThumbStickY = 0;
		float rightThumbStickX = 0;
		float rightThumbStickY = 0;
		float leftTrigger = 0;
		float rightTrigger = 0;
		
		EventQueue queue = this.controller.getEventQueue();
		Event event = new Event();
				
		// read all pending events
		while (queue.getNextEvent(event))
		//for (Component comp : this.controller.getComponents())
		{
			Component comp = event.getComponent();
			
			Identifier inputIdentifier = comp.getIdentifier();
			float inputValue = comp.getPollData();
			if (comp.isAnalog()) 
			{							
				if (inputIdentifier == Identifier.Axis.X)
				{
					leftThumbStickX = inputValue;
				}
				else if (inputIdentifier == Identifier.Axis.Y)
				{
					leftThumbStickY = inputValue;
				}
				else if (inputIdentifier == Identifier.Axis.RX)
				{
					rightThumbStickX = inputValue;
				}
				else if (inputIdentifier == Identifier.Axis.RY)
				{
					rightThumbStickY = inputValue;
				}
				else if (inputIdentifier == Identifier.Axis.Z)
				{
					leftTrigger = inputValue;
				}
				else if (inputIdentifier == Identifier.Axis.RZ)
				{
					rightTrigger = inputValue;
				}
			} 
			else 
			{			
				Buttons digitalButton = digitalIdentifierMap.get(inputIdentifier);
				
				if (digitalButton != null)
				{
					// button is not pressed or was previously pressed (as in, depressed)
					if (inputValue != 1f || !buttons.add(digitalButton))
					{
						buttons.remove(digitalButton);
					}
				}
				
				// DPad support
				if (inputIdentifier == Identifier.Axis.POV)
				{
					if (inputValue == 0.25f)
					{
						buttons.add(Buttons.DPAD_UP);
					}
					else if (inputValue == 0.5f)
					{
						buttons.add(Buttons.DPAD_RIGHT);
					}
					else if (inputValue == 0.75f)
					{
						buttons.add(Buttons.DPAD_DOWN);
					}
					else if (inputValue == 1f)
					{
						buttons.add(Buttons.DPAD_LEFT);
					}
					else
					{
						// DPad behaves like analog but is considered digital
						buttons.remove(Buttons.DPAD_LEFT);
						buttons.remove(Buttons.DPAD_UP);
						buttons.remove(Buttons.DPAD_RIGHT);
						buttons.remove(Buttons.DPAD_DOWN);
					}
				}
			}	
		}
		
		previousButtons = buttons;		
		return new GamePadState(
			true, 
			buttons, 
			new GamePadThumbSticks(leftThumbStickX, leftThumbStickY, rightThumbStickX, rightThumbStickY), 
			new GamePadTriggers(leftTrigger, rightTrigger));
	}
}
