package com.joshjcarrier.minecontrol.ui.models;

import java.net.URL;

import com.joshjcarrier.minecontrol.framework.input.GamePad;
import com.joshjcarrier.minecontrol.ui.ContentResources;

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
			return this.getClass().getClassLoader().getResource(ContentResources.INPUTDEVICE_XBOX360);
		}
		
		return this.getClass().getClassLoader().getResource(ContentResources.INPUTDEVICE_GENERIC); 
	}
}
