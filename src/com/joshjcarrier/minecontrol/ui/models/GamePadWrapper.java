package com.joshjcarrier.minecontrol.ui.models;

import com.joshjcarrier.minecontrol.ui.ContentResources;
import com.joshjcarrier.rxgamepad.RxGamePad;

import java.net.URL;
import java.util.List;

/**
 * User experience wrapper for the {@link RxGamePad} class.
 * @author joshjcarrier
 *
 */
public class GamePadWrapper
{
	private final RxGamePad rxGamePad;
    private final List<GamePadProfileWrapper> profiles;

	public GamePadWrapper(RxGamePad rxGamePad, List<GamePadProfileWrapper> profiles)	{
		this.rxGamePad = rxGamePad;
        this.profiles = profiles;
	}

    public List<GamePadProfileWrapper> getProfiles()
    {
        return this.profiles;
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
