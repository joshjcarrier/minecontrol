package com.joshjcarrier.minecontrol.services;

import java.util.Map.Entry;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.services.replayhandlers.IButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualKeyAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualKeyButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualMouseMoveAnalogReplayHandler;

public class ProfileStorageService 
{
	private final PropertiesStorage propertiesStorage;
	
	public ProfileStorageService() 
	{
		this.propertiesStorage = new PropertiesStorage();
	}
	
	public ControllerProfile load(String identifier)
	{
		this.propertiesStorage.load();
		ControllerProfile profile = new ControllerProfile();

		readKeyAnalogHandler(identifier + ".mouse.mode1.sensitivity.x", this.propertiesStorage); // TODO profile.getLeftThumbStickXHandler()
		readKeyAnalogHandler(identifier + ".mouse.mode1.sensitivity.y", this.propertiesStorage); // TODO profile.getLeftThumbStickYHandler()
		readMouseMoveAnalogHandler(identifier + ".mouse", this.propertiesStorage); // TODO profile.getRightThumbStickHandler()
		
		for (Entry<Buttons, IButtonsReplayHandler> mapping : profile.getButtonMappingReplayHandlers().entrySet())
		{
			IButtonsReplayHandler handler = readHandler(identifier + ".button." + mapping.getKey().name(), mapping.getKey(), mapping.getValue(), this.propertiesStorage);
			profile.getButtonMappingReplayHandlers().put(mapping.getKey(), handler);
		}
		
		return profile;
	}
	
	public void store(ControllerProfile profile)
	{
		// TODO commit button mapping value instead of persisting hash value
		String identifier = profile.getIdentifier();
		
		writeHandler(identifier + ".mouse.mode1.sensitivity.x", profile.getLeftThumbStickXHandler(), this.propertiesStorage);
		writeHandler(identifier + ".mouse.mode1.sensitivity.y", profile.getLeftThumbStickYHandler(), this.propertiesStorage);
		writeHandler(identifier + ".mouse", profile.getRightThumbStickHandler(), this.propertiesStorage);
		
//		this.propertiesStorage.writeInt(getConfigurationKey(identifier, "mouse.mode2.sensitivity.x"), profile.getMouseMode2SensitivityX());
//		this.propertiesStorage.writeInt(getConfigurationKey(identifier, "mouse.mode2.sensitivity.y"), profile.getMouseMode2SensitivityY());
		
		for (Entry<Buttons, IButtonsReplayHandler> mapping : profile.getButtonMappingReplayHandlers().entrySet())
		{
			writeHandler(identifier + ".button." + mapping.getKey().name(), mapping.getValue(), this.propertiesStorage);
		}
		
		this.propertiesStorage.commit();
	}
	
	private static String getConfigurationKey(String controllerIdentifier, String mappingIdentifier)
	{
		return "controllers." + controllerIdentifier + (mappingIdentifier != "" ? "." + mappingIdentifier : "");
	}
	
	private static IButtonsReplayHandler readHandler(String handlerIdentifierPrefix, Buttons activationButton, IButtonsReplayHandler fallbackValue, PropertiesStorage propertiesStorage)
	{
		ButtonMapping buttonMapping = propertiesStorage.readButtonMapping(getConfigurationKey(handlerIdentifierPrefix, ""));
		
		if (buttonMapping == null)
		{
			return fallbackValue;
		}
		
		return new VirtualKeyButtonsReplayHandler(activationButton, buttonMapping, false);
	}
	
	private static VirtualKeyAnalogReplayHandler readKeyAnalogHandler(String handlerIdentifierPrefix, PropertiesStorage propertiesStorage)
	{
		// TODO propertiesStorage.writeInt(getConfigurationKey(handlerIdentifierPrefix, ""));
		return null;
	}
	
	private static VirtualMouseMoveAnalogReplayHandler readMouseMoveAnalogHandler(String handlerIdentifierPrefix, PropertiesStorage propertiesStorage)
	{
		// TODO
//		propertiesStorage.writeInt(getConfigurationKey(handlerIdentifierPrefix, ""), handler.hashCode());
//		propertiesStorage.writeBoolean(getConfigurationKey(handlerIdentifierPrefix, "invert"), handler.isInvertY());
		return null;
	}
	
	private static void writeHandler(String handlerIdentifierPrefix, IButtonsReplayHandler handler, PropertiesStorage propertiesStorage)
	{
		propertiesStorage.writeButtonMapping(getConfigurationKey(handlerIdentifierPrefix, ""), handler != null ? handler.getButtonMapping() : ButtonMapping.UNBOUND);
	}
	
	private static void writeHandler(String handlerIdentifierPrefix, VirtualKeyAnalogReplayHandler handler, PropertiesStorage propertiesStorage)
	{
		propertiesStorage.writeInt(getConfigurationKey(handlerIdentifierPrefix, ""), handler.hashCode());
	}
	
	private static void writeHandler(String handlerIdentifierPrefix, VirtualMouseMoveAnalogReplayHandler handler, PropertiesStorage propertiesStorage)
	{
		propertiesStorage.writeInt(getConfigurationKey(handlerIdentifierPrefix, ""), handler.hashCode());
		propertiesStorage.writeBoolean(getConfigurationKey(handlerIdentifierPrefix, "invert"), handler.isInvertY());
	}
}
