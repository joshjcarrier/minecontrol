package com.joshjcarrier.minecontrol.ui.models;

import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;
import com.joshjcarrier.minecontrol.ui.ContentResources;
import com.joshjcarrier.rxgamepad.RxGamePad;

import java.net.URL;

/**
 * User experience wrapper for the {@link RxGamePad} class.
 * @author joshjcarrier
 *
 */
public class GamePadWrapper
{
	private final RxGamePad rxGamePad;
    private GamePadProfile defaultProfile;
	
	public GamePadWrapper(RxGamePad rxGamePad)	{
		this.rxGamePad = rxGamePad;
        this.defaultProfile = new GamePadProfile(rxGamePad);
	}

    public GamePadProfile getDefaultProfile()
    {
        return this.defaultProfile;
    }
	
	public String getName()
	{
		return this.rxGamePad.getName();
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
