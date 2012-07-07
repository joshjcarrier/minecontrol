package com.joshjcarrier.minecontrol.ui.models;

import java.net.URL;

import com.joshjcarrier.minecontrol.framework.input.GamePad;

import net.java.games.input.Controller;

/**
 * User experience wrapper for the {@link GamePad} class.
 * @author joshjcarrier
 *
 */
public class GamePadWrapper
{
	private final Controller controller;
	
	public GamePadWrapper(Controller controller)
	{
		this.controller = controller;
	}
			
	public Controller getController()
	{
		return this.controller;
	}
	
	public String getName()
	{
		return this.controller.getName();
	}
	
	public URL getTileResource()
	{
		if (this.getName().toLowerCase().contains("xbox"))
		{
			return this.getClass().getClassLoader().getResource("content/inputdevices/xbox360.png");
		}
		
		return this.getClass().getClassLoader().getResource("content/inputdevices/generic.png"); 
	}
}
