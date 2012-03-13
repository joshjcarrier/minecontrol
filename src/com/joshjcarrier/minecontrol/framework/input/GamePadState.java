package com.joshjcarrier.minecontrol.framework.input;

import java.util.EnumSet;

/**
 * Snapshot of a game pad's state at a given point in time.
 * @author joshjcarrier
 *
 */
public class GamePadState
{
	private final boolean isConnected;
	private final EnumSet<Buttons> buttons;
	private final GamePadThumbSticks gamePadThumbSticks;
	private final GamePadTriggers gamePadTriggers;
	
	public GamePadState(boolean isConnected, EnumSet<Buttons> buttons, GamePadThumbSticks gamePadThumbSticks, GamePadTriggers gamePadTriggers)
	{
		this.isConnected = true;
		this.buttons = buttons;
		this.gamePadThumbSticks = gamePadThumbSticks;
		this.gamePadTriggers = gamePadTriggers;
	}
	
	public EnumSet<Buttons> getButtons()
	{
		return this.buttons;
	}
		
	public GamePadThumbSticks getThumbSticks()
	{
		return this.gamePadThumbSticks;
	}
	
	public GamePadTriggers getTriggers()
	{
		return this.gamePadTriggers;
	}
	
	public boolean isConnected()
	{
		return this.isConnected;
	}
}
