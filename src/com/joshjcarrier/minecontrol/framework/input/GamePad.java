package com.joshjcarrier.minecontrol.framework.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import rx.Observable;
import rx.util.functions.Func1;

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
	
	public Observable<GamePadState> getState()
	{
		return Observable
				.interval(5, TimeUnit.MILLISECONDS)
				.map(new Func1<Long, Collection<Event>>(){

				EventQueue queue;
				
				{
					// primes the event queue with pending controller events
					controller.poll();
					this.queue = controller.getEventQueue();
				}			
				
				public Collection<Event> call(Long arg0) {
                    controller.poll();
					Collection<Event> events = new ArrayList<Event>();
					
					Event event = new Event();
					while (queue.getNextEvent(event))
					{
						events.add(event);
						event = new Event(); // this allocation necessary?
					}
					
					return events;
				}
			})
			.map(new Func1<Collection<Event>, GamePadState>(){

				EnumSet<Buttons> previousButtons = EnumSet.noneOf(Buttons.class);
				float previousLeftThumbStickX = 0;
				float previousLeftThumbStickY = 0;
				float previousRightThumbStickX = 0;
				float previousRightThumbStickY = 0;
				float previousLeftTrigger = 0;
				float previousRightTrigger = 0;
				
				public GamePadState call(Collection<Event> arg0) {

					EnumSet<Buttons> buttons = EnumSet.copyOf(previousButtons);
					float leftThumbStickX = previousLeftThumbStickX;
					float leftThumbStickY = previousLeftThumbStickY;
					float rightThumbStickX = previousRightThumbStickX;
					float rightThumbStickY = previousRightThumbStickY;
					float leftTrigger = previousLeftTrigger;
					float rightTrigger = previousRightTrigger;

					for (Event event : arg0)
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
								if (inputValue < 0)
								{
									rightTrigger = -inputValue;	
								}
								else
								{
									leftTrigger = inputValue;
								}
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
					
					// these filters compensate for noise due to mechanical nature of joystick readings
					leftThumbStickX = getFilteredApproximation(leftThumbStickX, 0.25f, 0.9f);
					leftThumbStickY = getFilteredApproximation(leftThumbStickY, 0.25f, 0.9f);
					rightThumbStickX = getFilteredApproximation(rightThumbStickX, 0.19f, 0.9f);
					rightThumbStickY = getFilteredApproximation(rightThumbStickY, 0.19f, 0.9f);
					leftTrigger = getFilteredApproximation(leftTrigger, 0.15f, 0.9f);
					rightTrigger = getFilteredApproximation(rightTrigger, 0.15f, 0.9f);
					
					previousLeftThumbStickX = leftThumbStickX;
					previousLeftThumbStickY = leftThumbStickY;
					previousRightThumbStickX = rightThumbStickX;
					previousRightThumbStickY = rightThumbStickY;
					previousLeftTrigger = leftTrigger;
					previousRightTrigger = rightTrigger;
					
					return new GamePadState(
						true, 
						buttons.clone(), 
						new GamePadThumbSticks(leftThumbStickX, leftThumbStickY, rightThumbStickX, rightThumbStickY), 
						new GamePadTriggers(leftTrigger, rightTrigger));
				}});
	}

	private float getFilteredApproximation(float value, float lowerTolerance, float upperTolerance)
	{
		float returnValue = 0;

		if (value > upperTolerance)
		{
			returnValue = 1f;
		} 
		else if (-value > upperTolerance)
		{
			returnValue = -1f;
		} 
		else if (value < lowerTolerance && value > -lowerTolerance)
		{
			returnValue = 0f;
		} 
		else
		{
			returnValue = value;
		}

		return returnValue;
	}
}
