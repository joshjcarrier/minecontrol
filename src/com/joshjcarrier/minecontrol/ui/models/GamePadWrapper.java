package com.joshjcarrier.minecontrol.ui.models;

import java.net.URL;

import com.joshjcarrier.minecontrol.framework.input.GamePad;
import com.joshjcarrier.minecontrol.ui.ContentResources;

import com.joshjcarrier.rxgamepad.RxGamePad;
import net.java.games.input.Controller;

/**
 * User experience wrapper for the {@link GamePad} class.
 * @author joshjcarrier
 *
 */
public class GamePadWrapper
{
	private final RxGamePad rxGamePad;
	
	public GamePadWrapper(RxGamePad rxGamePad)
	{
		this.rxGamePad = rxGamePad;
	}
			
	public Controller getController()
	{
		return this.rxGamePad.getInternalController();
	}

    public RxGamePad getGamePad()
    {
        return this.rxGamePad;
    }
	
	public String getName()
	{
		return this.getController().getName();
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
