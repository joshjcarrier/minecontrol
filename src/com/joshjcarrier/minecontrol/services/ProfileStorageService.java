package com.joshjcarrier.minecontrol.services;

import java.util.Map.Entry;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.services.replayhandlers.IButtonsReplayHandler;

public class ProfileStorageService 
{
	private final PropertiesStorage propertiesStorage;
	
	public ProfileStorageService() 
	{
		this.propertiesStorage = new PropertiesStorage();
	}
	
	public void load()
	{
//		IControllerProfile profile = new ControllerProfile();
//
//		profile.setMouseMode1SensitivityX(this.propertiesStorage.readInt(getConfigurationKey(identifier, "mouse.mode1.sensitivity.x"), profile.getMouseMode1SensitivityX()));
//		profile.setMouseMode1SensitivityY(this.propertiesStorage.readInt(getConfigurationKey(identifier, "mouse.mode1.sensitivity.y"), profile.getMouseMode1SensitivityY()));
//		profile.setMouseMode2SensitivityX(this.propertiesStorage.readInt(getConfigurationKey(identifier, "mouse.mode2.sensitivity.x"), profile.getMouseMode2SensitivityX()));
//		profile.setMouseMode2SensitivityY(this.propertiesStorage.readInt(getConfigurationKey(identifier, "mouse.mode2.sensitivity.y"), profile.getMouseMode2SensitivityY()));
//		
//		for (Entry<ControllerButton, MappedButton> mapping : profile.getButtonMappings().entrySet())
//		{
//			String mappedButtonName = this.propertiesStorage.read(getConfigurationKey(identifier, "button." + mapping.getKey().name()));
//			
//			if (mappedButtonName != null && !mappedButtonName.isEmpty())
//			{
//				profile.setButtonMapping(mapping.getKey(), MappedButton.valueOf(mappedButtonName));
//			}
//		}
//		
//		profile.setInvertLook(this.propertiesStorage.readBoolean(getConfigurationKey(identifier, "mouse.invert")));
//		
//		return profile;
	}
	
	public void store(ControllerProfile profile)
	{
		// TODO commit button mapping value instead of persisting hash value
		String identifier = profile.getIdentifier();
		this.propertiesStorage.writeInt(getConfigurationKey(identifier, "mouse.mode1.sensitivity.x"), profile.getLeftThumbStickXHandler().hashCode());
		this.propertiesStorage.writeInt(getConfigurationKey(identifier, "mouse.mode1.sensitivity.y"), profile.getLeftThumbStickYHandler().hashCode());
//		this.propertiesStorage.writeInt(getConfigurationKey(identifier, "mouse.mode2.sensitivity.x"), profile.getMouseMode2SensitivityX());
//		this.propertiesStorage.writeInt(getConfigurationKey(identifier, "mouse.mode2.sensitivity.y"), profile.getMouseMode2SensitivityY());
		
		for (Entry<Buttons, IButtonsReplayHandler> mapping : profile.getButtonMappingReplayHandlers().entrySet())
		{
			this.propertiesStorage.writeInt(getConfigurationKey(identifier, "button." + mapping.getKey().name()), mapping.getValue().hashCode());	
		}
		
		this.propertiesStorage.writeBoolean(getConfigurationKey(identifier, "mouse.invert"), profile.getRightThumbStickHandler().isInvertY());
		
		this.propertiesStorage.commit();
	}
	
	private static String getConfigurationKey(String controllerIdentifier, String mappingIdentifier)
	{
		return "controllers." + controllerIdentifier + "." + mappingIdentifier;
	}
}
